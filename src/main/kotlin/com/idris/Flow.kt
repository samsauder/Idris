package com.idris

import com.idris.database.operators.Controller
import com.idris.database.operators.todo.Creator
import com.idris.database.operators.refactored.Deleter
import com.idris.database.operators.refactored.Lister
import com.idris.database.operators.todo.Logger
import com.idris.database.operators.todo.Modifier
import com.idris.database.operators.refactored.Viewer
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styles

object Flow {
    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        println("\n(Idris REPL)")

        var input = ""

        while (true) {
            println()
            print(">> ")
            input = readln()
            val args = input.split(" ")  // parse the args

            when(args[0]) {
                "q" -> break      // quit the REPL
                "help" -> {       // open help
                    help()
                    continue
                }
            }

            if (args.size != 2) {
                println("ERROR: invalid # of args\n")
                continue
            }

            val t = flagToCT(args[1])  // concept type from flag

            if (t == null) {
                println("ERROR: invalid flag\n")
                continue
            }

            when (args[0]) {  // args: <command> <option>
                "list" -> Controller.list(t)
                "create" -> Creator.choose(args[1], path)
                "delete" -> Deleter.choose(args[1], path)
                "log" -> Logger.choose(args[1], path)
                "modify" -> Modifier.choose(args[1], path)
                "view" -> Controller.view(t)
                "help" -> help()
                else -> { println("ERROR: invalid operator\n") }
            }

            println()
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
            else -> {null}  // invalid flag
        }
    }


    // Print help
    private fun help() {
        val styled14minus = "[..1499]"
        val styled15 = "${Styles.BLUE}[1500-1599]${Styles.RESET}"
        val styled16 = "${Styles.CYAN}[1600-1699]${Styles.RESET}"
        val styled17 = "${Styles.GREEN}[1700-1799]${Styles.RESET}"
        val styled18 = "${Styles.YELLOW}[1800-1899]${Styles.RESET}"
        val styled19plus = "${Styles.RED}[1900...]${Styles.RESET}"

        val syntax = "SYNTAX        |  [list/create/delete/modify/log/view] -[x/f/c/e/p/d]"
        val colors = "ELO COLOR KEY |  $styled14minus $styled15 $styled16 $styled17 $styled18 $styled19plus\n"
        println(syntax)
        println(colors)
    }

}