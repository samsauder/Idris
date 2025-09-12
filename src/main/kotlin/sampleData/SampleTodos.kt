package sampleData

import constants.Descriptions
import model.Todo

val pullup85max = Todo("pullup85max", pull, Descriptions.PULLUP_85MAX, 20)
val hangingKneeRaises = Todo("hangingKneeRaises", core, Descriptions.HANGING_KNEE_RAISES, 10)
val bentLegWindshieldWipers = Todo("bentLegWindshieldWipers",core, Descriptions.BENT_LEG_WINDSHIELDS, 10)
val benchPress = Todo("benchPress", antagonist, Descriptions.BENCH_PRESS, 30)
val hang20mm90max = Todo("hang20mm90max", hang, Descriptions.HANG_20MM_90MAX, 20)

val boulderingPlan1Tasks = mutableListOf(hang20mm90max, pullup85max)
val boulderingPlan2Tasks = mutableListOf(hangingKneeRaises, bentLegWindshieldWipers, benchPress)
