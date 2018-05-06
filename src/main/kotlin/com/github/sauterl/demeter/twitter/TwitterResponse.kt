package com.github.sauterl.demeter.twitter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.net.URL

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterResponse(val statuses: List<TwitterTweet>) {

  companion object {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TwitterUser(
        val id_str: String,
        val name: String,
        val screen_name: String,
        val location: String?,
        val url: URL?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TwitterTweet(
        val created_at: String, // Actually valid JSON date?
        val id_str: String,
        val full_text: String, // TODO many more,
        val user: TwitterUser,
        val entities: TwitterEntity,
        val source: String // Not really usable
    ) {
      fun hasPhoto(): Boolean {
        var out = false

        entities.media?.forEach {
          if (it.isPhoto()) {
            out = true
          }
        }

        return out
      }

      fun getMedia(): List<TwitterMedia>? {
        return entities.media
      }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TwitterMedia(
        val id_str: String,
        val media_url: URL,
        val media_url_https: URL,
        val expanded_url: URL, // Also nice?
        val url: URL, // THE URL
//        val type: MediaType // Needs custom deserializer?
        val type: String
    ) {
      companion object {
        const val PHOTO = "photo"

        enum class MediaType {
          PHOTO;

          override fun toString(): String {
            return name.toLowerCase()
          }
        }
      }

      fun isPhoto(): Boolean {
        return type == PHOTO
      }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TwitterEntity(
        val media: List<TwitterMedia>?,
        val hastags: List<TwitterHashtag>?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TwitterHashtag(
        val text: String
    )
  }

}
