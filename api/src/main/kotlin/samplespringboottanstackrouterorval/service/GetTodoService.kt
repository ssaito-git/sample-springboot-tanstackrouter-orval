package samplespringboottanstackrouterorval.service

import org.springframework.stereotype.Service
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.reposiroty.TodoRepository
import java.util.UUID

@Service
class GetTodoService(
    private val todoRepository: TodoRepository
) {
    fun get(id: UUID): Todo? {
        return todoRepository.findById(id)
    }
}
