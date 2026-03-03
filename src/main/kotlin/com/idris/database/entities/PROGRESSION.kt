package com.idris.database.entities

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

    fun insert(p: Progression) {
        // TODO add completeness check

        transaction {
            PROGRESSION.new {
                this.name = p.name!!
                this.skillName = p.skillName!!
                this.description = p.description!!
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

        // message
    }

    fun modify(name: String, p: Progression) {
        transaction {
            PROGRESSION.findSingleByAndUpdate(PROGRESSIONS.name eq name) {
                if (p.name != null) it.name = p.name!!
                if (p.description != null) it.description = p.description!!

                if (p.challengeNames?.get(0) != null) it.c0 = p.challengeNames!![0] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(1) != null) it.c1 = p.challengeNames!![1] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(2) != null) it.c2 = p.challengeNames!![2] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(3) != null) it.c3 = p.challengeNames!![3] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(4) != null) it.c4 = p.challengeNames!![4] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(5) != null) it.c5 = p.challengeNames!![5] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(6) != null) it.c6 = p.challengeNames!![6] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(7) != null) it.c7 = p.challengeNames!![7] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(8) != null) it.c8 = p.challengeNames!![8] else return@findSingleByAndUpdate
                if (p.challengeNames?.get(9) != null) it.c9 = p.challengeNames!![9] else return@findSingleByAndUpdate
            }
        }

        // message
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
