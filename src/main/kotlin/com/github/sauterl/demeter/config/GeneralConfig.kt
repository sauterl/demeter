package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object GeneralConfig : ConfigSpec("general") {

  val identifier by optional("Custom")
  val dbFile by required<String>()
  val imgDir by required<String>()
  val interval by optional(120) // expected to be seconds. Default thus is 120s = 2 min
  val until by optional("infinity")
}
