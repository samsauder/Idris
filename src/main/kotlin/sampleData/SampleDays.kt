package sampleData

import model.Day

val boulderingD1Limit = Day("boulderingD1Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-01")
val boulderingD3Volume = Day("boulderingD3Volume", bouldering, mutableListOf(volume, core, antagonist), "2025-09-03")
val boulderingD6Limit = Day("boulderingD6Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-06")

val chessStudyDay = Day("chessStudyDay", chess, mutableListOf(visualization, tactics, opening, strategy), "2025-10-01")
val chessRapidDay = Day("chessRapidDay", chess, mutableListOf(visualization, tactics, rapid, analysis), "2025-10-01")
val chessGuessDay = Day("chessGuessDay", chess, mutableListOf(visualization, tactics, guess, analysis), "2025-10-01")