package sampleData

import constants.Descriptions
import model.Challenge


// Sam
val newV7 = Challenge("newV7", limit, Descriptions.NEW_V7, 15, 0.25)
val projectV7 = Challenge("projectV7", limit, Descriptions.PROJECT_V7, 15, 0.60)
val projectV8 = Challenge("projectV8", limit, Descriptions.PROJECT_V7, 45, 0.05)
val threeV5nineV6 = Challenge("threeV5nineV6", volume, Descriptions.THREE_V5_NINE_V6, 90, 0.10)

val boulderingPlan1Challenges = mutableListOf(projectV8, newV7)
val boulderingPlan2Challenges = mutableListOf(threeV5nineV6)


// Will
val fiveForty = Challenge("fiveForty", karate, Descriptions.FIVE_FORTY, 1, 0.80)
val reverseCrescent = Challenge("reverseCrescent", karate, Descriptions.REVERSE_CRESCENT, 1, 0.80)
val tornado = Challenge("tornado", karate, Descriptions.TORNADO, 1, 0.60)
val spinningTornado = Challenge("spinningTornado",karate, Descriptions.SPINNING_TORNADO, 1, 0.30)

val karateChallenges = mutableListOf(fiveForty, reverseCrescent, tornado, spinningTornado)