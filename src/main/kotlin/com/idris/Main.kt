package com.idris


// Main
fun main(args: Array<String>) {
    System.setProperty("slf4j.internal.verbosity", "WARN");
    val path = "/home/sam/Idris/testdata/061.db"
    Flow.repl(path)
}