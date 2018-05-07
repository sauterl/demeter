package com.github.sauterl.demeter.twitter

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage
import kotlin.math.min

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class TwitterImageProvider : ImageProvider<TwitterMediaPhoto> {
  override fun serve(tag: String): List<ConcreteImage<TwitterMediaPhoto>> {
    val twitter = TwitterInterface()
    val resp = twitter.searchTweets(tag)
    val photoTweets = resp.statuses.filter { it.hasPhoto() }
    val list: MutableList<ConcreteImage<TwitterMediaPhoto>> = mutableListOf()
    photoTweets.forEach {
      val words = it.full_text.split(" ")
      val sb = StringBuffer()
      when {
        words.size in 2..5 -> {
          val end = min(5, words.size)
          words.subList(0, end).forEach { w ->
            sb.append("$w ")
          }
        }
        words.isEmpty() -> sb.append("n/a")
        else -> sb.append(words[0])
      }
      sb.toString()

      if (it.getMedia() != null) {
        val medias = it.getMedia()!!
        medias.forEach { m ->
          run {
            val img = AbstractImage(it.id_str, sb.toString().trimEnd(), m.media_url.toString())
            list.add(ConcreteImage(img, TwitterMediaPhoto(it, m)))
          }
        }
      }

    }
    return list.toList()
  }
}
