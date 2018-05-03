package com.github.sauterl.demeter.utils

import org.mapdb.DB
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object DataBase {
  private var db = makeDb()
  private val map = createOrOpenMap(db)

  private fun makeDb(): DB {
    db = DBMaker.fileDB(Settings.dbFile).fileMmapEnable().make()
    return db
  }

  private fun createOrOpenMap(db: DB): HTreeMap<String, String> {
    return db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen()
  }

  fun close() {
    db.close()
  }

  fun map(): HTreeMap<String, String> {
    return if (db.isClosed()) {
      createOrOpenMap(db = makeDb())
    } else {
      map
    }
  }

}