package com.idris

import com.idris.database.helpers.*
import com.idris.sampleData.nineRepsV6for90

// The REPL command line interface


class FlowAlt {
    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        println("(Idris REPL)        SYNTAX: [list/create/delete/log] -[x/f/c/e]")
        var input = ""

        while (true) {
            println()
            print(">> ")
            input = readln()
            val args = input.split(" ")  // parse the args

            if (args[0] == "q") break  // Quit the REPL on q
            if (args.size !in 2..3) println("ERROR: invalid argument count")

            when (args[0]) {  // args: <command> <option>
                "list" -> ListHelper.choose(args[1], path)
                "create" -> CreateHelper.choose(args[1], path)
                "delete" -> DeleteHelper.choose(args[1], path)
                "log" -> LogHelper.choose(args[1], path)
                "modify" -> ModifyHelper.choose(args[1], path)
                // "dash" -> DashHelper.choose(args[1])
            }

            println()
        }
    }
}