package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.database.entities.RECORD
import com.idris.system.concepts.Challenge
import com.idris.system.concepts.Exam
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util
import com.idris.system.extra.Util.inputBigDecimal
import com.idris.system.extra.Util.inputChallenge
import com.idris.system.extra.Util.inputConceptNames
import com.idris.system.extra.Util.inputDescription
import com.idris.system.extra.Util.inputInteger
import com.idris.system.extra.Util.inputMinutes
import com.idris.system.extra.Util.inputName
import com.idris.system.extra.Util.inputProgression
import com.idris.system.extra.Util.inputSkill
import com.idris.system.extra.Util.inputString
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner


object Creator : Operator() {
    // =======================================================================================================
    override fun f() {  // add a FoundationE to the database
        transaction {
            FOUNDATION.new {
                name = inputName(ConceptType.FOUNDATION, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                println("\nAdded ${style(name, Styles.BOLD)} to the Foundation table.")
            }
        }
    }
    // =======================================================================================================
    override fun c(datapath: String) {  // add a ChallengeE to the database
        val s = Scanner(System.`in`)
        transaction {
            CHALLENGE.new {
                name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                progressionName = inputProgression()
                cElo = 0.0.toBigDecimal()
                uElo = 0.0.toBigDecimal()
                uOdds = 1.0.toBigDecimal()
                attempts = 0
                wins = 0
                println("\nAdded ${style(name, Styles.BOLD)} to the Challenge table.")
            }
        }
    }
    // =======================================================================================================
    override fun e() {  // add an ExamE to the database
        transaction {
            EXAM.new {
                name = inputName(ConceptType.EXAM, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                passed = false
                println("\nAdded ${style(name, Styles.BOLD)} to the Exam table.")
            }
        }
    }
    // =======================================================================================================
    override fun x() {
        val s = Scanner(System.`in`)
        transaction {
            EXPERIMENT.new {
                name = inputName(ConceptType.EXPERIMENT, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")

                val dnms = inputConceptNames(ConceptType.DAY, 7)  // Day names

                d1 = dnms[0]!!
                d2 = dnms[1]!!
                d3 = dnms[2]!!
                d4 = dnms[3]!!
                d5 = dnms[4]!!
                d6 = dnms[5]!!
                d7 = dnms[6]!!
                segCount = inputInteger("SEGMENT COUNT")!!
                println("\nAdded ${style(name, Styles.BOLD)} to the Experiment table.")
            }
        }
    }
    // =======================================================================================================
    override fun d() {
        transaction {
            DAY.new {
                name = inputName(ConceptType.DAY, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")

                val fnms = inputConceptNames(ConceptType.FOUNDATION, 10)  // Foundation names
                val pnms = inputConceptNames(ConceptType.PROGRESSION, 5)  // Progression names

                f0 = fnms[0]!!
                f1 = fnms[1]!!
                f2 = fnms[2]!!
                f3 = fnms[3]!!
                f4 = fnms[4]!!
                f5 = fnms[5]!!
                f6 = fnms[6]!!
                f7 = fnms[7]!!
                f8 = fnms[8]!!
                f9 = fnms[9]!!

                p0 = pnms[0]!!
                p1 = pnms[1]!!
                p2 = pnms[2]!!
                p3 = pnms[3]!!
                p4 = pnms[4]!!
                println("\nAdded ${style(name, Styles.BOLD)} to the Day table.")
            }
        }
    }
    // =======================================================================================================
    override fun p(datapath: String) {
        transaction {
            PROGRESSION.new {
                name = inputName(ConceptType.PROGRESSION, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputDescription()
                c0 = inputChallenge(0)
                c1 = inputChallenge(1)
                c2 = inputChallenge(2)
                c3 = inputChallenge(3)
                c4 = inputChallenge(4)
                c5 = inputChallenge(5)
                c6 = inputChallenge(6)
                c7 = inputChallenge(7)
                c8 = inputChallenge(8)
                c9 = inputChallenge(9)
                println("\nAdded ${style(name, Styles.BOLD)} to the Progression table.")
            }
        }
    }
    // =======================================================================================================
    fun r(rN: String,
          sN: String,
          de: String,
          oN: String,
          w: Boolean,
          da: String) {  // create RECORD
        transaction {
            RECORD.new {
                name = rN           // custom tag
                skillName = sN      // associated skill
                description = de    // description
                objectiveName = oN  // name of completed f/c/e
                won = w             // success/failure
                date = da           // date completed
            }
        }
    }
}