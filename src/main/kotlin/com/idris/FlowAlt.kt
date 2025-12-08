package com.idris

import com.idris.database.CreateHelper
import com.idris.database.ListHelper
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import java.sql.Connection

class FlowAlt {

    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        println("(Idris REPL)")
        var input = ""
        println("SYNTAX  [list/create/delete/log] -[x/f/c/e] <name>")

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
                //"log" -> // LogHelper.choose(args[1])
            }

            println()
        }
    }

}