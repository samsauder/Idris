package com.idris.database.entities

import com.idris.model.Progression
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.core.eq


object PROGRESSIONS : IntIdTable("progressionsT") {
    val name = varchar("progressionName", 50)
    val skillName = varchar("skillName", 50)
    val description = varchar("description", 200)

    // A progression consists of up to 10 progressively difficult challenges
    val c0 = varchar("c0Name", 50)
    val c1 = varchar("c1Name", 50)
    val c2 = varchar("c2Name", 50)
    val c3 = varchar("c3Name", 50)
    val c4 = varchar("c4Name", 50)
    val c5 = varchar("c5Name", 50)
    val c6 = varchar("c6Name", 50)
    val c7 = varchar("c7Name", 50)
    val c8 = varchar("c8Name", 50)
    val c9 = varchar("c9Name", 50)
}

class PROGRESSION(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<PROGRESSION>(PROGRESSIONS) {
        fun getOneNamed(name: String): PROGRESSION? {
            return find { PROGRESSIONS.name eq name}.singleOrNull()
        }
    }

    var name by PROGRESSIONS.name
    var skillName by PROGRESSIONS.skillName
    var description by PROGRESSIONS.description

    var c0 by PROGRESSIONS.c0
    var c1 by PROGRESSIONS.c1
    var c2 by PROGRESSIONS.c2
    var c3 by PROGRESSIONS.c3
    var c4 by PROGRESSIONS.c4
    var c5 by PROGRESSIONS.c5
    var c6 by PROGRESSIONS.c6
    var c7 by PROGRESSIONS.c7
    var c8 by PROGRESSIONS.c8
    var c9 by PROGRESSIONS.c9


    override fun deEntify() : Progression {
        val cN = listOf(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9)

        val p = Progression(
            name,
            skillName,
            description,
            cN)
        return p
    }
}
