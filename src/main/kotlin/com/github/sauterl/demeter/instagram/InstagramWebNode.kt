package com.github.sauterl.demeter.instagram

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URL

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class InstagramWebNode(
    val id: String,
    val shortcode: String,
    val taken_at_timestamp: Number,
    val display_url: URL,
    val is_video: Boolean,
    @JsonProperty("owner") val ownerObj : InstagramWebOwner,
    @JsonProperty("edge_media_to_caption") val captionObj: InstagramWebCaption
) {

  @JsonIgnore val ownerId = ownerObj.id
  @JsonIgnore val captionText : String? = if(captionObj.edges.isEmpty()) null else captionObj.edges.first().node.text
  @JsonIgnore val sourceUrl = "https://instagram.com/p/$shortcode/"

  companion object {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class InstagramWebOwner(
        val id: String
    )
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class InstagramWebCaption(
        val edges:List<InstagramWebTextNodeContainer>
    )
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class InstagramWebTextNode(
        val text:String
    )
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class InstagramWebTextNodeContainer(
        val node:InstagramWebTextNode
    )
  }
}