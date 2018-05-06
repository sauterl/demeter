package com.github.sauterl.demeter.config

import com.uchuhimo.konf.Config
import mu.KotlinLogging
import java.io.FileNotFoundException

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object Configuration {
  private val logger = KotlinLogging.logger {}

  private fun initConfig(): Config {

    var cfg = Config {
      addSpec(GeneralConfig)
      addSpec(CineastConfig)
      addSpec(TwitterConfig)
      addSpec(FlickrConfig)
      addSpec(InstagramConfig)
    }.withSourceFrom.json.resource("default.json")

    try {
      cfg = cfg.withSourceFrom.json.file("demeter.json")
    } catch (e: FileNotFoundException) {
      // ignore
    }
    cfg = cfg.withSourceFrom.env().withSourceFrom.systemProperties()
    logger.trace { "The config: $cfg" }
    return cfg
  }

  /*
  private val config = Config {
    addSpec(GeneralConfig)
    addSpec(CineastConfig)
  }.withSourceFrom.json.resource("default.json")
      .withSourceFrom.json.file("demeter.json")
      .withSourceFrom.env().withSourceFrom.systemProperties()*/

  val config = initConfig()

  object Cineast {
    val host = config[CineastConfig.host]
    val user = config[CineastConfig.user]
    val pw = config[CineastConfig.password]
  }

  object General {
    val identifier = config[GeneralConfig.identifier]
    val dbFile = config[GeneralConfig.dbFile]
    val imgDir = config[GeneralConfig.imgDir]
  }

  object Flickr {
    val amount = config[FlickrConfig.amount]
    val query = config[FlickrConfig.query]
  }

  object Twitter {
    val storeTweets = config[TwitterConfig.storeTweets]
    val tweetDir = config[TwitterConfig.tweetDir]
    val query = config[TwitterConfig.query]
  }

  object Instagram {
    val amount = config[InstagramConfig.amount]
    val query = config[InstagramConfig.query]
  }
}
