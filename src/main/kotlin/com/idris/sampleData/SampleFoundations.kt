package com.idris.sampleData

import com.idris.constants.Descriptions
import com.idris.model.objective.Foundation

val hang20mm90max = Foundation("hang20mm90max", hang, Descriptions.HANG_20MM_90MAX, 20.0)
val pullup85max = Foundation("pullup85max", pull, Descriptions.PULLUP_85MAX, 20.0)

/// val hangingKneeRaises = Todo("hangingKneeRaises", core, Descriptions.HANGING_KNEE_RAISES, 10.0)

// val bentLegWindshieldWipers = Todo("bentLegWindshieldWipers",core, Descriptions.BENT_LEG_WINDSHIELDS, 10.0)
// val benchPress = Todo("benchPress", antagonist, Descriptions.BENCH_PRESS, 30.0)

val boulderingLiTodos = mutableListOf(hang20mm90max, pullup85max)
// val boulderingVoTodos = mutableListOf(hangingKneeRaises, bentLegWindshieldWipers, benchPress)
