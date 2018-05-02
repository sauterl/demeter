package com.github.sauterl.demeter.twitter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import java.net.URL
import java.net.URLEncoder


/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */


val service = ServiceBuilder(TWITTER_PUBLIC).apiSecret(TWITTER_PRIVATE).build(TwitterApi.instance())
val request = OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/search/tweets.json?q=%23" + URLEncoder.encode("fantasybasel", "UTF-8") + "&count=100&result_type=mixed&lang=en&tweet_mode=extended")
val accessToken = OAuth1AccessToken(TWITTER_TOKEN, TWITTER_TOKEN_SECRET)

fun test() {
    service.signRequest(accessToken, request)
    val e = service.execute(request)
    println(e.body)

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterTweet(
        val created_at: String, // Actually valid JSON date?
        val id_str: String,
        val full_text: String, // TODO many more
        val source:String // Not really usable
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterMedia(
        val id_str: String,
        val media_url: URL,
        val media_url_https: URL,
        val url: URL, // THE URL
//        val type: MediaType // Needs custom deserializer?
        val type:String
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
}

@JsonIgnoreProperties(ignoreUnknown = true) data class TwitterEntity(
        val media:List<TwitterMedia>
)