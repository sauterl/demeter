package com.github.sauterl.demeter.utils

import com.typesafe.config.ConfigFactory

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object Settings{
    const val DEMETER_SETTINGS = "demeter.json"

    private const val GENERAL_CATEGORY = "general."
    private const val DB_FILE = GENERAL_CATEGORY+"db-file"
    private const val IMG_DIR = GENERAL_CATEGORY+"img-dir"

    private val config = ConfigFactory.load(DEMETER_SETTINGS)

    val dbFile = config.getString(DB_FILE)
    val imgDir = config.getString(IMG_DIR)

}