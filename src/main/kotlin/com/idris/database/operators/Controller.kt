package com.idris.database.operators

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.CONCEPT
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXAMS
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSION
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
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.time.ExperimentalTime

// The Controller is used to operate on database concepts through the text interface


object Controller {
    const val WIDTH = 140  // table width
    const val BAR_CHAR = "―"


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

        val colors = "ELO      $styled14minus  $styled15  $styled16  $styled17  $styled18plus"

        println(colors)
        println(bar(BAR_CHAR, WIDTH))
    }


    fun create(t: ConceptType) { TODO("Not yet implemented") }


    fun delete(t: ConceptType) {
        transaction {
            val name = inputName(t, ConceptState.PRESENT)
            val conceptE = getConceptEntity(t, name)
            conceptE?.delete()
            println("\nDeleted ${style(name, Styles.BOLD)} from the $t table.")
        }
    }


    fun modify() { TODO("Not yet implemented") }


    fun view(t: ConceptType) {
        val name = inputName(t, ConceptState.PRESENT)
        if (name == "") return  // user quits

        var concept: Concept? = null

        transaction {
            concept = CONCEPT.conceptNamed(t, name).deEntify()
        }

        val iconS = style("${concept?.icon}", Styles.GREEN)
        println("\n$iconS  $name")
        println(bar(BAR_CHAR, WIDTH))

        concept?.print()
        println(bar(BAR_CHAR, WIDTH))
    }


    fun list(t: ConceptType) {  // list all concepts of a given type
        println("\n${t}S")
        println(bar(BAR_CHAR, WIDTH))

        transaction {
            for (c in CONCEPT.getAll(t))  c.deEntify().printL()
        }
        println(bar(BAR_CHAR, WIDTH))
    }


    @OptIn(ExperimentalTime::class)
    fun log(ot: ObjectiveType) {
        transaction {
            when(ot) {
                ObjectiveType.CHALLENGE -> {  // log challenge
                    val cName = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)
                    val cResult = Util.resultFromInput() ?: return@transaction  // return if null
                    println()  // TODO remove print statement once prints are found for exam and foundation log
                    val challenge = CHALLENGE.getOneNamed(cName)?.deEntify()

                    if (challenge?.progressionName == "X") {  // challenge has no progression
                        CHALLENGES.update(challenge, cResult)
                    } else {                          // challenge has a progression
                        val progression = PROGRESSION.getOneNamed(challenge!!.progressionName!!)?.deEntify()
                        progression?.massLog(challenge.name!!, cResult)
                    }
                }

                ObjectiveType.EXAM -> {
                    val eName = inputName(ConceptType.EXAM, ConceptState.PRESENT)
                    val eResult = Util.resultFromInput() ?: return@transaction
                    val exam = EXAM.getOneNamed(eName)?.deEntify()
                    EXAMS.update(exam!!, eResult)
                }

                ObjectiveType.FOUNDATION -> {
                    val fName = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)
                    val foundation = FOUNDATION.getOneNamed(fName)?.deEntify()
                    FOUNDATIONS.update(foundation!!)
                }
            }
        }
    }
}