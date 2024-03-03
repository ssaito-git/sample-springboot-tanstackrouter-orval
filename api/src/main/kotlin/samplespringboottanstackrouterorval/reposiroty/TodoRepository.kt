package samplespringboottanstackrouterorval.reposiroty

import org.springframework.stereotype.Repository
import samplespringboottanstackrouterorval.data.Todo
import samplespringboottanstackrouterorval.util.toUuidOrNull
import java.time.OffsetDateTime
import java.util.UUID

@Repository
class TodoRepository {
    private val todos = mutableListOf(
        Todo(
            "d6ca4292-69ae-4f3d-86dc-eb8f8f0fac66".toUuidOrNull()!!,
            "foo",
            false,
            OffsetDateTime.now().minusHours(3),
            null,
        ),
        Todo(
            "1440b4c5-e268-48bc-93c5-46596f6312e2".toUuidOrNull()!!,
            "bar",
            true,
            OffsetDateTime.now().minusHours(2),
            OffsetDateTime.now().minusHours(2).plusMinutes(30),
        ),
        Todo(
            "aa1bd836-7796-41e5-ab73-dde97b2ed05c".toUuidOrNull()!!,
            "baz",
            false,
            OffsetDateTime.now().minusHours(1),
            null,
        ),
    )

    fun findById(id: UUID): Todo? {
        return todos.firstOrNull { it.id == id }?.copy()
    }

    fun findAll(): List<Todo> {
        return todos.map { it.copy() }
    }

    fun add(todo: Todo) {
        todos.add(todo)
    }

    fun save(todo: Todo) {
        val index = todos.indexOfFirst { it.id == todo.id }

        if (index == -1) {
            return
        }

        todos[index] = todo
    }

    fun delete(todo: Todo): Boolean {
        return todos.removeIf { it.id == todo.id }
    }
}
