package samplespringboottanstackrouterorval.controller.todo

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import samplespringboottanstackrouterorval.service.GetTodoListService

@RestController
@Tag(name = "Todo", description = "")
class GetTodoListController(
    private val getTodoListService: GetTodoListService,
) {
    @Operation(
        summary = "Todo のリストを取得する",
        description = "",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = TodoListResponse::class))],
            ),
        ],
    )
    @GetMapping("/api/todos", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTodoList(): ResponseEntity<TodoListResponse> {
        val todos = getTodoListService.get().map {
            TodoListItemResponse(it.id.toString(), it.text, it.completed)
        }
        val response = TodoListResponse(todos)
        return ResponseEntity.ok(response)
    }
}

@Schema(name = "TodoListItem")
data class TodoListItemResponse(
    @Schema(required = true, description = "Todo ごとに一意な ID")
    val id: String,
    @Schema(required = true, description = "テキスト")
    val text: String,
    @Schema(required = true, description = "完了しているか")
    val completed: Boolean,
)

@Schema(name = "TodoList")
data class TodoListResponse(
    @Schema(required = true, description = "Todo のリスト")
    val todos: List<TodoListItemResponse>,
)
