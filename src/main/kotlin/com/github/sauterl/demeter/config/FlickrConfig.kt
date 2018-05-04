package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object FlickrConfig : ConfigSpec("flickr") {
  val amount by required<Number>()
  val query by optional("fantasybasel")
}