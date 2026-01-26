package com.idris.database.operators.todo

import java.util.Scanner


abstract class Operator {
    val scanner = Scanner(System.`in`)

    abstract fun f();  // call the operation for Foundation
    abstract fun c(datapath: String);  // call the operation for Challenge
    abstract fun e();  // call the operation for Exam
    abstract fun x();  // call the operation for Experiment
    abstract fun d();  // call the operation for Day
    abstract fun p(datapath: String);  // call the operation for Progression

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
}