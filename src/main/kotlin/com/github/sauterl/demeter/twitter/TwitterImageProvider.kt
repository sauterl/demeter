package com.github.sauterl.demeter.twitter

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.utils.extractTitle
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
      val title = extractTitle(it.full_text, 5, def="${it.user.screen_name}'s image")

      if (it.getMedia() != null) {
        val medias = it.getMedia()!!
        medias.forEach { m ->
          run {
            val img = AbstractImage(it.id_str, title, m.media_url.toString())
            list.add(ConcreteImage(img, TwitterMediaPhoto(it, m)))
          }
        }
      }

    }
    return list.toList()
  }
}
