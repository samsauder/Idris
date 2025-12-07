package com.idris.database

import com.idris.model.Exam
import com.idris.model.Skill
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal

object ExamsT : IntIdTable("examsT") {
    val name = varchar("name", 50)
    val skill = varchar("skill", 50)
    val description = varchar("description", 200)
    val minutes = decimal("minutes",5, 2)
    val passed = bool("passed")
}

class ExamE(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExamE>(ExamsT) {

    }

    var name by ExamsT.name
    var skill: String by ExamsT.skill
    var description by ExamsT.description
    var minutes: BigDecimal by ExamsT.minutes
    var passed by ExamsT.passed

    // Turn the ExamE into an Exam
    fun deEntify() : Exam {
        return Exam(
            name,
            Skill(skill, null),
            description,
            minutes.toDouble(),
            passed)
    }
}