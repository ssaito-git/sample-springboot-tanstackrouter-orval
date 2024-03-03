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
import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import samplespringboottanstackrouterorval.service.CreateTodoService

@RestController
@Tag(name = "Todo", description = "")
class CreateTodoController(
    private val createTodoService: CreateTodoService,
) {
    @Operation(
        summary = "Todo を作成する",
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
        ],
    )
    @PostMapping(
        "/api/todos",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun createTodo(
        @Parameter(description = "", required = true) @Validated @RequestBody body: CreateTodoRequest,
    ): ResponseEntity<TodoResponse> {
        return createTodoService.create(body.text)
            .mapBoth(
                { ResponseEntity.ok(TodoResponse.from(it)) },
                { ResponseEntity.badRequest().build() },
            )
    }
}

@Schema(name = "CreateTodo")
data class CreateTodoRequest(
    @Schema(required = true, description = "テキスト")
    @field:NotBlank
    @field:Length(min = 1, max = 50)
    val text: String,
)
