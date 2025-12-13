package com.idris.database.helpers

import com.idris.database.ChallengeE
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
                foundation.printShort(0);
            }
        }
    }
    // ======================================================================
    override fun c() {
        transaction {
            println("CHALLENGES")
            println(barc)
            for (challengeEntity in ChallengeE.Companion.all()) {
                val challenge = challengeEntity.deEntify()
                challenge.printShort(0)
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
                exam.printShort(0)
            }
        }
    }

    override fun x() {
        TODO("Not yet implemented")
    }

    // ======================================================================

    override fun p(datapath: String) {
        transaction {
            println("PROGRESSIONS")
            println(bar)
            for (progressionEntity in ProgressionE.Companion.all()) {
                val progression = progressionEntity.deEntify(datapath);
                progression.print()
                println()
            }
        }
    }
    // ======================================================================
}