package com.idris

import com.idris.database.helpers.*

// The REPL command line interface


class FlowAlt {

    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        println("(Idris REPL)        SYNTAX : [list/create/delete/log] -[x/f/c/e] <name>")
        var input = ""

        while (true) {
            println()
            print(">> ")
            input = readln()
            val args = input.split(" ")  // parse the args

            if (args[0] == "q") break  // Quit the REPL on q
            if (args.size !in 2..3) println("ERROR: invalid argument count")

            when (args[0]) {  // args: <command> <option> <parameter>
                "list" -> ListHelper.choose(args[1])
                "create" -> CreateHelper.choose(args[1])
                // "delete" -> // DeleteHelper.choose(args[1])
                "log" -> LogHelper.choose(args[1])
            }

            println()
        }
    }

}