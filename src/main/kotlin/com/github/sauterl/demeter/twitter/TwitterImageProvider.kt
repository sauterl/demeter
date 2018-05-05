package com.github.sauterl.demeter.twitter

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class TwitterImageProvider : ImageProvider<TwitterResponse.Companion.TwitterTweet>{
  override fun serve(tag: String): List<ConcreteImage<TwitterResponse.Companion.TwitterTweet>> {
    val twitter = TwitterInterface()
    val resp = twitter.searchTweets(tag)
    val photoTweets = resp.statuses.filter{it.hasPhoto()}
    return photoTweets.map{
      val words = it.full_text.split(" ")
      val sb = StringBuffer()
      words.subList(0, 5).forEach { w -> sb.append("$w ") }
      val media = it.getMedia()?.get(0) // TODO currently only one photo
      val img = AbstractImage(it.id_str, sb.toString().trimEnd(), media?.media_url.toString()) // Due to previous filter, shouldn't be null
      return@map ConcreteImage(img, it)
    }
  }
}