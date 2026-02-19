package com.idris

import com.idris.database.operators.Controller
import com.idris.database.operators.todo.Creator
import com.idris.database.operators.todo.Modifier
import com.idris.system.extra.ConceptType
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util


object Flow {
    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        val version = "0.06"
        val quote = "The excellence of the soul is understanding; for one who understands is conscious, devoted, and already godlike."
        Util.idrisBanner()  // print the name Idris (stylized)
        println("Version ${version}\n")

        print(style(quote, Styles.YELLOW))
        println("  — Hermes Trismegistus\n\n")

        var input: String? = ""
        var inputPrev: String? = ""  // last entered command

        while (true) {
            print(">> ")
            // input = readln()
            input = readlnOrNull()

            if (input == "q") {  // quit
                break
            } else if (input == "") {
                execCommand(inputPrev)  // execute the most recently executed command
            } else {
                execCommand(input)
            }

            println("\n")

            if (input != "") inputPrev = input
        }
    }


    // Execute the given Idris command, return false if the command is invalid
    fun execCommand(command: String?): Boolean {
        val args = command?.split(" ")  // parse the args

        val type = if (args?.size == 2) flagToCT(args[1]) else null

        if (type == null && args?.size!! > 1 && args[1] != "-t") {  // TODO remove last expression later
            println("${Util.error()}  invalid flag\n")
            return false
        }

        // skills...

        when (args?.get(0)) {  // args: <command> <option>
            "list" -> Controller.list(type!!)
            "create" -> Creator.choose(args[1], "")
            "delete" -> Controller.delete(type!!)
            "log" -> Controller.log(flagToOT(args[1])!!)
            "modify" -> Modifier.choose(args[1], "")
            "view" -> Controller.view(type!!)
            "dash" -> Controller.dash()
            "help" -> Controller.help()
            // "q" -> break
            else -> {
                println("${Util.error()}  invalid command\n")
                return false
            }
        }
        return true
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