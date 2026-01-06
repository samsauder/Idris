package com.idris.database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

// Defines common attributes for all Idris Objective tables

abstract class ObjectivesT(tableName: String) : IntIdTable(tableName) {
    val name = varchar("name", 50)
    val skillName = varchar("skillName", 50)
    // val skill = varchar("skill", 50)
    val description = varchar("description", 200)
    val minutes = decimal("minutes",5, 2)
}