package com.idris

import com.idris.database.operators.Controller
import com.idris.database.operators.todo.Creator
import com.idris.database.operators.todo.Logger
import com.idris.database.operators.todo.Modifier
import com.idris.system.extra.ConceptType

object Flow {
    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        println("\n(Idris REPL)")

        var input = ""

        while (true) {
            print(">> ")
            input = readln()
            val args = input.split(" ")  // parse the args
            val type = if (args.size == 2) flagToCT(args[1]) else null

            if (type == null && args.size > 1) {
                println("ERROR: invalid flag\n")
                continue
            }


            // skills

            when (args[0]) {  // args: <command> <option>
                "list" -> Controller.list(type!!)
                "create" -> Creator.choose(args[1], path)
                "delete" -> Controller.delete(type!!)
                "log" -> Logger.choose(args[1], path)
                "modify" -> Modifier.choose(args[1], path)
                "view" -> Controller.view(type!!)
                "dash" -> Controller.dash()
                "help" -> Controller.help()
                "q" -> break
                else -> { println("ERROR: invalid command\n") }
            }

            println("\n")
        }
    }


    // Returns the ConceptType version of the given CLI flag
    fun flagToCT(flag: String): ConceptType? {
        return when (flag) {
            "-f" -> ConceptType.FOUNDATION
            "-c" -> ConceptType.CHALLENGE
            "-e" -> ConceptType.EXAM
            "-p" -> ConceptType.PROGRESSION
            "-d" -> ConceptType.DAY
            "-x" -> ConceptType.EXPERIMENT
            "-r" -> ConceptType.RECORD
            else -> {null}  // invalid flag
        }
    }

    // Returns the ObjectiveType version of the given CLI flag
    fun flagToOT(flag: String): ObjectiveType? {
        return when (flag) {
            "-f" -> ObjectiveType.FOUNDATION
            "-c" -> ObjectiveType.CHALLENGE
            "-e" -> ObjectiveType.EXAM
            else -> {null}  // invalid flag
        }
    }

}