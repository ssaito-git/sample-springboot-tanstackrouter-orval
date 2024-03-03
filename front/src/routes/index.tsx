import { createFileRoute } from '@tanstack/react-router'
import {
  getGetTodoListQueryOptions,
  useGetTodoListSuspense,
} from '../api/todo/todo'
import { Todo } from './-components/Todo'
import { Header } from './-components/Header'

export const Route = createFileRoute('/')({
  loader: ({ context: { queryClient } }) =>
    queryClient.ensureQueryData(getGetTodoListQueryOptions()),
  component: IndexComponent,
})

function IndexComponent() {
  return (
    <div className="flex h-full w-full flex-col items-center overflow-auto">
      <header className="sticky top-0 flex w-full grow-0 flex-col items-center bg-gray-200/50 backdrop-blur-xl">
        <div className="w-full max-w-3xl p-5">
          <Header />
        </div>
      </header>
      <main className="flex w-full flex-grow flex-col items-center">
        <div className="w-full max-w-3xl p-5">
          <TodoList />
        </div>
      </main>
    </div>
  )
}

const TodoList = () => {
  const query = useGetTodoListSuspense()

  return (
    <div>
      {query.data.data.todos.map((todo) => (
        <Todo key={todo.id} todo={todo} />
      ))}
    </div>
  )
}
