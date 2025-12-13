package com.idris

// Main


fun main(args: Array<String>) {
    val fa = FlowAlt()

    val path = "sdata/real.db"
    // path = "testdata/testData.db"

    // val database = Database(path)
    // database.reset(false)

    fa.repl(path)
}
