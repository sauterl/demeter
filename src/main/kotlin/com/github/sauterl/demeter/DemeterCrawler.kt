package com.github.sauterl.demeter

import com.github.sauterl.demeter.api.Crawler
import com.github.sauterl.demeter.cineast.Item
import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.flickr.FlickrImageProvider
import com.github.sauterl.demeter.instagram.InstagramImageProvider
import com.github.sauterl.demeter.twitter.TwitterImageProvider
import mu.KotlinLogging

/**
 *
 * CONVENTION: cineast-metadata: source = source of the image, sourceURL = source of the social media item (e.g. the tweet / the flickr post / the instagram post
 *
 * @author loris.sauter
 */
object DemeterCrawler {

  private val logger = KotlinLogging.logger {}

  fun crawlFlickr() {

    val flickrCrawler = Crawler("FliCrawler",FlickrImageProvider()) {
      val list: MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl", it.img.getUrl().toExternalForm()))
      list.add(Item.Companion.MetaData("ownerName", it.img.ownername ?: "n/a"))
      list.add(Item.Companion.MetaData("ownerId", it.img.owner))
      list.add(Item.Companion.MetaData("datetaken", it.img.datetaken ?: "n/a"))
      list.add(Item.Companion.MetaData("uploaded", it.img.dateupload ?: "n/a"))
      list.add(Item.Companion.MetaData("tags", it.img.tagList() ?: "n/a"))

      return@Crawler list.toList()
    }
    Configuration.Flickr.queryList.forEach {
      logger.info { "Crawling flickr for $it" }
      flickrCrawler.crawlFor(it)
    }

  }

  fun crawlTwitter() {
    val twitterCrawler = Crawler("TwitterCrawler",TwitterImageProvider()) {
      val media = it.img.media
      val list: MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl", media.url.toExternalForm()))
      list.add(Item.Companion.MetaData("ownerId", it.img.tweet.user.id_str))
      list.add(Item.Companion.MetaData("ownerName", it.img.tweet.user.name))
      list.add(Item.Companion.MetaData("ownerDisplay", it.img.tweet.user.screen_name))
      list.add(Item.Companion.MetaData("tweet", it.img.tweet.full_text))
      list.add(Item.Companion.MetaData("created_at", it.img.tweet.created_at))

      return@Crawler list.toList()

    }
    Configuration.Twitter.queryList.forEach {
      logger.info { "Crawling twitter for $it" }
      twitterCrawler.crawlFor(it)
    }
  }

  fun crawlInstagram() {
    val instaGrawler = Crawler("InstaGrawler", InstagramImageProvider()) {
      val list: MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl", it.img.sourceUrl))
      list.add(Item.Companion.MetaData("ownerId", it.img.ownerId))
      list.add(Item.Companion.MetaData("taken_at", "${it.img.taken_at_timestamp}"))
      list.add(Item.Companion.MetaData("caption", it.img.captionText ?: "n/a"))


      return@Crawler list.toList()
    }
    Configuration.Instagram.queryList.forEach {
      logger.info { "Crawling instagram for $it" }
      instaGrawler.crawlFor(it)
    }
  }
}
