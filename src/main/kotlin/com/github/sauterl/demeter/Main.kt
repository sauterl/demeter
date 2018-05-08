package com.github.sauterl.demeter

import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.utils.DataBase
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.log

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  logger.info { "Configuration Identifier: ${Configuration.General.identifier}" }
  Runtime.getRuntime().addShutdownHook(
      Thread {
        run {
          DataBase.close()
          logger.info { "Database closed" }
        }
      }
  )


  if (args.isNotEmpty()) {
    when (args[0].toLowerCase()) {
      "flickr" -> {
        logger.info { "flickr" }
        DemeterCrawler.crawlFlickr()
      }
      "twitter" -> {
        logger.info { "twitter" }
        //Twitter4JCrawler().test()
        //TwitterCrawler.crawlFor("#fantasybasel")
        DemeterCrawler.crawlTwitter()
      }
      "instagram" -> {
        logger.info { "instagram" }
        DemeterCrawler.crawlInstagram()
      }
      else -> {
        logger.error { "Unknown cli-argument(s): $args" }
      }
    }
  } else {
    performCrawling()
  }

  /*
val crawler = Crawler<String>() {
  emptyList()
}*/
  /*
Crawler(ImageProvider<T>, {
  img -> // create meta data list
})*/
}

fun performCrawling() {
  logger.info { "Crawling until ${Configuration.General.untilText()}" }
  Thread.setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler { t, e ->
    run {
      logger.error { "Unhandled exception in $t: $e" }
      logger.error(e) {
        "Exception: "
      }
    }
  })
  listenToCommands()
  while (running) {
    val start = System.currentTimeMillis()
    DemeterCrawler.crawlFlickr()
    DemeterCrawler.crawlTwitter()
    DemeterCrawler.crawlInstagram()
    val delta = (System.currentTimeMillis() - start) / 1000 // To get seconds
    var sleep = if (delta < Configuration.General.inteval) Configuration.General.inteval - delta else 0
    logger.info { "Sleeping for $sleep s" }
    if(!Configuration.General.withinTimeBounds()){
      logger.info { "Run out of time. stopping" }
      System.exit(0)
    }
    Thread.sleep(sleep * 1000)
    running = Configuration.General.withinTimeBounds()
  }
}

fun listenToCommands() {
  Thread {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    logger.info { "Demeter CLI started" }
    var line : String? = reader.readLine()
    while(line != null){
      val first = if (line.trim().split(" ").isEmpty()) "" else line.trim().split(" ")[0]
      when(first){
        "q", "quit", "stop", "exit" ->{
          logger.info { "Stopping...." }
          System.exit(0)
        }
      }

      line = readLine()
    }
  }.start()
}

private var running = true
