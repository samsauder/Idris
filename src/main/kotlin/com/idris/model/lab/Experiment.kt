package com.idris.model.lab
import com.idris.model.objective.Exam
import com.idris.model.objective.Foundation
import com.idris.model.Skill
import com.idris.model.objective.Challenge
import com.idris.model.objective.Objective
import com.idris.sampleData.*;
import java.time.LocalDate
// import java.time.LocalDateTime
import kotlinx.serialization.*
// import kotlinx.serialization.json.*

// Sam Sauder
// Dec 5 2025
// An Experiment is plan of action to achieve a PRIMARY goal and a set of AUXILIARY goals over a certain duration.
// + name
// + description
// + segment: a list of Day names that can be looked up when needed from the Day database
// + segCount: how many times does the segment repeat
// + startDate: when the experiment was begun
// + adherance: # of days completed / # of days possible to have completed



class Experiment(var name: String,
                 var description: String,
                 var segment: List<String?>,
                 var segCount: Int) {
    // lateinit var startDate: LocalDate;
    // adherance
    /*
    fun begin() {
        startDate = LocalDate.now();
    }*/
}

fun main() {
    val limitDay = listOf(
        hang20mm90max,
        isolateV7in5,
        projectV7in5,
        isolateV8in7,
        projectV8in7,
        pullup85max
    )

    val volumeDay = listOf(
        nineRepsV6for90,
        twelveRepsV6for90,
        fifteenRepsV6for90,
        eighteenRepsV6for90
    )

    // val segment = listOf(limitDay, volumeDay, null, limitDay, volumeDay, null, null)
    val segment = listOf("limitDay", "volumeDay", null, "limitDay", "volumeDay", null, null)


    val e = Experiment("thing", "a thing.", segment, 1);
    val displayer = TextEDisplayer();
    // val encodedX = Json.encodeToString(e)
    // val decodedX = Json.decodeFromString<Experiment>(encodedX)
    // displayer.dashboardOf(decodedX);
    displayer.dashboardOf(e);
}



/*
class Experiment(var title: String,
                 var description: String,
                 var segment: Array<List<Skill>>,
                 var segmentCt: Int,
                 var foundations: Array<Foundation>,
                 var challenges: Array<Challenge>,
                 var exams: Array<Exam>) {

    // private var adherence: Double
    // start date
}
*/



// + title
// + description       |  precise specification of the experiment
// + start date        |  what day was the experiment begun
// + practiceSegmentCt |  # of practice segments in the entire experiment
// + measureSegmentCt  |  1
// + segmentDuration   |  length of practiceSegment (days)
// + practiceSegment   |  an array of Skill lists for each day in a segment (may have gaps)
// + measureSegment    |  an array of Exam lists for each day in the segment    (may have gaps)
// + foundations       |  an array of all Foundations
// + challenges        |  an array of all Challenges
// + exams             |  an array of all Exams
// + adherence         |  current # of days completed / projected # of days completed

