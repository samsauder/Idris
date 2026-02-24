package com.idris.database.entities

import com.idris.system.concepts.Concept
import com.idris.system.concepts.Foundation
import com.idris.system.concepts.Record
import com.idris.system.extra.Util
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal


object FOUNDATIONS : OBJECTIVES("foundationsT") {
    fun insert(f: Foundation) {
        transaction {
            FOUNDATION.new {
                this.name = f.name
                this.skillName = f.skillName
                this.description = f.description  // "" if null
                this.minutes = BigDecimal(f.minutes)
            }
        }
    }

    fun update(f: Foundation) {
        f.update(true)

        FOUNDATION.findSingleByAndUpdate(FOUNDATIONS.name eq f.name) {
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