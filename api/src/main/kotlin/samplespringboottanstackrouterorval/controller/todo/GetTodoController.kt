package samplespringboottanstackrouterorval.controller.todo

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.service.GetTodoService
import samplespringboottanstackrouterorval.util.toUuidOrNull

@RestController
@Tag(name = "Todo", description = "")
class GetTodoController(
    private val getTodoService: GetTodoService,
) {
    @Operation(
        summary = "指定した ID の Todo を取得する",
        description = "",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = TodoResponse::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "",
                content = [Content()],
            ),
            ApiResponse(
                responseCode = "404",
                description = "",
                content = [Content()],
            ),
        ],
    )
    @GetMapping("/api/todos/{todoId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTodo(
        @Parameter(description = "Todo ID", required = true) @PathVariable todoId: String,
    ): ResponseEntity<TodoResponse> {
        val id = todoId.toUuidOrNull()
            ?: return ResponseEntity.badRequest().build()

        val todo = getTodoService.get(id)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(TodoResponse.from(todo))
    }
}

@Schema(name = "Todo")
data class TodoResponse(
    @Schema(required = true, description = "Todo ごとに一意な ID")
    val id: String,
    @Schema(required = true, description = "テキスト")
    val text: String,
    @Schema(required = true, description = "完了しているか")
    val completed: Boolean,
    @Schema(required = true, description = "作成日時")
    val createdAt: Long,
    @Schema(required = true, description = "完了日時")
    val completedAt: Long?,
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                todo.id.toString(),
                todo.text,
                todo.completed,
                todo.createdAt.toEpochSecond(),
                todo.completedAt?.toEpochSecond(),
            )
        }
    }
}
