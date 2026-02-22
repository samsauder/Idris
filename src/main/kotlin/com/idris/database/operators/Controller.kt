package com.idris.database.operators

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.CONCEPT
import com.idris.database.entities.PROGRESSION
import com.idris.database.operators.todo.Creator
import com.idris.system.concepts.Concept
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util
import com.idris.system.extra.Util.bar
import com.idris.system.extra.Util.getConceptEntity
import com.idris.system.extra.Util.inputName
import com.idris.system.extra.Util.inputString
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// The Controller is an abstraction used to operate on database concepts
// <targets>    f/c/e/p/d/x
// <operators>  list/create/delete/modify/log/view


object Controller {
    const val WIDTH = 140  // table width
    const val BAR_CHAR = "="

    // ======================================================================
    fun help() {
        val ostyle = Styles.ITALIC  // styling for operation definitions
        val cstyle = Styles.BOLD  // styling for concept definitions

        val operation = style("operation", Styles.ITALIC)
        val concept = style("concept", Styles.BOLD)

        val syntax = "SYNTAX   <$operation> <$concept>"

        println("\n${bar(BAR_CHAR, WIDTH)}")
        println(syntax)

        println()
        Util.describeOperations(ostyle)
        println()
        Util.describeConcepts(cstyle)

        println(bar("-", WIDTH))

        // COLORS
        val styled14minus = "14XX-"
        val styled15 = "${Styles.BLUE}15XX${Styles.RESET}"
        val styled16 = "${Styles.GREEN}16XX${Styles.RESET}"
        val styled17 = "${Styles.YELLOW}17XX${Styles.RESET}"
        val styled18plus = "${Styles.RED}18XX+${Styles.RESET}"

        val colors = "ELO      $styled14minus $styled15 $styled16 $styled17 $styled18plus"

        println(colors)
        println(bar(BAR_CHAR, WIDTH))
    }
    // ======================================================================
    fun list(t: ConceptType) {  // list all concepts of a given type
        println("\n${t}S")
        println(bar(BAR_CHAR, WIDTH))

        transaction {
            for (c in CONCEPT.getAll(t))  c.deEntify().printL()
        }
        println(bar(BAR_CHAR, WIDTH))
    }
    // ======================================================================
    fun create(t: ConceptType) { TODO() }
    // ======================================================================
    fun delete(t: ConceptType) {
        transaction {
            val name = inputName(t, ConceptState.PRESENT)
            val conceptE = getConceptEntity(t, name)
            conceptE?.delete()
            println("\nDeleted ${style(name, Styles.BOLD)} from the $t table.")
        }
    }
    // ======================================================================
    fun modify() { TODO() }
    // ======================================================================

    @OptIn(ExperimentalTime::class)
    fun log(ot: ObjectiveType) {
        transaction {
            //val resultS = inputString("[RESULT]  ")  // result string: 1 or 0
            //if (resultS != "1" && resultS != "0") return@transaction
            // val result = resultS.toDouble()
            // val result = resultFromInput()
            // val won: Boolean = resultS == "win"

            when(ot) {
                ObjectiveType.CHALLENGE -> {  // log challenge
                    val cName = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)
                    val cResult = resultFromInput() ?: return@transaction  // return if null
                    val challenge = CHALLENGE.getOneNamed(cName)?.deEntify()

                    if (challenge?.progressionName == "X") {  // challenge has no progression
                        /*
                        challenge.update(won)
                        CHALLENGE.findSingleByAndUpdate(CHALLENGES.name eq name) {
                            it.cElo = BigDecimal.valueOf(challenge.challengeElo)
                            it.uElo = BigDecimal.valueOf(challenge.userElo)
                            it.uOdds = BigDecimal.valueOf(challenge.userOdds)
                            it.attempts++
                            it.wins += result.toInt()
                            it.progressionName = challenge.progressionName
                        }  // update info
                        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                        Creator.r(
                            "${name}___${datetime}",
                            challenge.skillName,
                            "",
                            name,
                            won,
                            datetime)  // add record to the record table

                        */
                        // val result = if (won) 1.0 else 0.0
                        Logger.updateChallenge(challenge, cResult)
                    } else {                          // challenge has a progression
                        val progression = PROGRESSION.getOneNamed(challenge!!.progressionName)?.deEntify()
                        // val resultNum = if (result == "win") 1.0 else 0.0
                        // val result = resultS.toDouble()
                        progression?.massLog(challenge.name, cResult)
                    }
                }

                ObjectiveType.EXAM -> {
                    val eName = inputName(ConceptType.EXAM, ConceptState.PRESENT)
                    val eResult = resultFromInput() ?: return@transaction
                    val exam = EXAM.getOneNamed(eName)?.deEntify()
                    Logger.updateExam(exam!!, eResult)
                }

                ObjectiveType.FOUNDATION -> {
                    val fName = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)
                    val foundation = FOUNDATION.getOneNamed(fName)?.deEntify()
                    Logger.updateFoundation(foundation!!)
                }
            }
        }
    }

    fun resultFromInput(): Double? {
        val resultS = inputString("RESULT  ")  // result string may be 1 or 0
        // println()
        return if (resultS != "1" && resultS != "0") null else resultS.toDouble()
    }

    // ======================================================================
    fun view(t: ConceptType) {
        val name = inputName(t, ConceptState.PRESENT)
        if (name == "") return  // user quits

        var concept: Concept? = null

        println("\n${name}")
        println(bar(BAR_CHAR, WIDTH))

        transaction {
            concept = CONCEPT.conceptNamed(t, name).deEntify()
        }
        concept?.print()
        println(bar(BAR_CHAR, WIDTH))
    }
    // ======================================================================
    fun dash() {  // See an overview of your statistics across skills
        TODO()
        /* WORK-IN-PROGRESS
        println("\nDASHBOARD")
        println(bar(BAR_CHAR, WIDTH))

        println("")

        transaction {  // print the progressions of each skill
            // val f: CONCEPT = CONCEPT.conceptsOfSkill(ConceptType.CHALLENGE, )
        }
         */
    }
}