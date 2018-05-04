package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object CineastConfig : ConfigSpec("cineast") {
  val host by required<String>()
  val user by required<String>()
  val password by required<String>()
}
