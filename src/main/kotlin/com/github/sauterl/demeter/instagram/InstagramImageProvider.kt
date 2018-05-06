package com.github.sauterl.demeter.instagram

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.config.Configuration
import kotlin.math.min

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class InstagramImageProvider : ImageProvider<InstagramWebNode> {
  override fun serve(tag: String): List<ConcreteImage<InstagramWebNode>> {
    return InstagramWebScraper.retrieveNodesForTag(tag, Configuration.Instagram.amount).filter { n -> !n.is_video }.map {
      val title = if (it.captionText != null) {
        val words = it.captionText.split(" ")
        println("Words: $words")
        val sb = StringBuffer()
        if (words.size in 2..5) {
          val end = min(5, words.size)
          words.subList(0, end).forEach { w ->
            sb.append("$w ")
          }
        } else if (words.isEmpty()) {
          sb.append("n/a")
        } else {
          sb.append(words[0])
        }
        sb.toString()
      } else {
        "n/a"
      }
      val img = AbstractImage(it.id, title, it.display_url.toExternalForm())
      return@map ConcreteImage(img, it)
    }
  }
}
