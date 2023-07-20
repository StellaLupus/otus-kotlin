package ru.otus.otuskotlin.crowdproj.dsl

class WhereContext {
    var where: MutableList<String> = mutableListOf()
    infix fun String.eq(s: String?) {
        where += if (s != null)
            "$this = '$s'"
        else
            "$this is null"
    }

    infix fun String.eq(i: Int) {
        where += "$this = $i"
    }

    infix fun String.nonEq(s: String?) {
        where += if (s != null)
            "$this != '$s'"
        else
            "$this !is null"
    }

    infix fun String.nonEq(i: Int) {
        where += "$this != $i"
    }
}

class SqlSelectBuilder(
    private var columns: MutableList<String> = mutableListOf(),
    private var table: String = "",
    private var where: String = ""
) {
    fun build(): String {
        if (table.isEmpty())
            throw Exception("Table name is empty")
        return "select ${if (columns.isEmpty()) "*" else columns.joinToString(", ")} from $table${if (where.isEmpty()) "" else " where ${where}"}"
    }

    fun from(table: String) {
        this.table = table.trim()
    }

    fun select(vararg columns: String) {
        this.columns.addAll(columns)
    }

    fun where(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        where += ctx.where.joinToString(" ")
    }
    fun or(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        if(ctx.where.count() < 2)
            throw Exception("Or condition must contain more than one condition")
        where += "(${ctx.where.joinToString(" or ")})"
    }
}

fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(block)
}

