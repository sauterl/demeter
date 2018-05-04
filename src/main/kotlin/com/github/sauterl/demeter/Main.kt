package com.github.sauterl.demeter

import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.flickr.FlickrCrawler
import com.github.sauterl.demeter.twitter.TwitterCrawler

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun main(args: Array<String>) {
  println("Started")
  println("Test: ${Configuration.General.identifier}")


  return

  if (args.isNotEmpty()) {
    when (args[0].toLowerCase()) {
      "flickr" -> {
        println("flickr")
        FlickrCrawler.crawlFor("fantasybasel", 20)
        FlickrCrawler.close()
      }
      "twitter" -> {
        println("twitter")
        //Twitter4JCrawler().test()
        TwitterCrawler.crawlFor("fantasybasel")
        TwitterCrawler.close()
      }
      else -> {
        println("No mode specified. Known modes: 'flickr', 'twitter'")
      }
    }
  } else {
    println("Provide a social media identifier (e.g. demeter.jar flickr)\n" +
        "Available identifiers: flickr, twitter")
  }
}
