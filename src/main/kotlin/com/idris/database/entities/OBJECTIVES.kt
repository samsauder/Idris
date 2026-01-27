package com.idris.database.entities

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

// Defines common attributes for all Idris Objective tables


abstract class OBJECTIVES(tableName: String) : CONCEPTS(tableName) {
    val minutes = decimal("minutes",5, 2)
}