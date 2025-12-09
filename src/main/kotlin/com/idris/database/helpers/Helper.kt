package com.idris.database.helpers

import com.idris.model.objective.Objective
import com.idris.model.Skill
import java.util.Scanner

// Call an Idris operation (list/create/delete/log) for Foundation, Challenge, or Exam

abstract class Helper {
    val scanner = Scanner(System.`in`)
    val bar = "====================================================="
    val barc = "$bar=================="

    // Input an operation option and call its appropriate function
    fun choose(option: String) {
        when(option) {
            "-f" -> f()
            "-c" -> c()
            "-e" -> e()
            else -> error("Invalid option")
        }
    }

    abstract fun f();  // call the operation for Foundation
    abstract fun c();  // call the operation for Challenge
    abstract fun e();  // call the operation for Exam

    // Fill an Objectives attributes from standard input
    fun fillObjectiveCore(o: Objective) {
        /*
        val s = Scanner(System.`in`)

        print("NAME  ")
        o.name = s.next()

        print("SKILL  ")
        o.skill = Skill(s.next(), null)

        print("DESCRIPTION  ")
        val z = Scanner(System.`in`)
        o.description = z.nextLine()

        print("MINUTES  ")
        val w = Scanner(System.`in`)
        o.minutes = z.nextDouble()*/

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
}