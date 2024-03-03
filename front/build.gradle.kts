import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

node {
    download = false
}

tasks.register<NpxTask>("generateOpenApiClient") {
    dependsOn(":api:clean")
    dependsOn(":api:generateOpenApiDocs").mustRunAfter(":api:clean")
    mustRunAfter(":api:generateOpenApiDocs")

    command = "orval"
}

tasks.register<NpmTask>("npmRunDev") {
    args = listOf("run", "dev")
}

tasks.register<NpmTask>("npmCleanInstall") {
    args = listOf("ci")
}
