package com.idris.database.entities

import com.idris.system.concepts.Foundation
import com.idris.system.concepts.Record
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal


object FOUNDATIONS : OBJECTIVES("foundationsT") {
    fun insert(f: Foundation) {  // insert a FOUNDATION equivalent of f into the DB
        if (!f.isComplete()) return  // return if f is not complete

        transaction {
            FOUNDATION.new {
                this.name = f.name!!
                this.skillName = f.skillName!!
                this.description = f.description!!
                this.minutes = BigDecimal(f.minutes!!)
            }
        }

        println("\nAdded ${style(f.name, Styles.BOLD)} to the FOUNDATIONS table.")
    }

    fun modify(name: String, f: Foundation) {  // update the FOUNDATION with the given name according to f
        transaction {
            // overwrite each attribute in the FOUNDATION with non-empty attributes of f
            FOUNDATION.findSingleByAndUpdate(FOUNDATIONS.name eq name) {
                if (f.name != null) it.name = f.name!!
                if (f.skillName != null) it.skillName = f.skillName!!
                if (f.description != null) it.description = f.description!!
                if (f.minutes != null) it.minutes = f.minutes!!.toBigDecimal()
            }
        }

        println("\nModified ${style(name, Styles.BOLD)} in the FOUNDATIONS table.")
    }

    fun update(f: Foundation) {  // record the result the recently completed foundation
        f.update(true)

        FOUNDATION.findSingleByAndUpdate(FOUNDATIONS.name eq f.name!!) {
            val datetime = Util.datetimeNow()
            val r = Record("${it.name}___${datetime}", it.skillName, "", it.name, true, datetime)
            RECORDS.insert(r)
        }
    }
}

class FOUNDATION(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<FOUNDATION>(FOUNDATIONS) {
        fun getOneNamed(name: String): FOUNDATION? {
            return find { FOUNDATIONS.name eq name}.singleOrNull()
        }
    }

    var name by FOUNDATIONS.name
    var skillName: String by FOUNDATIONS.skillName
    var description by FOUNDATIONS.description
    var minutes by FOUNDATIONS.minutes

    // Turn the FoundationE into a Foundation
    override fun deEntify() : Foundation {
        return Foundation(
            name,
            skillName,
            description,
            minutes.toDouble())
    }
}