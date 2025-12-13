package com.idris

// Main


fun main(args: Array<String>) {
    val path = "sdata/real.db"
    // path = "testdata/testData.db"

    FlowAlt.repl(path)
}


// Reloads the specified database (reconnects and resets)
fun reload(path: String) {
    val database = Database(path)
    database.reset(false)
}
