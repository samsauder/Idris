package com.idris.database.operators

import com.idris.database.CHALLENGE
import com.idris.database.CONCEPT
import com.idris.database.DAY
import com.idris.database.EXAM
import com.idris.database.EXPERIMENT
import com.idris.database.FOUNDATION
import com.idris.database.PROGRESSION
import com.idris.model.objective.Objective
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

// Call an Idris operation (list/create/delete/log) for Foundation, Challenge, or Exam

abstract class Operator {
    val scanner = Scanner(System.`in`)
    // val bar = "======================================================================================================="
    val bar = "========================================"
    val barc = "$bar=================="


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


    abstract fun f();  // call the operation for Foundation
    abstract fun c(datapath: String);  // call the operation for Challenge
    abstract fun e();  // call the operation for Exam
    abstract fun x();  // call the operation for Experiment
    abstract fun d();  // call the operation for Day
    abstract fun p(datapath: String);  // call the operation for Progression


    // Return true if there is a ConceptE of ConceptType type with the specified name in the database
    fun conceptExists(name: String, type: ConceptType) : Boolean {
        var exists = false

        // check that a concept with the specified name exists in the database
        transaction {
            exists = when (type) {
                ConceptType.FOUNDATION  -> FOUNDATION.getOneNamed(name)
                ConceptType.CHALLENGE   -> CHALLENGE.getOneNamed(name)
                ConceptType.EXAM        -> EXAM.getOneNamed(name)
                ConceptType.PROGRESSION -> PROGRESSION.getOneNamed(name)
                ConceptType.DAY         -> DAY.getOneNamed(name)
                ConceptType.EXPERIMENT  -> EXPERIMENT.getOneNamed(name)
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
        print("NAME  ")
        var name = scanner.next()

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
        val s = Scanner(System.`in`);
        print("$prompt  ")
        return scanner.next();
    }


    // Return an array of n valid Idris Concept names of the specified ConceptType taken from standard input
    fun inputConceptNames(type: ConceptType, n: Int) : Array<String?> {
        val entityNames = arrayOfNulls<String>(10)
        for (i in 0..< n) {
            var name = inputString(type.toString())
            if (name == "X") {
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
                ConceptType.FOUNDATION -> {FOUNDATION.getOneNamed(name)!!}
                ConceptType.CHALLENGE -> {CHALLENGE.getOneNamed(name)!!}
                ConceptType.EXAM -> {EXAM.getOneNamed(name)!!}
                ConceptType.DAY -> {DAY.getOneNamed(name)!!}
                ConceptType.PROGRESSION -> {PROGRESSION.getOneNamed(name)!!}
                ConceptType.EXPERIMENT -> {EXPERIMENT.getOneNamed(name)!!}
                // ConceptType.RECORD -> {}
            }
            cE = conceptEntity
        }
        return cE
    }

}