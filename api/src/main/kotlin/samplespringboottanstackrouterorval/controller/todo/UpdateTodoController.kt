package samplespringboottanstackrouterorval.controller.todo

import com.github.michaelbull.result.mapBoth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import samplespringboottanstackrouterorval.service.UpdateTodoInput
import samplespringboottanstackrouterorval.service.UpdateTodoService
import samplespringboottanstackrouterorval.util.toUuidOrNull

@RestController
@Tag(name = "Todo", description = "")
class UpdateTodoController(
    private val updateTodoService: UpdateTodoService,
) {
    @Operation(
        summary = "指定した ID の Todo を更新する",
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
    @PutMapping(
        "/api/todos/{todoId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateTodo(
        @Parameter(description = "Todo ID", required = true) @PathVariable todoId: String,
        @Parameter(description = "", required = true) @Validated @RequestBody body: UpdateTodoRequest,
    ): ResponseEntity<TodoResponse> {
        val id = todoId.toUuidOrNull()
            ?: return ResponseEntity.badRequest().build()

        val input = UpdateTodoInput(
            id,
            body.text,
            body.completed,
        )

        return updateTodoService.update(input)
            .mapBoth(
                {
                    ResponseEntity.ok(TodoResponse.from(it))
                },
                {
                    when (it) {
                        UpdateTodoService.InputError -> ResponseEntity.badRequest().build()
                        UpdateTodoService.NotFoundError -> ResponseEntity.notFound().build()
                    }
                },
            )
    }
}

@Schema(name = "UpdateTodo")
data class UpdateTodoRequest(
    @Schema(required = true, description = "テキスト")
    @field:NotBlank
    @field:Length(min = 1, max = 50)
    val text: String,

    @Schema(required = true, description = "完了しているか")
    @field:NotNull
    val completed: Boolean,
)
