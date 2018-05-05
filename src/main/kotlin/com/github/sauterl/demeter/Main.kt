package com.github.sauterl.demeter

import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.instagram.InstagramWebScraper
import com.github.sauterl.demeter.twitter.TwitterCrawler
import com.github.sauterl.demeter.utils.DataBase

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun main(args: Array<String>) {
  println("Started")
  println("Test: ${Configuration.General.identifier}")

  Runtime.getRuntime().addShutdownHook(
      Thread {
        run {
          DataBase.close()
          println("DataBase closed")
        }
      }
  )


  if (args.isNotEmpty()) {
    when (args[0].toLowerCase()) {
      "flickr" -> {
        println("flickr")
        DemeterCrawler.crawlFlickr()
      }
      "twitter" -> {
        println("twitter")
        //Twitter4JCrawler().test()
        //TwitterCrawler.crawlFor("#fantasybasel")
        TwitterCrawler.crawlFor("@fantasybasel")
      }
      "instagram" -> {
        val nodes = InstagramWebScraper.retrieveNodesForTag("fantasybasel",2)
        println("Received: ${nodes.size}")
        nodes.forEach {
          println("ID: ${it.id}\n\tcaption: ${it.captionText}\n\turl: ${it.display_url}\n\tsource: ${it.sourceUrl}")
        }
      }
      else -> {
        println("instagram")
        println("No mode specified. Known modes: 'flickr', 'twitter'")
      }
    }
  } else {
    println("Provide a social media identifier (e.g. demeter.jar flickr)\n" +
        "Available identifiers: flickr, twitter")
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
