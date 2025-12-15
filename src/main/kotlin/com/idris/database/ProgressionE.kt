package com.idris.database

import com.idris.model.Progression
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import com.idris.Database
import com.idris.model.newclasses.Concept
import org.jetbrains.exposed.v1.core.eq


object ProgressionsT : IntIdTable("progressionsT") {
    val name = varchar("progressionName", 50)
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

class ProgressionE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<ProgressionE>(ProgressionsT) {
        fun getOneNamed(name: String): ProgressionE? {
            return find { ProgressionsT.name eq name}.singleOrNull()
        }
    }

    var name by ProgressionsT.name
    var description by ProgressionsT.description

    var c0 by ProgressionsT.c0
    var c1 by ProgressionsT.c1
    var c2 by ProgressionsT.c2
    var c3 by ProgressionsT.c3
    var c4 by ProgressionsT.c4
    var c5 by ProgressionsT.c5
    var c6 by ProgressionsT.c6
    var c7 by ProgressionsT.c7
    var c8 by ProgressionsT.c8
    var c9 by ProgressionsT.c9

    // Given a path to a .db file
    fun deEntify(datapath: String) : Progression {
        return Progression(
            name,
            listOf(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9),
            Database(datapath))
    }

    override fun deEntify(): Concept {
        TODO("Not yet implemented")
    }
}
