package com.idris.sampleData

import com.idris.constants.Descriptions
import com.idris.model.challenge.Challenge


// Sam
// Bouldering
/*
val newV7 = Challenge("newV7", limit, Descriptions.NEW_V7, 15.0, 0.25)
val projectV7 = Challenge("projectV7", limit, Descriptions.PROJECT_V7, 15.0, 0.60)
val projectV8 = Challenge("projectV8", limit, Descriptions.PROJECT_V7, 45.0, 0.05)
val threeV5nineV6 = Challenge("threeV5nineV6", volume, Descriptions.THREE_V5_NINE_V6, 90.0, 0.10)

val boulderingLiChallenges = mutableListOf(projectV8, newV7)
val boulderingVoChallenges = mutableListOf(threeV5nineV6)
*/

val isolateV7in5 = Challenge("isolateV7in5", limit, "", -1.0, -1.0);
val projectV7in5 = Challenge("projectV7in5", limit, "", -1.0, -1.0);

val isolateV8in7 = Challenge("isolateV8in7", limit, "", -1.0, -1.0);
val projectV8in7 = Challenge("projectV8in7", limit, "", -1.0, -1.0);

val nineRepsV6for90 = Challenge("nineRepsV6for90", limit, "", -1.0, -1.0)

val projectBlack = Challenge("projectBlack", limit, Descriptions.PROJECT_BLACK, 60.0, 0.05)
val newPurple = Challenge("newPurple", limit, Descriptions.NEW_PURPLE, 60.0, 0.25)
val new3x3Reds = Challenge("new3x3Reds", volume, Descriptions.NEW_3X3_REDS, 90.0, 0.20)
val new4x3Reds = Challenge("new4x3Reds", volume, Descriptions.NEW_4X3_REDS, 90.0, 0.10)
val new5x3Reds = Challenge("new5x3Reds", volume, Descriptions.NEW_5X3_REDS, 90.0, 0.05)
val new7Reds120 = Challenge("new7Reds120", volume, Descriptions.NEW_7_REDS_120, 120.0, 0.50)
val boulderingLiChallenges = mutableListOf(projectBlack, newPurple)
val boulderingVoChallenges = mutableListOf(new3x3Reds, new4x3Reds, new5x3Reds, new7Reds120)

// Visualization
val colorRecall = Challenge("colorRecall", visualization, Descriptions.COLOR_RECALL, 0.10, 0.50)
val staticRecall70 = Challenge("staticRecall70", visualization, Descriptions.STATIC_RECALL_70, 1.0, 0.20)
val staticRecall80 = Challenge("staticRecall80", visualization, Descriptions.STATIC_RECALL_80, 1.0, 0.15)
val staticRecall90 = Challenge("staticRecall90", visualization, Descriptions.STATIC_RECALL_90, 1.0, 0.10)
val staticRecall100 = Challenge("staticRecall100", visualization, Descriptions.STATIC_RECALL_100, 1.0, 0.05)
val chessViChallenges = mutableListOf(colorRecall, staticRecall70, staticRecall80, staticRecall90, staticRecall100)

// Tactics
val puzzle1m2100 = Challenge("puzzle1m2100", tactics, Descriptions.PUZZLE_1M_2100, 1.0, 0.93)
val puzzle1m2200 = Challenge("puzzle1m2200", tactics, Descriptions.PUZZLE_1M_2200, 1.0, 0.62)
val puzzle1m2300 = Challenge("puzzle1m2300", tactics, Descriptions.PUZZLE_1M_2300, 1.0, 0.39)
val puzzle1m2500 = Challenge("puzzle1m2500", tactics, Descriptions.PUZZLE_1M_2500, 1.0, 0.07)
val chessTaChallenges = mutableListOf(puzzle1m2100, puzzle1m2200, puzzle1m2300, puzzle1m2500)

// Rapid
val rapid2000 = Challenge("rapid2000", rapid, Descriptions.RAPID_2000, 30.0, 0.50)
val rapid2100 = Challenge("rapid2100", rapid, Descriptions.RAPID_2100, 30.0, 0.40)
val rapid2200 = Challenge("rapid2200", rapid, Descriptions.RAPID_2200, 30.0, 0.25)
val rapid2300 = Challenge("rapid2300", rapid, Descriptions.RAPID_2300, 30.0, 0.10)
val chessRaChallenges = mutableListOf(rapid2000, rapid2100, rapid2200, rapid2300)

// Guess
val guess8pt6 = Challenge("guess8pt6", guess, Descriptions.GUESS_8PT6, 30.0, 0.25)
val chessGuChallenges = mutableListOf(guess8pt6)


// Will
val fiveForty = Challenge("fiveForty", karate, Descriptions.FIVE_FORTY, 1.0, 0.80)
val reverseCrescent = Challenge("reverseCrescent", karate, Descriptions.REVERSE_CRESCENT, 1.0, 0.80)
val tornado = Challenge("tornado", karate, Descriptions.TORNADO, 1.0, 0.60)
val spinningTornado = Challenge("spinningTornado", karate, Descriptions.SPINNING_TORNADO, 1.0, 0.30)
val karateChallenges = mutableListOf(fiveForty, reverseCrescent, tornado, spinningTornado)