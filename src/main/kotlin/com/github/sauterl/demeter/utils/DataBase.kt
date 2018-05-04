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
  private val internalMap = createOrOpenMap(db)

  private fun makeDb(): DB {
    db = DBMaker.fileDB(Settings.dbFile).fileMmapEnable().make()
    return db
  }

  private fun createOrOpenMap(db: DB): HTreeMap<String, String> {
    return db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen()
  }

  fun close() {
    if(db.isClosed()){
      db.close()
    }
  }

  val map: HTreeMap<String, String>
    get() = if(db.isClosed()) createOrOpenMap(db = makeDb()) else internalMap

}
