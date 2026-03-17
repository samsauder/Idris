package com.idris

import com.idris.database.operators.Controller
import com.idris.database.operators.todo.Creator
import com.idris.database.operators.todo.Modifier
import com.idris.system.concepts.ConceptSymbols
import com.idris.system.extra.ConceptType
import com.idris.system.extra.OperationType
import com.idris.system.extra.OperationSymbols
import com.idris.system.extra.Privilege
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util
import com.idris.system.extra.Util.conSymbolOf
import com.idris.system.extra.Util.opSymbolOf
import com.idris.system.extra.Util.toConType
import com.idris.system.extra.Util.toOpType


object Flow {
    // Start an Idris REPL
    fun repl(path: String) {
        val d = Database(path)
        d.connect()

        val version = "1.0.0"
        // val quote = "The excellence of the soul is understanding\nfor one who understands\nis conscious, devoted, and already godlike."

        Util.idrisBanner()  // print the name Idris (stylized)
        println("Version ${version}\n\n")

        // print(style(quote, Styles.YELLOW))
        // println("  — Hermes Trismegistus\n\n")

        var input: String?
        var inputPrev: String? = ""  // last entered command

        while (true) {
            print("$ ")
            input = readlnOrNull()

            if (input == OperationSymbols.QUIT) {         // QUIT
                break
            } else if (input == OperationSymbols.HELP) {  // HELP
                Controller.help()
                println("\n")
                continue
            } else if (input == OperationSymbols.ADMIN) {  // ADMIN
                Controller.admin()
                println("\n")
                continue
            } else if (input == OperationSymbols.STATE) {
                Controller.state()
                println("\n")
                continue
            } else if (input == "") {   // previously entered command
                input = inputPrev
            } else {                    // core command
                val inputIsValid = checkValid(input!!)
                if (!inputIsValid) continue
            }

            val ot: OperationType = toOpType(opSymbolOf(input!!))!!
            val ct: ConceptType = toConType(conSymbolOf(input))!!

            exec(ot, ct)

            println("\n")

            if (input != "") inputPrev = input
        }
    }


    // Executes the passed Idris command and returns true on success
    fun exec(operation: OperationType, concept: ConceptType): Boolean {
        val firstChar = concept.toString().substring(0, 1).lowercase()  // workaround until Creator and Modifier are dissolved

        when (operation) {
            OperationType.CREATE -> Creator.choose(firstChar, "")   // TODO switch with Controller equivalent
            OperationType.MODIFY -> Modifier.choose(firstChar, "")  // TODO switch with Controller equivalent
            OperationType.DELETE -> Controller.delete(concept)     // DELETE

            OperationType.LOG ->    Controller.log(concept)        // LOG
            OperationType.LIST ->   Controller.list(concept)       // LIST
            OperationType.VIEW ->   Controller.view(concept)       // VIEW
            OperationType.STATE -> Controller.state()                 // STATE
            else -> null
        }
        return true
    }


    // Return true if the command string is a valid Idris command
    private fun checkValid(command: String): Boolean {
        if (command.split(" ").size > 1) {  // check that command is a single word
            println("${Util.error()}  Command must not have spaces.\n\n")
            return false
        }

        val o: String = opSymbolOf(command)
        val c: String = conSymbolOf(command)

        if (Controller.mode == Privilege.USER) {  // check that the operation is authorized for the user
            val adminOperations = listOf(OperationSymbols.CREATE,
                                         OperationSymbols.DELETE,
                                         OperationSymbols.MODIFY)

            if (adminOperations.contains(o)) {
                println("${Util.error()}  Unauthorized.\n\n")
                return false
            }
        }

        // check that the operation and concept specified exist
        val opExists = OperationSymbols.all.contains(o)
        val conExists = ConceptSymbols.all.contains(c)

        if (opExists && conExists) {
            return true
        } else {
            println("${Util.error()}  Invalid command.\n\n")
            return false
        }
    }
}