package database

import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(ChallengesT)

        // Insert
        val challenge1 = ChallengeE.new {
            name = "puzzle1m2200"
            skill = "tactics"
            description = "Solve a 2200 Lichess puzzle in under 1 minute."
            minutes = BigDecimal("1.00")
            cElo = BigDecimal("-0000.00")
            uElo = BigDecimal("1500.00")
            uOdds = BigDecimal("0.50")
        }

        // Select
        val tacticsChallenges = ChallengeE.find { ChallengesT.name eq "tactics" }.toList()  // return all tactics Challenges
    }

}