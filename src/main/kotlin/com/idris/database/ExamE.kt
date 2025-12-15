package com.idris.database

import com.idris.model.objective.Exam
import com.idris.model.Skill
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal

object ExamsT : ObjectivesT("examsT") {
    val passed = bool("passed")
}

class ExamE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<ExamE>(ExamsT) {

    }

    var name by ExamsT.name
    var skill: String by ExamsT.skill
    var description by ExamsT.description
    var minutes: BigDecimal by ExamsT.minutes
    var passed by ExamsT.passed

    // Turn the ExamE into an Exam
    override fun deEntify() : Exam {
        return Exam(
            name,
            Skill(skill, null),
            description,
            minutes.toDouble(),
            passed)
    }
}