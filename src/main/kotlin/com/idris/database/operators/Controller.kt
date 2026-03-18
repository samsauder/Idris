package com.idris.database.operators

import com.idris.database.entities.*
import com.idris.system.concepts.Concept
import com.idris.system.extra.*
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Util.bar
import com.idris.system.extra.Util.getConceptEntity
import com.idris.system.extra.Util.input
import com.idris.system.extra.Util.inputName
import com.idris.system.State
import com.idris.system.extra.Styler.styleIf
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.time.ExperimentalTime

// The Controller is used to operate on database concepts through the text interface


object Controller {
    const val WIDTH = 68  // table width
    const val BAR_CHAR = "―"
    var mode = Privilege.USER

    fun help() {
        val ostyle = Styles.ITALIC  // styling for operation definitions
        val cstyle = Styles.BOLD  // styling for concept definitions

        val operation = style("operation", Styles.ITALIC)
        val concept = style("concept", Styles.BOLD)

        val syntax = "${style("COMMAND SYNTAX", Styles.BOLD)}    <$operation>[$concept]"

        println("\n${bar(BAR_CHAR, WIDTH)}")
        println("${syntax}\n")
        println(style("Each command is 1-2 characters.", Styles.ITALIC))
        println(bar("-", WIDTH))

        Util.describeOperations(ostyle, mode == Privilege.ADMIN)
        println()
        Util.describeConcepts(cstyle)

        println(bar(BAR_CHAR, WIDTH))

        // COLORS
        val styled14minus = "14XX-"
        val styled15 = "${Styles.BLUE}15XX${Styles.RESET}"
        val styled16 = "${Styles.GREEN}16XX${Styles.RESET}"
        val styled17 = "${Styles.YELLOW}17XX${Styles.RESET}"
        val styled18plus = "${Styles.RED}18XX+${Styles.RESET}"

        val colors = "${style("RATINGS", Styles.BOLD)}    $styled14minus  $styled15  $styled16  $styled17  $styled18plus"

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


    // Log the specified concept n times
    @OptIn(ExperimentalTime::class)
    fun log(t: ConceptType) {  // TODO refactor
        transaction {
            when(t) {
                ConceptType.CHALLENGE -> {  // log challenge
                    val cName = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)
                    val cResult = Util.resultFromInput() ?: return@transaction  // return if null
                    val a: Any? = input("#") // user input to get n
                    println()  // TODO remove print statement once prints are found for exam and foundation log

                    var n = 1  // // # of duplicate challenge results to log

                    if (a != null) {  // single challenge
                        n = a.toString().toInt()
                    }

                    val challenge = CHALLENGE.getOneNamed(cName)?.deEntify()

                    // update the challenge n times with the same result
                    repeat(n) {
                        if (challenge?.progressionName == "X") {  // challenge has no progression
                            CHALLENGES.update(challenge, cResult)
                        } else {                          // challenge has a progression
                            val progression = PROGRESSION.getOneNamed(challenge!!.progressionName!!)?.deEntify()
                            progression?.massLog(challenge.name!!, cResult)
                        }
                    }
                }

                ConceptType.EXAM -> {
                    val eName = inputName(ConceptType.EXAM, ConceptState.PRESENT)
                    val eResult = Util.resultFromInput() ?: return@transaction
                    val exam = EXAM.getOneNamed(eName)?.deEntify()
                    EXAMS.update(exam!!, eResult)
                }

                ConceptType.FOUNDATION -> {
                    val fName = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)
                    val foundation = FOUNDATION.getOneNamed(fName)?.deEntify()
                    FOUNDATIONS.update(foundation!!)
                }

                else -> { return@transaction }  // not an objective
            }
        }
    }


    // Prompts the user for changes in state then prints the current state
    fun state() {
        // val state = get user's current state (either through stdin or the DB)

        // TODO pull most recent state from STATES table

        // println("State is a binary string [xxxx] <y...>\n")

        val bits = input("STATE") as String  // returns a space-separated binary string "xxxx y..."
        val components = bits.split(" ")  // should be of the form xxxx y...

        val supplementCt = 4  // depends on the user

        if (components[0].length != 4 ||
            (components.size > 1 && components[1].length != supplementCt)) {  // error
            println("${Util.error()}  Invalid state.")
            return
        }

        val state = State(bits)  // constructs a state from the bits
        val level = state.level()
        val b = Styles.BOLD
        val g = Styles.GREEN
        val y = Styles.YELLOW


        var style: String? = null

        if (level[0] == 'H') {
            style = Styles.RED
        } else if (level[0] == 'M') {
            style = Styles.YELLOW
        } else if (level[0] == 'L') {
            style = Styles.GREEN
        }


        print("\n${style("[$level]", style)}  ")


        when (level[0]) {
            'H' -> print("Ready for ${style("Intense", Styles.BOLD)} activities.")
            'M' -> print("Ready for ${style("Moderate", Styles.BOLD)} activities.")
            'L' -> print("Ready for ${style("Recovery", Styles.BOLD)} activities.")
        }

        //println("(IDEAL TASK DIFFICULTY)")
        println()

        println(bar("=", 40))

        // attribute booleans
        val sCon = state.sleep
        val fCon = state.food
        val wCon = state.water
        val cCon = state.cardio
        val suCon = !state.supplements.contains(false) && !state.supplements.contains(null)

        println("sleep       |  ${styleIf(sCon.toString(), sCon,b)}")
        println("food        |  ${styleIf(fCon.toString(), fCon,b)}")
        println("water       |  ${styleIf(wCon.toString(), wCon,b)}")
        println("cardio      |  ${styleIf(cCon.toString(), cCon, b)}")
        println("supplements |  ${styleIf(suCon.toString(), suCon, b)}\n")
        println("capacity    |  ${style((4 - state.load).toString(), Styles.BLUE)} hrs")
        println(bar("=", 40))
    }


    fun admin() {  // toggle ADMIN mode for this controller
        val modeS: String  // styled mode

        if (mode == Privilege.USER) {
            mode = Privilege.ADMIN
            modeS = style(mode.toString(), Styles.YELLOW)
        } else {
            mode = Privilege.USER
            modeS = style(mode.toString(), Styles.GREEN)
        }

        println("Changed to $modeS mode.")
    }
}