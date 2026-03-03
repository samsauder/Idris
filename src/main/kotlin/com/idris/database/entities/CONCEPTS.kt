package com.idris.database.entities

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable


abstract class CONCEPTS(tableName: String) : IntIdTable(tableName) {
    var name = varchar("name", 50)
    var skillName = varchar("skillName", 50)
    val description = varchar("description", 200)
}