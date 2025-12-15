package com.idris.database

import com.idris.database.ChAttemptsT.decimal
import com.idris.database.ChAttemptsT.varchar
import com.idris.model.objective.Foundation
import com.idris.model.Skill
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.util.Objects

// IntIdTable("foundationsT")


object FoundationsT : ObjectivesT("foundationsT") {

}


class FoundationE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<FoundationE>(FoundationsT) {
        fun getOneNamed(name: String): FoundationE? {
            return find { FoundationsT.name eq name}.singleOrNull()
        }
    }

    var name by FoundationsT.name
    var skill: String by FoundationsT.skill
    var description by FoundationsT.description
    var minutes by FoundationsT.minutes

    // Turn the FoundationE into a Foundation
    override fun deEntify() : Foundation {
        return Foundation(
            name,
            Skill(skill, null),
            description,
            minutes.toDouble())
    }
}