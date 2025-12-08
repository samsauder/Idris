package com.idris
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import java.sql.Connection

// An abstraction for an SQLITE database of Idris entities on the host machine


class Database(var path: String) {  // specify a valid .db file with path

    // Connect to the SQLITE database at path
    fun connect() {
        Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE

    }
}