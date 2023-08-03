rootProject.name = "crowdproj"

pluginManagement {

    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion apply false
    }
}

include("m1l1")
include("m1l3-oop")
include("m1l4-dsl")
include("m1l5-coro")