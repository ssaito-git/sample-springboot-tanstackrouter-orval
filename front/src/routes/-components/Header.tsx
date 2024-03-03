import { useQueryClient } from '@tanstack/react-query'
import { useState } from 'react'
import { useCreateTodo, getGetTodoListQueryKey } from '../../api/todo/todo'

export const Header = () => {
  const [text, setText] = useState('')
  const queryClient = useQueryClient()
  const createMutation = useCreateTodo({
    mutation: {
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: getGetTodoListQueryKey() })
        setText('')
      },
    },
  })
  const handleAddClick = () => {
    createMutation.mutate({ data: { text } })
  }

  return (
    <div className="flex content-between">
      <input
        type="text"
        className="mr-3 w-full rounded border p-2"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <button
        className="w-20 rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-700"
        onClick={handleAddClick}
        disabled={createMutation.isPending}
      >
        Add
      </button>
    </div>
  )
}
