import { useQueryClient } from '@tanstack/react-query'
import { TodoListItem } from '../../api/model'
import {
  useUpdateTodo,
  getGetTodoListQueryKey,
  useDeleteTodo,
} from '../../api/todo/todo'
import { useState } from 'react'

export type TodoProps = {
  todo: TodoListItem
}

export const Todo = ({ todo }: TodoProps) => {
  const [mode, setMode] = useState<Mode>('display')
  const handleChange = (newMode: Mode) => {
    setMode(newMode)
  }

  switch (mode) {
    case 'display':
      return <DisplayTodo todo={todo} onChange={handleChange} />
    case 'edit':
      return <EditTodo todo={todo} onChange={handleChange} />
  }
}

type Mode = 'display' | 'edit'

type DisplayTodoProps = {
  todo: TodoListItem
  onChange: (mode: Mode) => void
}

const DisplayTodo = ({ todo, onChange }: DisplayTodoProps) => {
  const queryClient = useQueryClient()
  const updateMutation = useUpdateTodo({
    mutation: {
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: getGetTodoListQueryKey() }),
    },
  })
  const deleteMutation = useDeleteTodo({
    mutation: {
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: getGetTodoListQueryKey() }),
    },
  })
  const handleCheckboxClick = () => {
    updateMutation.mutate({
      todoId: todo.id,
      data: {
        text: todo.text,
        completed: !todo.completed,
      },
    })
  }
  const handleDeleteClick = () => {
    deleteMutation.mutate({ todoId: todo.id })
  }
  const handleEditClick = () => {
    onChange('edit')
  }

  return (
    <div className="mb-3 flex justify-center rounded bg-white p-3 shadow">
      <div className="mr-3 grid content-center">
        <input
          type="checkbox"
          defaultChecked={todo.completed}
          className="h-6 w-6"
          onChange={handleCheckboxClick}
        />
      </div>
      <div
        className={`m-auto mr-2 flex-auto ${todo.completed ? ' line-through' : ''}`}
      >
        <span className="border border-transparent p-2">{todo.text}</span>
      </div>
      <button
        className="mr-3 w-20 rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-700"
        onClick={handleEditClick}
      >
        Edit
      </button>
      <button
        className="w-20 rounded border border-red-500 bg-transparent px-4 py-2 font-semibold text-red-700 hover:border-transparent hover:bg-red-500 hover:text-white"
        onClick={handleDeleteClick}
      >
        Delete
      </button>
    </div>
  )
}

const EditTodo = ({ todo, onChange }: DisplayTodoProps) => {
  const [text, setText] = useState(todo.text)
  const queryClient = useQueryClient()
  const updateMutation = useUpdateTodo({
    mutation: {
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: getGetTodoListQueryKey() })
        onChange('display')
      },
    },
  })
  const handleSaveClick = () => {
    updateMutation.mutate({
      todoId: todo.id,
      data: {
        text: text,
        completed: todo.completed,
      },
    })
  }

  return (
    <div className="mb-3 flex justify-center rounded bg-white p-3 shadow">
      <div className="mr-3 grid content-center">
        <input
          type="checkbox"
          defaultChecked={todo.completed}
          className="h-6 w-6 cursor-not-allowed"
          disabled
        />
      </div>
      <div className="m-auto mr-2 flex-auto">
        <input
          type="text"
          value={text}
          className="w-full rounded border p-2"
          onChange={(e) => setText(e.target.value)}
        />
      </div>
      <button
        className="mr-3 w-20 rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-700"
        onClick={handleSaveClick}
      >
        Save
      </button>
      <button
        className="w-20 cursor-not-allowed rounded border border-red-500 bg-transparent px-4 py-2 font-semibold  text-red-700"
        disabled
      >
        Delete
      </button>
    </div>
  )
}
