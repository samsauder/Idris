package sampleData

import model.NonDayPlan
import model.auxiliary.Type

val boulderingWeekPlan1 = NonDayPlan("boulderingWeekPlan", Type.week, bouldering,
    mutableListOf(limit, volume, hang, pull, core, antagonist),
    "2025-09-01"
)