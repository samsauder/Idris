package com.idris.system.extra

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CONCEPT
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.database.entities.RECORD
import com.idris.system.concepts.Concept
import com.idris.system.concepts.Objective
import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.style
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Scanner


object Util {
    val scanner = Scanner(System.`in`)
    val bar = "========================================"
    val barc = "$bar=================="

    // Print the name Idris
    fun idrisBanner() {
        println("    ____    __     _ ")
        println("   /  _/___/ /____(_)____")
        println("   / // __  / ___/ / ___/")
        println(" _/ // /_/ / /  / (__  )")
        println("/___/\\__,_/_/  /_/____/\n")
    }

    // Define all Idris operations for the user
    fun describeOperations(style: String) {
        printDef("create", OpInfo.CREATE_DESC, style)
        printDef("delete", OpInfo.DELETE_DESC, style)
        printDef("modify", OpInfo.MODIFY_DESC, style)
        printDef("view", OpInfo.VIEW_DESC, style)
        printDef("list", OpInfo.LIST_DESC, style)
        printDef("log", OpInfo.LOG_DESC, style)
    }

    // Define all Idris entities for the user
    fun describeConcepts(style: String) {
        printDef("x", ConInfo.X_DESC, style)
        printDef("d", ConInfo.D_DESC, style)
        printDef("f", ConInfo.F_DESC, style)
        printDef("c", ConInfo.C_DESC, style)
        printDef("e", ConInfo.E_DESC, style)
        printDef("p", ConInfo.P_DESC, style)
        printDef("t", ConInfo.T_DESC, style)
        printDef("r", ConInfo.R_DESC, style)
    }

    // Prints a definition consisting of: a bolded string and its description
    private fun printDef(name: String, description: String, style: String) {
        println("  ${format(name, style, 8)} |  $description")
    }

    // Print a horizontal bar of the given char and width
    fun bar(c: CharSequence, w: Int): String {
        return c.repeat(w)
    }

    // Return true if there is a ConceptE of ConceptType type with the specified name in the database
    fun conceptExists(name: String, type: ConceptType) : Boolean {
        var exists = false

        // check that a concept with the specified name exists in the database
        transaction {
            exists = when (type) {
                ConceptType.FOUNDATION -> FOUNDATION.getOneNamed(name)
                ConceptType.CHALLENGE -> CHALLENGE.getOneNamed(name)
                ConceptType.EXAM -> EXAM.getOneNamed(name)
                ConceptType.PROGRESSION -> PROGRESSION.getOneNamed(name)
                ConceptType.DAY -> DAY.getOneNamed(name)
                ConceptType.EXPERIMENT -> EXPERIMENT.getOneNamed(name)
                ConceptType.RECORD -> RECORD.getOneNamed(name)
            } != null
        }
        return exists
    }

    // Fill an Objectives attributes from standard input
    fun fillObjectiveCore(o: Objective, t: ConceptType) {
        // o.name = inputName(t, ConceptState.ABSENT)
        // o.skillName = inputSkill()
        // o.description = inputDescription()
        fillConceptCore(o, t)
        o.minutes = inputMinutes()
    }

    // Fill a Concept's attributes from standard input
    fun fillConceptCore(c: Concept, t: ConceptType) {
        val n = inputName(t, ConceptState.ABSENT)
        val s = inputSkill()
        val d = inputDescription()
        c.name = n
        c.skillName = s
        c.description = d
    }


    // Takes a name of a Concept from standard input and verifies that the target ConceptState is true (EXISTS or ABSENT)
    fun inputName(t: ConceptType, desiredState: ConceptState) : String {
        var name = inputString("?")
        if (name == "" || name == "q") return ""  // return nothing if nothing is input

        while (inUndesiredState(name, t, desiredState)) {
            println("${error()}  ${style(name, Styles.BOLD)} not $desiredState in the $t table.\n")
            name = inputName(t, desiredState)
        }
        return name
    }

    // Return the string ERROR in red
    fun error(): String{
        return style("ERROR", Styles.RED)
    }


    // Return true if a named concept should be PRESENT from its table but is ABSENT, false otherwise
    // Return true if a named concept should be ABSENT from its table but is PRESENT, false otherwise
    // ARGS
    // * n: target concept name
    // * t: target concept type
    // * s: desired concept state
    fun inUndesiredState(conceptName: String, conceptType: ConceptType, desiredConceptState: ConceptState): Boolean {
        return when (desiredConceptState) {
            ConceptState.PRESENT -> !conceptExists(conceptName, conceptType)
            ConceptState.ABSENT -> conceptExists(conceptName, conceptType)
        }
    }


    fun inputSkill() : String {
        // val s = Scanner(System.`in`)
        print("SKILL  ")
        return scanner.next()
    }


