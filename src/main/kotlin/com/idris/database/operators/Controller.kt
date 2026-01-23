package com.idris.database.operators

import com.idris.database.entities.CONCEPT
import com.idris.database.operators.refactored.Deleter.bar
import com.idris.database.operators.refactored.Deleter.inputName
import com.idris.system.concepts.Concept
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// The Controller is an abstraction used to operate on database concepts
// <targets>    f/c/e/p/d/x
// <operators>  list/create/delete/modify/log/view


object Controller {
    const val WIDTH = 130  // table width
    const val BAR_CHAR = "-"


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
    fun create() { TODO() }
    // ======================================================================
    fun delete() { TODO() }
    // ======================================================================
    fun modify() { TODO() }
    // ======================================================================
    fun log() { TODO() }
    // ======================================================================
    fun view(t: ConceptType) {
        val name = inputName(t, ConceptState.PRESENT)
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
}