package com.idris.database

import com.idris.model.newclasses.Concept
import com.idris.model.newclasses.NewDay
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

// A Day is a plan of action to advance a specific skill (consists of a set of Foundations and Progressions)


object DaysT : IntIdTable("daysT") {
    val name = varchar("dayName", 50)
    val description = varchar("description", 200)

    // A day consists of up to 10 Foundations and up to 5 Progressions (for a skill)
    val f0 = varchar("f0Name", 50)
    val f1 = varchar("f1Name", 50)
    val f2 = varchar("f2Name", 50)
    val f3 = varchar("f3Name", 50)
    val f4 = varchar("f4Name", 50)
    val f5 = varchar("f5Name", 50)
    val f6 = varchar("f6Name", 50)
    val f7 = varchar("f7Name", 50)
    val f8 = varchar("f8Name", 50)
    val f9 = varchar("f9Name", 50)

    val p0 = varchar("p0Name", 50)
    val p1 = varchar("p1Name", 50)
    val p2 = varchar("p2Name", 50)
    val p3 = varchar("p3Name", 50)
    val p4 = varchar("p4Name", 50)
}

class DayE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<DayE>(DaysT) {
        fun getOneNamed(name: String): DayE? {
            return find { DaysT.name eq name}.singleOrNull()
        }
    }

    var name by DaysT.name
    var description by DaysT.description

    var f0 by DaysT.f0
    var f1 by DaysT.f1
    var f2 by DaysT.f2
    var f3 by DaysT.f3
    var f4 by DaysT.f4
    var f5 by DaysT.f5
    var f6 by DaysT.f6
    var f7 by DaysT.f7
    var f8 by DaysT.f8
    var f9 by DaysT.f9

    var p0 by DaysT.p0
    var p1 by DaysT.p1
    var p2 by DaysT.p2
    var p3 by DaysT.p3
    var p4 by DaysT.p4

    override fun deEntify(): NewDay {
        return NewDay(
            name,
            description,
            arrayOf(f0, f1, f2, f3, f4, f5, f6, f7, f8, f9),
            arrayOf(p0, p1, p2, p3, p4)
        )
    }
}