    fun inputDescription() : String {
        val s = Scanner(System.`in`)
        print("DESCRIPTION  ")
        return s.nextLine()
    }


    fun inputMinutes() : Double {
        val s = Scanner(System.`in`)
        print("MINUTES  ")
        return s.nextInt().toDouble()
    }


    fun inputInt(prompt: String) : Int? {
        val s = Scanner(System.`in`)
        print("$prompt  ")
        val num = readlnOrNull() ?: return null
        if (num == "") return null
        return num.toInt()
    }


    fun inputChallenge(i: Int) : String {
        // val s = Scanner(System.`in`)
        print("CHALLENGE$i  ")
        return scanner.next()
    }


    fun inputProgression() : String {
        val s = Scanner(System.`in`)
        print("PROGRESSION  ")
        return scanner.next()
    }


    // Prints a prompt string and returns a string taken in from standard input
    fun inputString(prompt: String) : String {
        print("$prompt  ")
        return readlnOrNull() ?: ""
    }

    // Nullable inputString
    fun inputStringN(prompt: String): String? {
        print("$prompt  ")
        val input = readlnOrNull()
        return if (input == "") null else input
    }

    // Returns input of Any type from stdin or null
    fun input(prompt: String): Any? {
        print("$prompt  ")
        return readlnOrNull()
    }


    // Returns an array of Strings/nulls from stdin
    fun inputStrings(prompt: String, count: Int): Array<String?> {
        val strings = arrayOfNulls<String>(count)  // array of count number of Any type values

        for (i in 0..< count) {
            val string = inputStringN("$prompt $i") ?: break  // if null, break
            strings[i] = string
        }
        return strings
    }


    // Returns an array of Ints/nulls from stdin
    fun inputInts(prompt: String, count: Int): Array<Int?> {
        val ints = arrayOfNulls<Int>(count)  // array of count number of Any type values

        for (i in 0..< count) {
            val int = inputInt("$prompt $i") ?: break  // if null, break
            ints[i] = int
        }
        return ints
    }

    // Prints a prompt string a returns a double taken from stdin
    fun inputBigDecimal(prompt: String): BigDecimal {
        print("$prompt  ")
        return scanner.nextBigDecimal()
    }

    // Return an array of n valid Idris Concept names of the specified ConceptType taken from standard input
    fun inputConceptNames(type: ConceptType, n: Int) : Array<String?> {
        println("Input all $type names (X for null, _ for rest)")

        val entityNames = arrayOfNulls<String>(10)
        for (i in 0..< n) {
            var name = inputString(type.toString())
            if (name == "X" || name == "_") {  // empty (X) or rest day (_)
                entityNames[i] = name
                continue
            }
            while (!conceptExists(name, type)) {
                println("Not a valid ${type}.")
                name = inputString(type.toString())
            }
            entityNames[i] = name
        }

        return entityNames
    }


    // Return a concept of the specified ConceptType and name from its table (or null if not found)
    fun getConceptEntity(ct: ConceptType, name: String): CONCEPT? {
        var cE: CONCEPT? = null
        transaction {
            val conceptEntity: CONCEPT = when (ct) {
                ConceptType.FOUNDATION -> {
                    FOUNDATION.Companion.getOneNamed(name)!!
                }

                ConceptType.CHALLENGE -> {
                    CHALLENGE.Companion.getOneNamed(name)!!
                }

                ConceptType.EXAM -> {
                    EXAM.Companion.getOneNamed(name)!!
                }

                ConceptType.DAY -> {
                    DAY.Companion.getOneNamed(name)!!
                }

                ConceptType.PROGRESSION -> {
                    PROGRESSION.Companion.getOneNamed(name)!!
                }

                ConceptType.EXPERIMENT -> {
                    EXPERIMENT.getOneNamed(name)!!
                }
                // ConceptType.RECORD -> {}
                ConceptType.RECORD -> RECORD.getOneNamed(name)!!
            }
            cE = conceptEntity
        }
        return cE
    }

    /*
    // Print the result of a recent challenge attempt
    fun printResult(name: String, won: Boolean) {
        val sym = if (won) style("[+]", Styles.GREEN) else style("[-]", Styles.RED)
        val nameB = style(name, Styles.BOLD)
        val date = "${LocalDate.now()}"
        println("$sym $nameB on $date.")
    }*/

    // Return a win symbol or a loss symbol
    private fun resultSym(won: Boolean): String {
        return if (won) {
            style("[+]", Styles.GREEN)
        } else {
            style("[-]", Styles.RED)
        }
    }

    // Print win/loss, name, and date of the objective attempt
    fun printAttempt(objName: String, won: Boolean) {
        val objNameS = style(objName, Styles.BOLD)  // objective name (styled)
        println("${resultSym(won)} $objNameS on ${LocalDate.now()}")
    }


}



// TODO  get rid of all redundant functions