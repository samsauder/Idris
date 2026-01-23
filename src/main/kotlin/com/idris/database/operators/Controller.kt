package com.idris.database.operators

import com.idris.database.entities.CONCEPT
import com.idris.database.operators.refactored.Deleter.bar
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// The Controller is an abstraction used to operate on database concepts
// <targets>    f/c/e/p/d/x
// <operators>  list/create/delete/modify/log/view


object Controller {
    const val WIDTH = 130  // table width

    private fun title(t: ConceptType) {  // outputs a title and a horizontal bar under it
        println("${t}S")
        println(bar("=", WIDTH))
    }

    // ======================================================================
    fun list(t: ConceptType) {  // list all concepts of a given type
        title(t)
        transaction {
            for (c in CONCEPT.getAll(t))  c.deEntify().printL()
        }
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
    fun view() { TODO() }
    // ======================================================================
}