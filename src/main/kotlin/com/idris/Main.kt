package com.idris

// Main


fun main(args: Array<String>) {
    val path = "sdata/real.db"
    // val path = "testdata/testData.db"
    // val path = "testdata/willData.db"
    // reload(path, false)
    FlowAlt.repl(path)
}


// Reloads the specified database (reconnects and resets)
fun reload(path: String, deleteEntities: Boolean) {
    val database = Database(path)
    database.reset(deleteEntities)
}
