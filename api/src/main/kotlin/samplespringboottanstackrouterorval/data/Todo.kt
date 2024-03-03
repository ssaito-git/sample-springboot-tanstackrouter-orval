package samplespringboottanstackrouterorval.data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.time.OffsetDateTime
import java.util.UUID

data class Todo(
    val id: UUID,
    val text: String,
    val completed: Boolean,
    val createdAt: OffsetDateTime,
    val completedAt: OffsetDateTime?,
) {
    fun updateText(text: String): Result<Todo, String> {
        if (text.isBlank()) {
            return Err("テキストが空です")
        }

        if (text.length > 50) {
            return Err("テキストが 50 文字より大きいです")
        }

        return Ok(this.copy(text = text))
    }

    fun complete(completedAt: OffsetDateTime): Todo {
        return this.copy(
            completed = true,
            completedAt = completedAt,
        )
    }

    fun cancel(): Todo {
        return this.copy(
            completed = false,
            completedAt = null,
        )
    }
}
