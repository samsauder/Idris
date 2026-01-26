package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CONCEPT
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.system.concepts.Objective
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

abstract class Operator {
    val scanner = Scanner(System.`in`)
    val bar = "========================================"
    val barc = "$bar=================="

    abstract fun f();  // call the operation for Foundation
    abstract fun c(datapath: String);  // call the operation for Challenge
    abstract fun e();  // call the operation for Exam
    abstract fun x();  // call the operation for Experiment
    abstract fun d();  // call the operation for Day
    abstract fun p(datapath: String);  // call the operation for Progression

    // Print a horizontal bar of the given char and width
    fun bar(c: CharSequence, w: Int): String {
       return c.repeat(w)
    }

    // Input an operation option and call its appropriate function
    fun choose(option: String, datapath: String) {
        when(option) {
            "-f" -> f()  // foundation
            "-c" -> c(datapath)  // challenge
            "-e" -> e()  // exam
            "-p" -> p(datapath)  // progression
            "-d" -> d()  // day
            "-x" -> x()  // experiment
            else -> println("ERROR: invalid command")
        }
    }

    // Return true if there is a ConceptE of ConceptType type with the specified name in the database
    fun conceptExists(name: String, type: ConceptType) : Boolean {
        var exists = false

        // check that a concept with the specified name exists in the database
        transaction {
            exists = when (type) {
                ConceptType.FOUNDATION -> FOUNDATION.Companion.getOneNamed(name)
                ConceptType.CHALLENGE -> CHALLENGE.Companion.getOneNamed(name)
                ConceptType.EXAM -> EXAM.Companion.getOneNamed(name)
                ConceptType.PROGRESSION -> PROGRESSION.Companion.getOneNamed(name)
                ConceptType.DAY -> DAY.Companion.getOneNamed(name)
                ConceptType.EXPERIMENT -> EXPERIMENT.Companion.getOneNamed(name)
            } != null
        }
        return exists
    }

    // Fill an Objectives attributes from standard input
    fun fillObjectiveCore(o: Objective, t: ConceptType) {
        o.name = inputName(t, ConceptState.ABSENT)
        o.skillName = inputSkill()
        o.description = inputDescription()
        o.minutes = inputMinutes()
    }


    // Takes a name of a Concept from standard input and verifies that the target ConceptState is true (EXISTS or ABSENT)
    fun inputName(t: ConceptType, desiredState: ConceptState) : String {
        // val s = Scanner(System.`in`)
        // print("NAME  ")
        // var name = scanner.next()
        var name = inputString("?")
        if (name == "") return ""  // return nothing if nothing is input

        while (inUndesiredState(name, t, desiredState)) {
            println("ERROR: '${name}' not $desiredState in the $t table.\n")
            name = inputName(t, desiredState)
        }
        return name
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


    fun inputInteger(prompt: String) : Int? {
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


    // Return an array of n valid Idris Concept names of the specified ConceptType taken from standard input
    fun inputConceptNames(type: ConceptType, n: Int) : Array<String?> {
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
            }
            cE = conceptEntity
        }
        return cE
    }

}