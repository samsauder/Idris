package com.idris.database

import com.idris.constants.Descriptions
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.sql.Connection


fun insertTacticsChallenges() {
    transaction {
        val challenge1 = ChallengeE.new {
            name = "puzzle1m2100"
            skill = "tactics"
            description = Descriptions.PUZZLE_1M_2100
            minutes = BigDecimal("1.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.81")
        }

        val challenge2 = ChallengeE.new {
            name = "puzzle1m2200"
            skill = "tactics"
            description = Descriptions.PUZZLE_1M_2200
            minutes = BigDecimal("1.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.65")
        }

        val challenge3 = ChallengeE.new {
            name = "puzzle1m2300"
            skill = "tactics"
            description = Descriptions.PUZZLE_1M_2300
            minutes = BigDecimal("1.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.43")
        }

        val challenge4 = ChallengeE.new {
            name = "puzzle1m2500"
            skill = "tactics"
            description = Descriptions.PUZZLE_1M_2500
            minutes = BigDecimal("1.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.07")
        }

        println("Added Tactics challenges to the Sam database.")
    }
}


fun insertRapidChallenges() {
    transaction {
        val challenge1 = ChallengeE.new {
            name = "rapid2000"
            skill = "rapid"
            description = Descriptions.RAPID_2000
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("1500.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.0")
            attempts = 0
            wins = 0
        }

        val challenge2 = ChallengeE.new {
            name = "rapid2100"
            skill = "rapid"
            description = Descriptions.RAPID_2100
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("1500.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.0")
            attempts = 0
            wins = 0
        }

        val challenge3 = ChallengeE.new {
            name = "rapid2200"
            skill = "rapid"
            description = Descriptions.RAPID_2200
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("1500.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.0")
            attempts = 0
            wins = 0
        }

        val challenge4 = ChallengeE.new {
            name = "rapid2300"
            skill = "rapid"
            description = Descriptions.RAPID_2300
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("1500.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.0")
            attempts = 0
            wins = 0
        }

        println("Added Rapid challenges to the real database.")
    }
}


fun insertExampleChallenge() {
    transaction {
        val exampleChallenge = ChallengeE.new {
            name = "exampleChallenge"
            skill = "example"
            description = ""
            minutes = BigDecimal("1.0")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.25")
        }
        println("Added an Example challenge to test database.")
    }
}


// Set up the tables for the real/test database
fun setupTables(isReal: Boolean) {
    transaction {
        /*ChallengesT.deleteAll()
        ChAttemptsT.deleteAll()
        FoundationsT.deleteAll()
        ExamsT.deleteAll()*/
        SchemaUtils.create(ChallengesT)
        SchemaUtils.create(ChAttemptsT)
        SchemaUtils.create(FoundationsT)
        SchemaUtils.create(ExamsT)
        SchemaUtils.create(ProgressionsT)
        // SchemaUtils.create(ExperimentsT)
        // SchemaUtils.create(DaysT)

        val type: String = if (isReal) "real" else "test"
        println("Created ChallengesT, ChAttemptsT, FoundationsT, ExamsT, ProgressionsT tables for the $type database.")
    }
}


// Connects to the SQLite database specified by dbFile
fun connectToSQLiteDB(filepath: String) {
    Database.connect("jdbc:sqlite:$filepath", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
}


// Sets up a database populated with real data for Sam (overwrites all current data)
fun setupRealDatabase() {
    // connectToSQLiteDB("sdata/realData.db")
    connectToSQLiteDB("sdata/real.db")

    setupTables(true)
    // insertTacticsChallenges()
    // insertRapidChallenges()
}


// Sets up a database populated with test data for a random user (overwrites all current data)
fun setupTestDatabase() {
    connectToSQLiteDB("testdata/testData.db")
    setupTables(false)
    insertExampleChallenge()
}


fun main() {
    setupRealDatabase()
    // setupTestDatabase()
}