package com.idris.database.helpers

import com.idris.database.DayE
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import com.idris.model.newclasses.NewDay


// Call the Idris 'view' operation on an Idris entity
// * view brings up a detailed overview of the specified entity

object ViewHelper : Helper() {
    override fun f() {
        TODO("Not yet implemented")
    }

    override fun c(datapath: String) {
        TODO("Not yet implemented")
    }

    override fun e() {
        TODO("Not yet implemented")
    }

    override fun x() {
        TODO("Not yet implemented")
    }

    // Print an overview of a specified Day
    override fun d() {
        val name = inputName(ConceptType.DAY, ConceptState.PRESENT)  // get a valid Day name from stdin
        val dayEntity: DayE = DayE.getOneNamed(name)!!
        val day: NewDay = dayEntity.deEntify()
        day.printDay()
    }

    override fun p(datapath: String) {
        TODO("Not yet implemented")
    }

}