package samplespringboottanstackrouterorval.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.springframework.stereotype.Service
import samplespringboottanstackrouterorval.reposiroty.TodoRepository
import java.util.UUID

@Service
class DeleteTodoService(
    private val todoRepository: TodoRepository,
) {
    fun delete(id: UUID): Result<Unit, String> {
        val todo = todoRepository.findById(id)
            ?: return Err("Todo が存在しません")

        todoRepository.delete(todo)

        return Ok(Unit)
    }
}
