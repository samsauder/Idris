package sampleData

import model.DayPlan

val boulderingD1Limit = DayPlan("boulderingD1Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-01")
val boulderingD3Volume = DayPlan("boulderingD3Volume", bouldering, mutableListOf(volume, core, antagonist), "2025-09-03")
val boulderingD6Limit = DayPlan("boulderingD6Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-06")

val chessStudyDay = DayPlan("chessStudyDay", chess, mutableListOf(visualization, tactics, opening, strategy), "2025-10-01")
val chessRapidDay = DayPlan("chessRapidDay", chess, mutableListOf(visualization, tactics, rapid, analysis), "2025-10-01")
val chessGuessDay = DayPlan("chessGuessDay", chess, mutableListOf(visualization, tactics, guess, analysis), "2025-10-01")