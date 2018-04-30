package com.github.sauterl.demeter.flickr;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.test.TestInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * TODO: Write JavaDoc
 *
 * @author loris.sauter
 */
public class SimpleFlickrCrawler {
  

  public void test() throws ParserConfigurationException, SAXException, IOException, FlickrException {
    ///Flickr f = new Flickr(ApiKeyStore.FLICKR_PUBLIC,ApiKeyStore.FLICKR_PRIVATE, new REST());
    Flickr f = new Flickr(FlickrApiKey.PUBLIC_KEY, FlickrApiKey.PRIVATE_KEY, new REST());
    TestInterface ti = f.getTestInterface();
    Collection c = ti.echo(Collections.singleton(Collections.EMPTY_MAP));
    Logger l = LogManager.getLogger();
    l.info("Result: {}", c);
  }
  
}
