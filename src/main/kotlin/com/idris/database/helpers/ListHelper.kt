package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ExamE
import com.idris.database.FoundationE
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

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
    // ======================================================================
}