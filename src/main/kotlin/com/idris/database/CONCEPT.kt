package com.idris.database

import com.idris.model.newclasses.Concept
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity

// Abstract class defining common attributes for all Idris database entities


abstract class CONCEPT(id: EntityID<Int>) : IntEntity(id) {
    abstract fun deEntify() : Concept;  // return a Concept
}