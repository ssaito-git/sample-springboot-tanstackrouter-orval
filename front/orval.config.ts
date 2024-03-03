import { defineConfig } from 'orval'

export default defineConfig({
  todo: {
    input: '../api/build/openapi.json',
    output: {
      target: 'src/api/todo-api.ts',
      schemas: 'src/api/model',
      client: 'react-query',
      mode: 'tags-split',
      override: {
        query: {
          useSuspenseQuery: true,
          version: 5,
        },
      },
    },
  },
})
