package com.idris.database

import com.idris.database.ChAttemptsT.varchar
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity

object ProgressionsT : IntIdTable("daysT") {
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

class ProgressionE(id: EntityID<Int>) : IntEntity(id) {
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
}
