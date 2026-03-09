package com.idris.system.extra

import com.idris.database.entities.*
import com.idris.system.concepts.Challenge
import com.idris.system.concepts.Concept
import com.idris.system.concepts.ConceptSymbols
import com.idris.system.concepts.Objective
import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.style
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.time.LocalDate
import java.util.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


object Util {
    val scanner = Scanner(System.`in`)

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
        printDef(OperationSymbols.CREATE, OpInfo.CREATE_DESC, style)
        printDef(OperationSymbols.DELETE, OpInfo.DELETE_DESC, style)
        printDef(OperationSymbols.MODIFY, OpInfo.MODIFY_DESC, style)
        printDef(OperationSymbols.VIEW, OpInfo.VIEW_DESC, style)
        printDef(OperationSymbols.LIST, OpInfo.LIST_DESC, style)
        printDef(OperationSymbols.LOG, OpInfo.LOG_DESC, style)
    }

    // Define all Idris entities for the user
    fun describeConcepts(style: String) {
        printDef(ConceptSymbols.EXPERIMENT, ConInfo.X_DESC, style)
        printDef(ConceptSymbols.DAY, ConInfo.D_DESC, style)
        printDef(ConceptSymbols.FOUNDATION, ConInfo.F_DESC, style)
        printDef(ConceptSymbols.CHALLENGE, ConInfo.C_DESC, style)
        printDef(ConceptSymbols.EXAM, ConInfo.E_DESC, style)
        printDef(ConceptSymbols.PROGRESSION, ConInfo.P_DESC, style)
        printDef("t", ConInfo.T_DESC, style)
        printDef(ConceptSymbols.RECORD, ConInfo.R_DESC, style)
    }

    // Prints a definition consisting of: a bolded string and its description
    private fun printDef(name: String, description: String, style: String) {
        println("  ${format(name, style, 3)} |  $description")
    }

    // Print a horizontal bar of the given char and width
    fun bar(c: CharSequence, w: Int): String {
        return c.repeat(w)
    }

    // Return true if there is a ConceptE of ConceptType type with the specified name in the database
    fun isInDB(conceptName: String, conceptType: ConceptType) : Boolean {
        var exists = false

        // check that a concept with the specified name exists in the database
        transaction {
            exists = when (conceptType) {
                ConceptType.FOUNDATION -> FOUNDATION.getOneNamed(conceptName)
                ConceptType.CHALLENGE -> CHALLENGE.getOneNamed(conceptName)
                ConceptType.EXAM -> EXAM.getOneNamed(conceptName)
                ConceptType.PROGRESSION -> PROGRESSION.getOneNamed(conceptName)
                ConceptType.DAY -> DAY.getOneNamed(conceptName)
                ConceptType.EXPERIMENT -> EXPERIMENT.getOneNamed(conceptName)
                ConceptType.RECORD -> RECORD.getOneNamed(conceptName)
                else -> { TODO("add tile eventually") }
            } != null
        }
        return exists
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

    // IMPROVED: same as inputName, but may return a null output
    fun inputNm(t: ConceptType, desiredState: ConceptState): String? {
        var name = inputString("NAME")
        if (name == "" || name == "q") return null  // return nothing if nothing is input

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
    fun inUndesiredState(conceptName: String, conceptType: ConceptType, desiredConceptState: ConceptState): Boolean {
        return when (desiredConceptState) {
            ConceptState.PRESENT -> !isInDB(conceptName, conceptType)
            ConceptState.ABSENT -> isInDB(conceptName, conceptType)
        }
    }

    fun inputInt(prompt: String) : Int? {
        val s = Scanner(System.`in`)
        print("$prompt  ")
        val num = readlnOrNull() ?: return null
        if (num == "") return null
        return num.toInt()
    }

    // Enter a 1 or a 0 and return the appropriate boolean value
    // - return null on invalid input
    fun inputBool(prompt: String): Boolean? {
        val v = inputInt(prompt)
        return if (v != 1 && v != 0) v == 1 else null
    }

    // Improved inputBool
    fun inputB(prompt: String): Boolean? {
        val vA = input(prompt) ?: return null  // value any
        if (vA == "") return null
        val v = vA.toString().toInt()
        if (v != 1 && v != 0) return null              // return on input other than 1 and 0
        return v == 1
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

    // IMPROVED: input
    fun inp(prompt: String): String? {
        print("$prompt  ")
        val v = readlnOrNull()
        if (v == "") return null
        return v
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

    // Return an array of n valid Idris Concept names of the specified ConceptType taken from standard input
    fun inputConceptNames(type: ConceptType, n: Int) : Array<String?> {
        println("\n[$type Names]")

        val entityNames = arrayOfNulls<String>(10)

        for (i in 0..< n) {
            var name = inputString(type.toString())

            if (name == "_") {  // rest day _
                entityNames[i] = name
                continue
            }

            if (name == "") break  // nothing

            while (!isInDB(name, type)) {
                println("$name does not exist among ${type}s.")
                name = inputString(type.toString())
            }

            entityNames[i] = name
        }

        return entityNames
    }


    // IMPROVED: fill the Concept with name, skill, and description from stdin (otherwise keep them null)
    fun inputConCor(concept: Concept, ct: ConceptType): Concept {
        val name = inputNm(ct, ConceptState.ABSENT)
        val skill = input("SKILL") as String?
        val description = input("DESCRIPTION") as String?

        if (name != "" && name != null) concept.name = name
        if (skill != "" && skill != null) concept.skillName = skill
        if (description != "" && description != null) concept.description = description

        return concept
    }


    // IMPROVED: Fill the Objective with name, skill, description, and minutes from stdin using inputConCor
    fun inputObjCor(objective: Objective, ct: ConceptType): Objective {
        inputConCor(objective, ct)
        val minutes = input("MINUTES") as String?
        if (minutes != "" && minutes != null) objective.minutes = minutes.toDouble()
        return objective
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
                else -> { TODO("add tile eventually") }
            }
            cE = conceptEntity
        }
        return cE
    }

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

    // Takes in a list of challenge names and returns an array of matching challenges from the db
    fun challengesFromNames(challengeNames: List<String?>): Array<Challenge?>{
        var ca: Array<Challenge?> = emptyArray()
        transaction {
            val l: Int = challengeNames.size
            ca = arrayOfNulls(l)  // challenge array

            var i = 0
            for (cName in challengeNames) {
                if (cName == "X" || cName == "" || cName == null) {  // null placeholder TODO (change to only "" later)
                    continue;
                }
                val cIterator = CHALLENGE.find { CHALLENGES.name eq cName }.iterator()
                val c = cIterator.next().deEntify()
                ca[i] = c
                i++
            }
        }
        return ca
    }

    // Return the current datetime
    @OptIn(ExperimentalTime::class)
    fun datetimeNow(): String {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }

    fun resultFromInput(): Double? {
        val resultS = inputString("RESULT  ")  // result string may be 1 or 0
        return if (resultS != "1" && resultS != "0") null else resultS.toDouble()
    }

    // Turn an operation symbol into an operation type (null if no match)
    fun toOpType(opSymbol: String): OperationType? {
        return when(opSymbol) {
            OperationSymbols.CREATE -> OperationType.CREATE
            OperationSymbols.DELETE -> OperationType.DELETE
            OperationSymbols.MODIFY -> OperationType.MODIFY

            OperationSymbols.LOG ->    OperationType.LOG
            OperationSymbols.LIST ->   OperationType.LIST
            OperationSymbols.VIEW ->   OperationType.VIEW

            OperationSymbols.ADMIN ->  OperationType.ADMIN
            OperationSymbols.HELP ->   OperationType.HELP
            else -> {null}
        }
    }

    // Turn a concept symbol into a concept type (null if no match)
    fun toConType(conSymbol: String): ConceptType? {
        return when(conSymbol) {
            ConceptSymbols.FOUNDATION ->  ConceptType.FOUNDATION
            ConceptSymbols.CHALLENGE ->   ConceptType.CHALLENGE
            ConceptSymbols.EXAM ->        ConceptType.EXAM
            ConceptSymbols.PROGRESSION -> ConceptType.PROGRESSION
            ConceptSymbols.DAY ->         ConceptType.DAY
            ConceptSymbols.EXPERIMENT ->  ConceptType.EXPERIMENT
            ConceptSymbols.RECORD ->      ConceptType.RECORD
            ConceptSymbols.TILE ->        ConceptType.TILE
            else -> {null}
        }
    }

    // Get the Operation of the given valid Idris command
    fun opSymbolOf(command: String): String {
        return command.substring(0, OperationSymbols.LEN)
    }

    // Get the Concept of the given valid Idris command
    fun conSymbolOf(command: String): String {
        return command.substring(OperationSymbols.LEN)
    }

}