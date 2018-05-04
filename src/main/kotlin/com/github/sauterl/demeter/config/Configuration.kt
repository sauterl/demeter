package com.github.sauterl.demeter.config

import com.uchuhimo.konf.Config
import java.io.FileNotFoundException

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object Configuration {

  private fun initConfig(): Config {
    var cfg = Config {
      addSpec(GeneralConfig)
      addSpec(CineastConfig)
    }.withSourceFrom.json.resource("default.json")

    try{
      cfg = cfg.withSourceFrom.json.file("demeter.json")
    }catch(e : FileNotFoundException){
      // ignore
    }
    return cfg.withSourceFrom.env().withSourceFrom.systemProperties()

  }

  /*
  private val config = Config {
    addSpec(GeneralConfig)
    addSpec(CineastConfig)
  }.withSourceFrom.json.resource("default.json")
      .withSourceFrom.json.file("demeter.json")
      .withSourceFrom.env().withSourceFrom.systemProperties()*/

  private val config = initConfig()

  object Cineast{
    val host = config[CineastConfig.host]
    val user = config[CineastConfig.user]
    val pw = config[CineastConfig.password]
  }

  object General{
    val identifier = config[GeneralConfig.identifier]
    val dbFile = config[GeneralConfig.dbFile]
    val imgDir = config[GeneralConfig.imgDir]
  }
}
