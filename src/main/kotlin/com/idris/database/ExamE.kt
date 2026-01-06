package com.idris.database

import com.idris.model.objective.Exam
import com.idris.model.Skill
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal


object ExamsT : ObjectivesT("examsT") {
    val passed = bool("passed")
}

class ExamE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<ExamE>(ExamsT) {
        fun getOneNamed(name: String): ExamE? {
            return find { ExamsT.name eq name}.singleOrNull()
        }
    }

    var name by ExamsT.name
    var skillName: String by ExamsT.skillName
    // var skill: String by ExamsT.skill
    var description by ExamsT.description
    var minutes: BigDecimal by ExamsT.minutes
    var passed by ExamsT.passed

    // Turn the ExamE into an Exam
    override fun deEntify() : Exam {
        return Exam(
            name,
            skillName,
            // Skill(skill, null),
            description,
            minutes.toDouble(),
            passed)
    }
}