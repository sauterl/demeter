package com.github.sauterl.demeter.twitter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
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
  val resp = deserializeTwitterResponse(e.body)
  var photos = 0
  resp.statuses.forEach {
    if (it.entities.media != null && !it.entities.media.isEmpty()) {
      println("User ${it.user.screen_name} has photo: ${it.entities.media[0].media_url}")
      photos++
    } else {
      println("User ${it.user.screen_name}'s tweet, without a photo")
    }
  }
  println("The ${resp.statuses.size} tweets contained $photos with media")

}

fun deserializeTwitterResponse(json: String): TwitterResponse {
  return jacksonObjectMapper().readValue(json)
}

