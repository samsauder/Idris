package com.idris
import com.idris.database.ChAttemptsT
import com.idris.database.ChallengesT
import com.idris.database.DaysT
import com.idris.database.ExamsT
import com.idris.database.FoundationsT
import com.idris.database.ProgressionsT
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.sql.Connection

// Use main when the database needs to be reset
fun main() {
    val path = "testdata/055.db"
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

            SchemaUtils.create(ChallengesT)
            SchemaUtils.create(ChAttemptsT)
            SchemaUtils.create(FoundationsT)
            SchemaUtils.create(ExamsT)
            SchemaUtils.create(ProgressionsT)
            // SchemaUtils.create(ExperimentsT)
            SchemaUtils.create(DaysT)

            println("Set up all tables for the database at '$path'.\n")
        }
    }

    // Delete all entities in the database
    private fun deleteEntities() {
        transaction {
            ChallengesT.deleteAll()
            ChAttemptsT.deleteAll()
            FoundationsT.deleteAll()
            ExamsT.deleteAll()
            ProgressionsT.deleteAll()
            DaysT.deleteAll()
        }
    }

}