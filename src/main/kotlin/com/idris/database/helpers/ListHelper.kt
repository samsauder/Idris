package com.idris.database.helpers

import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.DayE
import com.idris.database.ExamE
import com.idris.database.FoundationE
import com.idris.database.ProgressionE
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// Call the Idris 'list' operation for Foundation, Challenge, or Exam


object ListHelper : Helper() {
    // ======================================================================
    override fun f() {
        transaction {
            println("FOUNDATIONS")
            println(bar)
            for (foundationEntity in FoundationE.Companion.all()) {
                val foundation = foundationEntity.deEntify()
                foundation.printL()
                // foundation.printShort(0);
            }
        }
    }
    // ======================================================================
    override fun c(datapath: String) {
        transaction {
            println("CHALLENGES")
            println(barc)
            for (challengeEntity in ChallengeE.Companion.all()) {
                val challenge = challengeEntity.deEntify()
                // challenge.printShort(0)
                challenge.printL()
            }
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            println("EXAMS")
            println(bar)
            for (examEntity in ExamE.Companion.all()) {
                val exam = examEntity.deEntify();
                exam.printL()
                // exam.printShort(0)
            }
        }
    }
    // ======================================================================
    override fun x() {
        TODO("Not yet implemented")
    }
    // ======================================================================
    override fun d() {
        // TODO("Not yet implemented")
        println("DAYS")
        println(barc)
        transaction {
            for (dayEntity in DayE.Companion.all()) {
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
            for (progressionEntity in ProgressionE.Companion.all()) {
                val progression = progressionEntity.deEntify(datapath);
                progression.printL()
            }
        }
    }
    // ======================================================================
}