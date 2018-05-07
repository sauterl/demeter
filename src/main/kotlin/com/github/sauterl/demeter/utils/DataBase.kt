package com.github.sauterl.demeter.utils

import com.github.sauterl.demeter.config.Configuration
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
  private val internalSet = createOrOpenSet(db)

  private fun makeDb(): DB {
    db = DBMaker.fileDB(Configuration.General.dbFile).fileMmapEnable().make()
    return db
  }

  private fun createOrOpenMap(db: DB): HTreeMap<String, String> {
    return db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen()
  }

  private fun createOrOpenSet(db: DB): HTreeMap.KeySet<String> {
    return db.hashSet("set", Serializer.STRING).createOrOpen()
  }

  fun close() {
    if (!db.isClosed()) {
      db.close()
    }
  }

  val map: HTreeMap<String, String>
    get() = if (db.isClosed()) createOrOpenMap(db = makeDb()) else internalMap


  val set: HTreeMap.KeySet<String>
    get() = if (db.isClosed()) createOrOpenSet(db = makeDb()) else internalSet
}
