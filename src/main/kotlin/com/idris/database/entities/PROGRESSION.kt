package com.idris.database.entities

import com.idris.system.concepts.Concept
import com.idris.system.concepts.Progression
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


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

    /*
    // Insert a new PROGRESSION into PROGRESSIONS
    fun insert(name: String,
               skill: String,
               description: String,
               challenges: Array<String?>) {
        transaction {
            PROGRESSION.new {
                this.name = name
                this.skillName = skill
                this.description = description
                this.c0 = challenges[0] ?: "X"
                this.c1 = challenges[1] ?: "X"
                this.c2 = challenges[2] ?: "X"
                this.c3 = challenges[3] ?: "X"
                this.c4 = challenges[4] ?: "X"
                this.c5 = challenges[5] ?: "X"
                this.c6 = challenges[6] ?: "X"
                this.c7 = challenges[7] ?: "X"
                this.c8 = challenges[8] ?: "X"
                this.c9 = challenges[9] ?: "X"
            }
        }
    } */


    fun insert(p: Progression) {
        transaction {
            PROGRESSION.new {
                this.name = p.name
                this.skillName = p.skillName
                this.description = p.description
                this.c0 = (p.challenges[0]?.name ?: "X") as String
                this.c1 = (p.challenges[1]?.name ?: "X") as String
                this.c2 = (p.challenges[2]?.name ?: "X") as String
                this.c3 = (p.challenges[3]?.name ?: "X") as String
                this.c4 = (p.challenges[4]?.name ?: "X") as String
                this.c5 = (p.challenges[5]?.name ?: "X") as String
                this.c6 = (p.challenges[6]?.name ?: "X") as String
                this.c7 = (p.challenges[7]?.name ?: "X") as String
                this.c8 = (p.challenges[8]?.name ?: "X") as String
                this.c9 = (p.challenges[9]?.name ?: "X") as String
            }
        }
    }
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
