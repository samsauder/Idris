package com.idris.database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity

object ExperimentsT : IntIdTable("experimentsT") {
    val name = varchar("name", 50)
    val description = varchar("description", 800)
    // val segmentName = varchar("segmentName", 50)
    // var segCount = integer("segCount")
}

class ExperimentE(id: EntityID<Int>) : IntEntity(id) {

}