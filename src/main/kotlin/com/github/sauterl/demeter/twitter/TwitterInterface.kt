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
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class TwitterInterface {


    fun searchTaggedTweets(tag:String):TwitterResponse{
        val service = ServiceBuilder(TWITTER_PUBLIC).apiSecret(TWITTER_PRIVATE).build(TwitterApi.instance())
        val request = OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/search/tweets.json?q=%23" + URLEncoder.encode("$tag", "UTF-8") + "&count=100&result_type=mixed&lang=en&tweet_mode=extended")
        val accessToken = OAuth1AccessToken(TWITTER_TOKEN, TWITTER_TOKEN_SECRET)
        service.signRequest(accessToken, request)
        val e = service.execute(request)
        return deserializeTwitterResponse(e.body)
    }

    fun deserializeTwitterResponse(json: String): TwitterResponse {
        return jacksonObjectMapper().readValue(json)
    }
}
