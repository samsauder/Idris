package sampleData

import model.NonDayPlan
import model.auxiliary.Type

val boulderingWeekPlan1 = NonDayPlan("boulderingWeekPlan",
    Type.week, bouldering, bouldering.subskills,"2025-09-01")

val chess7D = NonDayPlan("chess7D", Type.week, chess, chess.subskills, "2025-10-01")