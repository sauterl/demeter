package com.github.sauterl.demeter.config

import com.uchuhimo.konf.Config
import mu.KotlinLogging
import java.io.FileNotFoundException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
    val inteval = config[GeneralConfig.interval]
    val until = config[GeneralConfig.until]

    val forever = until.contentEquals("infinity")

    fun untilDate() = initDate()

    private val df = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS")
    private val ldf = SimpleDateFormat("dd.MM.yyyy HH:mm")

    private fun initDate(): Date? {
      return try{
        df.parse(until)
      }catch(e:ParseException){
        try{
          ldf.parse(until)
        }catch(e:ParseException){
          null
        }
      }
    }

    fun withinTimeBounds():Boolean{
      return if(forever){
        true
      }else{
        untilDate()?.after(Date()) ?: true
      }
    }

    fun untilText():String{
      return if(forever){
        "Forever. To stop type 'exit'"
      }else{
        df.format(untilDate())
      }
    }

  }

  object Flickr {
    val amount = config[FlickrConfig.amount]
    val queryList = config[FlickrConfig.queryList]
  }

  object Twitter {
    val storeTweets = config[TwitterConfig.storeTweets]
    val tweetDir = config[TwitterConfig.tweetDir]
    val queryList = config[TwitterConfig.queryList]
  }

  object Instagram {
    val amount = config[InstagramConfig.amount]
    val queryList = config[InstagramConfig.queryList]
  }
}
