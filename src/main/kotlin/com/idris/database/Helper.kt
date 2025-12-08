package com.idris.database

import com.idris.model.Objective
import com.idris.model.Skill
import java.util.Scanner

// Each Helper provides helper methods for one of the following database operations:
// - create
// - list
// - delete
// - log

abstract class Helper {
    val bar = "========================================"
    val barc = "$bar==================="
    
    // Input an option and call its appropriate function
    fun choose(option: String) {
        when(option) {
            "-f" -> f()
            "-c" -> c()
            "-e" -> e()
            else -> error("Invalid option")
        }
    }

    abstract fun f();  // call the operation for a Foundation
    abstract fun c();  // call the operation for a Challenge
    abstract fun e();  // call the operation for an Exam

    // Fill an Objectives attributes from standard input
    fun fillObjectiveCore(o: Objective) {
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
        o.minutes = z.nextDouble()
    }

}