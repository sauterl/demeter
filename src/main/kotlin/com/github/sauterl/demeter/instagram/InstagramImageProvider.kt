package com.github.sauterl.demeter.instagram

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.utils.extractTitle
import kotlin.math.min

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class InstagramImageProvider : ImageProvider<InstagramWebNode> {
  override fun serve(tag: String): List<ConcreteImage<InstagramWebNode>> {
    return InstagramWebScraper.retrieveNodesForTag(tag, Configuration.Instagram.amount).filter { n -> !n.is_video }.map {
      val title = extractTitle(it.captionText ?: "n/a", 5, "${it.ownerId}'s image")
      val img = AbstractImage(it.id, title, it.display_url.toExternalForm())
      return@map ConcreteImage(img, it)
    }
  }
}
