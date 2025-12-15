package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.ConceptE
import com.idris.database.DayE
import com.idris.database.ExamE
import com.idris.database.ExperimentE
import com.idris.database.FoundationE
import com.idris.database.FoundationsT
import com.idris.database.ProgressionE
import com.idris.database.ProgressionsT
import com.idris.model.objective.Objective
import com.idris.model.Skill
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

// Call an Idris operation (list/create/delete/log) for Foundation, Challenge, or Exam

abstract class Helper {
    val scanner = Scanner(System.`in`)
    val bar = "======================================================================================================="
    val barc = "$bar=================="


    // Input an operation option and call its appropriate function
    fun choose(option: String, datapath: String) {
        when(option) {
            "-f" -> f()
            "-c" -> c(datapath)
            "-e" -> e()
            "-p" -> p(datapath)
            else -> error("Invalid option")
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
                ConceptType.FOUNDATION  -> FoundationE.getOneNamed(name)
                ConceptType.CHALLENGE   -> ChallengeE.getOneNamed(name)
                ConceptType.EXAM        -> ExamE.getOneNamed(name)
                ConceptType.PROGRESSION -> ProgressionE.getOneNamed(name)
                ConceptType.DAY         -> DayE.getOneNamed(name)
                ConceptType.EXPERIMENT  -> ExperimentE.getOneNamed(name)
            } != null
        }
        return exists
    }


    // Fill an Objectives attributes from standard input
    fun fillObjectiveCore(o: Objective) {
        o.name = inputName()
        o.skill = Skill(inputSkill(), null)
        o.description = inputDescription()
        o.minutes = inputMinutes()
    }


    fun inputName() : String {
        // val s = Scanner(System.`in`)
        print("NAME  ")
        return scanner.next()
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
        for (i in 0..n) {
            var name = inputString(type.toString())
            while (!conceptExists(name, type)) {
                println("Not a valid ${type}.")
                name = inputString(type.toString())
            }
            entityNames + name
        }
        return entityNames
    }

}