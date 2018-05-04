package com.github.sauterl.demeter

import com.github.sauterl.demeter.flickr.FlickrCrawler
import com.github.sauterl.demeter.flickr.SimpleFlickrCrawler
import com.github.sauterl.demeter.twitter.TwitterCrawler

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun main(args: Array<String>) {
  println("Started")
  if (args.isNotEmpty()) {
    when (args[0].toLowerCase()) {
      "flickr" -> {
        FlickrCrawler.crawlFor("fantasybasel", 20)
        FlickrCrawler.close()
      }
      "twitter" -> {
        //Twitter4JCrawler().test()
        TwitterCrawler.crawlFor("fantasybasel")
        TwitterCrawler.close()
      }
      else -> {
        println("No mode specified. Known modes: 'flickr', 'twitter'")
      }
    }
  } else {
    SimpleFlickrCrawler().test()
  }
}
