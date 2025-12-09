package com.idris.sampleData

import com.idris.constants.Descriptions
import com.idris.model.objective.Exam

// Sam
val pull2RM = Exam("pull2RM", pull, "", 1.0)
val hang20mm7sec1RM = Exam("hang20mm7sec1RM", hang, "", 1.0)

/// val bench1RM80lb = Exam("bench1RM80lb", antagonist, Descriptions.BENCH_1RM_80LB , 1.0)
val boulderingLiExams = mutableListOf(pull2RM)
// val boulderingVoExams = mutableListOf(bench1RM80lb)


// Will
val yellowBeltExam = Exam("yellowBeltTest", karate, Descriptions.YELLOW_BELT_TEST, 30.0)

val karateTests = mutableListOf(yellowBeltExam)