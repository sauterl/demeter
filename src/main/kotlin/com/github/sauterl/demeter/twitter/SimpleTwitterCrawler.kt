package com.github.sauterl.demeter.twitter

import twitter4j.Query
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class SimpleTwitterCrawler {

    fun test(){
        // Thanks @lucaro for the example.

        val cb = ConfigurationBuilder()
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_PUBLIC)
                .setOAuthConsumerSecret(TWITTER_PRIVATE)
                .setOAuthAccessToken(TWITTER_TOKEN)
                .setOAuthAccessTokenSecret(TWITTER_TOKEN_SECRET)
                .setIncludeEntitiesEnabled(true)
        val tf = TwitterFactory(cb.build())
        val twitter = tf.instance

        val query = Query("#fantasybasel")
        query.resultType = Query.ResultType.recent

        val result = twitter.search(query)

        result.tweets.forEach {
            println(it.user.screenName)
            println(it.text)
            it.urlEntities.forEach {
                println(it.expandedURL)
            }
        }
    }




}