package com.github.sauterl.demeter.twitter

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.sauterl.demeter.config.Configuration
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import java.io.File
import java.net.URLEncoder

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class TwitterInterface {

  fun searchTweets(query: String): TwitterResponse {
    println("C: ${Configuration.config}")
    println("T: ${Configuration.Twitter}")
    val service = ServiceBuilder(TWITTER_PUBLIC).apiSecret(TWITTER_PRIVATE).build(TwitterApi.instance())
    val request = OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode("$query", "UTF-8") + "&count=100&result_type=mixed&lang=en&tweet_mode=extended")
    val accessToken = OAuth1AccessToken(TWITTER_TOKEN, TWITTER_TOKEN_SECRET)
    service.signRequest(accessToken, request)
    val e = service.execute(request)
    writeTweets(e.body)
    return deserializeTwitterResponse(e.body)
  }

  fun deserializeTwitterResponse(json: String): TwitterResponse {
    return jacksonObjectMapper().readValue(json)
  }

  private fun writeTweets(pureBody: String) {
    if (Configuration.Twitter.storeTweets) {
      val mapper = jacksonObjectMapper()
      mapper.enable(SerializationFeature.INDENT_OUTPUT)
      val dir = File(Configuration.Twitter.tweetDir)
      dir.mkdirs()
      val root = mapper.readTree(pureBody)
      val statuses = root["statuses"]
      if (statuses.isArray) {
        statuses.forEach {
          val f = dir.resolve("${it["id"]}.json")
          if (!f.exists()) {
            f.writeText(mapper.writeValueAsString(it))
            println("Wrote tweet ${it["id"]} to ${f.path}")
          }
        }
      }
    }
  }
}
