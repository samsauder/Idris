package com.idris.database

import com.idris.model.newclasses.NewDay
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass

// A Day is a plan of action to advance a specific skill (consists of a set of Foundations and Progressions)


object DAYS : IntIdTable("daysT") {
    val name = varchar("dayName", 50)
    val skillName = varchar("skillName", 50)
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

class DAY(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<DAY>(DAYS) {
        fun getOneNamed(name: String): DAY? {
            return find { DAYS.name eq name}.singleOrNull()
        }
    }

    var name by DAYS.name
    var skillName by DAYS.skillName
    var description by DAYS.description

    var f0 by DAYS.f0
    var f1 by DAYS.f1
    var f2 by DAYS.f2
    var f3 by DAYS.f3
    var f4 by DAYS.f4
    var f5 by DAYS.f5
    var f6 by DAYS.f6
    var f7 by DAYS.f7
    var f8 by DAYS.f8
    var f9 by DAYS.f9

    var p0 by DAYS.p0
    var p1 by DAYS.p1
    var p2 by DAYS.p2
    var p3 by DAYS.p3
    var p4 by DAYS.p4

    override fun deEntify(): NewDay {
        return NewDay(
            name,
            skillName,
            description,
            arrayOf(f0, f1, f2, f3, f4, f5, f6, f7, f8, f9),
            arrayOf(p0, p1, p2, p3, p4)
        )
    }
}
