package com.github.sauterl.demeter.flickr;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.test.TestInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

/**
 * TODO: Write JavaDoc
 *
 * @author loris.sauter
 */
public class SimpleFlickrCrawler {
  
  public void labour() throws IOException {
    //https://stackoverflow.com/a/1359700
    final String baseURL = "https://api.flickr.com/services/rest/";
    final String generalParams = "?api_key=@key@&format=json";
    final String params = "&method=flickr.test.echo&name=value";
    final String query = generalParams.replace("@key@",FlickrApiKey.PUBLIC_KEY)+params;
  
    System.out.println("Query: "+query);
    
    URL url = new URL(baseURL);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestProperty("Content-Length",Integer.toString(query.getBytes().length));
    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
    dos.writeBytes(query);
    dos.close();
  
    InputStream is = conn.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
    br.lines().forEach(System.out::println);
    br.close();
  }
  

  public void test() throws ParserConfigurationException, SAXException, IOException, FlickrException {
    ///Flickr f = new Flickr(ApiKeyStore.FLICKR_PUBLIC,ApiKeyStore.FLICKR_PRIVATE, new REST());
    Flickr f = new Flickr(FlickrApiKey.PUBLIC_KEY, FlickrApiKey.PRIVATE_KEY, new REST());
    TestInterface ti = f.getTestInterface();
    Parameter p = new Parameter("p1", "v1");
    Parameter p2 = new Parameter("p2", 1);
    Collection c = ti.echo(Arrays.asList(p,p2));
    Logger l = LogManager.getLogger();
    l.info("Result: {}", c);
  }
  
}
