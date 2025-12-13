package com.idris.database.helpers

import com.idris.constants.Styles
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
    override fun c(datapath: String) {
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
        val styled15minus = "[...1599]"
        val styled16 = "${Styles.GREEN}[1600-1699]${Styles.RESET}"
        val styled17 = "${Styles.BLUE}[1700-1799]${Styles.RESET}"
        val styled18 = "${Styles.YELLOW}[1800-1899]${Styles.RESET}"
        val styled19plus = "${Styles.RED}[1900...]${Styles.RESET}"

        println("CHALLENGE ELO KEY: $styled15minus $styled16 $styled17 $styled18 $styled19plus\n");
        println("PROGRESSIONS")
        println(barc)
        transaction {
            for (progressionEntity in ProgressionE.Companion.all()) {
                val progression = progressionEntity.deEntify(datapath);
                progression.print()
                //println()
            }
        }
    }
    // ======================================================================
}