package samplespringboottanstackrouterorval.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapError
import org.springframework.stereotype.Service
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.reposiroty.TodoRepository
import java.time.OffsetDateTime
import java.util.UUID

@Service
class UpdateTodoService(
    private val todoRepository: TodoRepository,
) {
    fun update(input: UpdateTodoInput): Result<Todo, Error> {
        val todo = todoRepository.findById(input.id)
            ?: return Err(NotFoundError)

        return todo.updateText(input.text)
            .mapError { InputError }
            .andThen {
                val updatedTodo = if (input.completed) {
                    it.complete(OffsetDateTime.now())
                } else {
                    it.cancel()
                }

                todoRepository.save(updatedTodo)

                Ok(updatedTodo)
            }
    }

    sealed interface Error
    data object InputError : Error
    data object NotFoundError : Error
}

data class UpdateTodoInput(
    val id: UUID,
    val text: String,
    val completed: Boolean,
)
