package com.github.sauterl.demeter.config

import com.uchuhimo.konf.ConfigSpec

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object TwitterConfig : ConfigSpec("twitter") {

  val storeTweets  by optional(false)
  val tweetDir by optional("tweets/")
  val query by optional("#fantasybasel")
}