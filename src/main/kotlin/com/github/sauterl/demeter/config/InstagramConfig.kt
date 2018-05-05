package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object InstagramConfig : ConfigSpec("instagram") {
  val amount by required<Int>()
  val query by optional("fantasybasel")
}
