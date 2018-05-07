package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object InstagramConfig : ConfigSpec("instagram") {
  val amount by required<Int>()
  val queryList by optional(getDefaultQueryList())

  private fun getDefaultQueryList():List<String>{
    return listOf("fantasybasel")
  }
}
