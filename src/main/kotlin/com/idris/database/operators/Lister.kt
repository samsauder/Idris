package com.idris.database.operators

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// Call the Idris 'list' operation for Foundation, Challenge, or Exam


object Lister : Operator() {
    // fun list(ct: ConceptType) {}

    // ======================================================================
    override fun f() {
        transaction {
            println("FOUNDATIONS")
            println(bar)
            for (foundationEntity in FOUNDATION.Companion.all()) {
                val foundation = foundationEntity.deEntify()
                foundation.printL()
            }
        }
    }
    // ======================================================================
    override fun c(datapath: String) {
        transaction {
            println("CHALLENGES")
            println(barc)
            for (challengeEntity in CHALLENGE.Companion.all()) {
                val challenge = challengeEntity.deEntify()
                challenge.printL()
            }
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            println("EXAMS")
            println(bar)
            for (examEntity in EXAM.Companion.all()) {
                val exam = examEntity.deEntify();
                exam.printL()
            }
        }
    }
    // ======================================================================
    override fun x() {
        // TODO("Not yet implemented")
        println("EXPERIMENTS")
        println(barc)
        transaction {
            for (expEntity in EXPERIMENT.Companion.all()) {
                val day = expEntity.deEntify();
                day.printL()
            }
        }
    }
    // ======================================================================
    override fun d() {
        // TODO("Not yet implemented")
        println("DAYS")
        println(barc)
        transaction {
            for (dayEntity in DAY.Companion.all()) {
                val day = dayEntity.deEntify();
                day.printL()
            }
        }
    }
    // ======================================================================
    override fun p(datapath: String) {
        println("PROGRESSIONS")
        println(barc)
        transaction {
            for (progressionEntity in PROGRESSION.Companion.all()) {
                // val progression = progressionEntity.deEntify(datapath);
                val progression = progressionEntity.deEntify();
                progression.printL()
            }
        }
    }
    // ======================================================================



}