package com.idris  // Main


fun main(args: Array<String>) {
    // val f = Flow()
    // f.begin()  // Start prompting the user for commands
    // f.beginAlt(args);

    val fa = FlowAlt()
    // fa.repl("../../sdata/realData.db")
    fa.repl("../../testdata/testData.db")
}
