package sampleData

import model.Day

/*
val boulderingD1Limit = Day("boulderingD1Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-01")
val boulderingD3Volume = Day("boulderingD3Volume", bouldering, mutableListOf(volume, core, antagonist), "2025-09-03")
val boulderingD6Limit = Day("boulderingD6Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-06")
*/

val boulderingD1Limit = Day("boulderingD1Limit", bouldering, mutableListOf(hang, limit, pull), "2025-9-25")
val boulderingD2Volume = Day("boulderingD2Volume", bouldering, mutableListOf(volume), "2025-9-26")

val boulderingD4Limit = Day("boulderingD4Limit", bouldering, mutableListOf(hang, limit, pull), "2025-9-28")
val boulderingD5Volume = Day("boulderingD5Volume", bouldering, mutableListOf(volume), "2025-9-29")

val chessStudyDay = Day("chessStudyDay", chess, mutableListOf(visualization, tactics, opening, strategy), "2025-10-01")
val chessRapidDay = Day("chessRapidDay", chess, mutableListOf(visualization, tactics, rapid, analysis), "2025-10-01")
val chessGuessDay = Day("chessGuessDay", chess, mutableListOf(visualization, tactics, guess, analysis), "2025-10-01")