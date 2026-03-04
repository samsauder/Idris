package com.idris.database.operators.todo

import com.idris.database.entities.*
import com.idris.system.concepts.*
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Util.inp
import com.idris.system.extra.Util.input
import com.idris.system.extra.Util.inputB
import com.idris.system.extra.Util.inputBool
import com.idris.system.extra.Util.inputConCor
import com.idris.system.extra.Util.inputConceptNames
import com.idris.system.extra.Util.inputNm
import com.idris.system.extra.Util.inputObjCor


object Modifier : Operator() {
    override fun f() {
        val nm = inputNm(ConceptType.FOUNDATION, ConceptState.PRESENT) ?: return  // non-null or return
        val f = inputObjCor(Foundation(), ConceptType.FOUNDATION) as Foundation
        FOUNDATIONS.modify(nm, f)
    }

    override fun c(datapath: String) {
        val nm = inputNm(ConceptType.CHALLENGE, ConceptState.PRESENT) ?: return  // non-null or return
        val c = inputObjCor(Challenge(), ConceptType.CHALLENGE) as Challenge
        c.progressionName = input("PROGRESSION") as String?
        CHALLENGES.modify(nm, c)
    }

    override fun e() {
        val nm = inputNm(ConceptType.EXAM, ConceptState.PRESENT) ?: return  // non-null or return
        val e = inputObjCor(Foundation(), ConceptType.FOUNDATION) as Exam
        EXAMS.modify(nm, e)
    }

    override fun x() {
        val nm = inputNm(ConceptType.EXPERIMENT, ConceptState.PRESENT) ?: return   // non-null or return
        val x = inputConCor(Experiment(), ConceptType.EXPERIMENT) as Experiment
        x.segment = inputConceptNames(ConceptType.DAY, 7).toList()
        x.segCount = input("# OF SEGMENTS").toString().toInt()
        EXPERIMENTS.modify(nm, x)
    }

    override fun d() {
        val nm = inputNm(ConceptType.DAY, ConceptState.PRESENT) ?: return  // non-null or return
        val d = inputConCor(Day(), ConceptType.DAY) as Day
        d.foundationNames = inputConceptNames(ConceptType.FOUNDATION, 10)  // foundation names
        d.progressionNames = inputConceptNames(ConceptType.PROGRESSION, 5)  // progression names
        DAYS.modify(nm, d)
    }

    override fun p(datapath: String) {
        val nm = inputNm(ConceptType.PROGRESSION, ConceptState.PRESENT) ?: return  // non-null or return
        val p = inputConCor(Progression(), ConceptType.PROGRESSION) as Progression
        p.challengeNames = inputConceptNames(ConceptType.CHALLENGE, 10).toList() as List<String>?
        PROGRESSIONS.modify(nm, p)
    }

    override fun r() {  // modify a record
        val nm = inputNm(ConceptType.RECORD, ConceptState.PRESENT) ?: return  // non-null or return
        val r = inputConCor(Record(), ConceptType.RECORD) as Record
        r.objectiveName = inp("COMPLETED OBJECTIVE")
        r.won = inputB("RESULT")
        r.date = inp("DATETIME")
        RECORDS.modify(nm, r)
    }
}