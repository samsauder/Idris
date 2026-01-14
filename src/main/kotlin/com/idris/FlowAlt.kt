package com.idris

import com.idris.constants.Styles
import com.idris.database.operators.*

// The REPL command line interface


object FlowAlt {
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

            if (args[0] == "q") break  // Quit the REPL on q

            if (args.size !in 2..3 && args[0] != "help") {
                println("ERROR: invalid command\n")
                continue
            }

            when (args[0]) {  // args: <command> <option>
                "list" -> ListHelper.choose(args[1], path)
                "create" -> CreateHelper.choose(args[1], path)
                "delete" -> DeleteHelper.choose(args[1], path)
                "log" -> LogHelper.choose(args[1], path)
                "modify" -> ModifyHelper.choose(args[1], path)
                "view" -> ViewHelper.choose(args[1], path)
                "help" -> help()
                else -> { println("ERROR: invalid command\n") }
            }

            println()
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

        // val syntaxLater = "SYNTAX: [list/create/delete/modify/log/dash/begin] -[x/f/c/e/p/d/r]"
        val syntax = "SYNTAX        |  [list/create/delete/modify/log/view] -[x/f/c/e/p/d]"
        val colors = "ELO COLOR KEY |  $styled14minus $styled15 $styled16 $styled17 $styled18 $styled19plus\n"
        println(syntax)
        println(colors)
    }

}