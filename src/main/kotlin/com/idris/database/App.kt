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
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.50")
        }

        val challenge2 = ChallengeE.new {
            name = "rapid2100"
            skill = "rapid"
            description = Descriptions.RAPID_2100
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.40")
        }

        val challenge3 = ChallengeE.new {
            name = "rapid2200"
            skill = "rapid"
            description = Descriptions.RAPID_2200
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.25")
        }

        val challenge4 = ChallengeE.new {
            name = "rapid2300"
            skill = "rapid"
            description = Descriptions.RAPID_2300
            minutes = BigDecimal("30.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.10")
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


// Sets up a database populated with real data for Sam
fun setupRealDatabase() {
    Database.connect("jdbc:sqlite:sdata/realData.db", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        // addLogger(StdOutSqlLogger)

        ChallengesT.deleteAll()
        SchemaUtils.create(ChallengesT)
        SchemaUtils.create(ChAttemptsT)
        SchemaUtils.create(FoundationsT)

        println("Created ChallengesT table and ChAttemptsT table for the real database.")

        insertTacticsChallenges()
        insertRapidChallenges()
    }
}



// Sets up a database populated with test data for a random user
fun setupTestDatabase() {
    Database.connect("jdbc:sqlite:testdata/testData.db", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        // addLogger(StdOutSqlLogger)

        // ChallengesT.deleteAll()
        SchemaUtils.create(ChallengesT)
        SchemaUtils.create(ChAttemptsT)
        SchemaUtils.create(FoundationsT)

        println("Created ChallengesT table, ChAttemptsT, and FoundationsT table for the test database.")

        insertExampleChallenge()
    }
}


fun main() {
    setupRealDatabase()
    // setupTestDatabase()
}