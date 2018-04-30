package com.github.sauterl.demeter;

import com.aetrion.flickr.FlickrException;
import com.github.sauterl.demeter.flickr.SimpleFlickrCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Main entrypoint for this application
 *
 * @author loris.sauter
 */
public class Main {
  
  public static final Logger LOGGER = LogManager.getLogger();
  
  public static final String NAME = "Demeter";
  
  public static void main(String[] args) throws ParserConfigurationException, SAXException, FlickrException, IOException {
    LOGGER.info("Starting demeter");
    new SimpleFlickrCrawler().test();
  }
}
