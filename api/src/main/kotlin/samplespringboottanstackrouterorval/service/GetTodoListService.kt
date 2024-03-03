package samplespringboottanstackrouterorval.service

import org.springframework.stereotype.Service
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.reposiroty.TodoRepository

@Service
class GetTodoListService(
    private val todoRepository: TodoRepository
) {
    fun get(): List<Todo> {
        return todoRepository.findAll()
    }
}
