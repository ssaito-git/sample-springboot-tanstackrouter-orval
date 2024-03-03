package samplespringboottanstackrouterorval.service

import com.fasterxml.uuid.Generators
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.springframework.stereotype.Service
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.reposiroty.TodoRepository
import java.time.OffsetDateTime

@Service
class CreateTodoService(
    private val todoRepository: TodoRepository,
) {
    fun create(text: String): Result<Todo, String> {
        if (text.isBlank()) {
            return Err("テキストが空です")
        }

        if (text.length > 50) {
            return Err("テキストが 50 文字より大きいです")
        }

        val newTodo = Todo(
            Generators.timeBasedEpochGenerator().generate(),
            text,
            false,
            OffsetDateTime.now(),
            null,
        )

        todoRepository.add(newTodo)

        return Ok(newTodo)
    }
}
