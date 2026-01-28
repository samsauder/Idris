package com.idris
import com.idris.database.entities.RECORDS
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.DAYS
import com.idris.database.entities.EXAMS
import com.idris.database.entities.EXPERIMENTS
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSIONS
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.sql.Connection


// Use main when the database needs to be reset
fun main() {
    val path = "testdata/06.db"
    val reset: Boolean = false
    val db = Database(path)
    db.setup(reset)
    println("Set up $path")
}

// An abstraction for an SQLITE database of Idris entities on the host machine
class Database(var path: String) {  // specify a valid .db file with path
    init {
        connect()
    }

    // Connect to the SQLITE database at path
    fun connect() {
        Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE

    }

    // Set up the tables for the current database
    fun setup(reset: Boolean) {
        transaction {
            if (reset) deleteEntities()  // remove all entities

            SchemaUtils.create(CHALLENGES)
            SchemaUtils.create(RECORDS)
            SchemaUtils.create(FOUNDATIONS)
            SchemaUtils.create(EXAMS)
            SchemaUtils.create(PROGRESSIONS)
            SchemaUtils.create(EXPERIMENTS)
            SchemaUtils.create(DAYS)

            println("Set up all tables for the database at '$path'.\n")
        }
    }

    // Delete all entities in the database
    private fun deleteEntities() {
        transaction {
            CHALLENGES.deleteAll()
            RECORDS.deleteAll()
            FOUNDATIONS.deleteAll()
            EXAMS.deleteAll()
            PROGRESSIONS.deleteAll()
            DAYS.deleteAll()
        }
    }
}