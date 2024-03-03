package samplespringboottanstackrouterorval.controller.todo

import com.github.michaelbull.result.mapBoth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import samplespringboottanstackrouterorval.service.DeleteTodoService
import samplespringboottanstackrouterorval.util.toUuidOrNull

@RestController
@Tag(name = "Todo", description = "")
class DeleteTodoController(
    private val deleteTodoService: DeleteTodoService,
) {
    @Operation(
        summary = "指定した ID の Todo を削除する",
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
    @DeleteMapping("/api/todos/{todoId}")
    fun deleteTodo(
        @Parameter(description = "Todo ID", required = true) @PathVariable todoId: String,
    ): ResponseEntity<Void> {
        val id = todoId.toUuidOrNull()
            ?: return ResponseEntity.badRequest().build()

        return deleteTodoService.delete(id)
            .mapBoth(
                { ResponseEntity.noContent().build() },
                { ResponseEntity.notFound().build() },
            )
    }
}
