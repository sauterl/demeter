package com.github.sauterl.demeter.cineast

import com.typesafe.config.ConfigFactory

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object Settings{

    const val CINEAST_SETTINGS = "cineast.json"
    const val CINEAST_NAMESPACE = "cineast."
    const val HOST = CINEAST_NAMESPACE+"host"

    private val config = ConfigFactory.load(CINEAST_SETTINGS)


    val host = config.getString(HOST)
}