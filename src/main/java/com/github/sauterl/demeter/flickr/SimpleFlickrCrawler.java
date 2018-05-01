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
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
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
    final String generalParams = "api_key=@key@&format=json";
    final String testParams = "&method=flickr.test.echo&name=value";
    final String tagParams = "method=flickr.photos.search&tags=fantasybasel";
    final String query = baseURL+"?"+tagParams+"&"+generalParams.replace("@key@",FlickrApiKey.PUBLIC_KEY); // should work (browser and insomnia)
  
    System.out.println("Query: "+query);
    
    URL url = new URL(baseURL);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();
  
    InputStream is = conn.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
    br.lines().forEach(System.out::println);
    br.close();
    is.close();
  }
  
  public void soapRequestTag() throws IOException {
    final String baseURL = "https://api.flickr.com/services/soap/";
  
    String postData = "<s:Envelope\n" +
        "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
        "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
        "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
        ">\n" +
        "\t<s:Body>\n" +
        "\t\t<x:FlickrRequest xmlns:x=\"urn:flickr\">\n" +
        "\t\t\t<method>flickr.photos.search</method>\n" +
        "\t\t\t<api_key>@key@</api_key>\n" +
        "\t\t\t<tags>fantasybasel</tags>\n" +
        "\t\t\t<format>json</format>\n" +
        "\t\t</x:FlickrRequest>\n" +
        "\t</s:Body>\n" +
        "</s:Envelope>";
    postData = postData.replace("@key@", FlickrApiKey.PUBLIC_KEY);
    System.out.println("Posting: "+postData);
  
    URL url = new URL(baseURL);
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection)con;
  
    http.setRequestMethod("POST");
    http.setDoOutput(true);
  
    byte[] data = postData.getBytes(StandardCharsets.UTF_8);
  
    http.setFixedLengthStreamingMode(data.length);
    //http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
    http.connect();
    try(OutputStream os = http.getOutputStream()){
      os.write(data);
    }
  
    System.out.println("Sent request");
  
    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
  
    System.out.println("Response: "+http.getResponseMessage());
  
    br.lines().forEach(System.out::println);
  }
  
  public void soapRequest() throws IOException {
    final String baseURL = "https://api.flickr.com/services/soap/";
    
    String postData = "<s:Envelope\n" +
        "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
        "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
        "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
        ">\n" +
        "\t<s:Body>\n" +
        "\t\t<x:FlickrRequest xmlns:x=\"urn:flickr\">\n" +
        "\t\t\t<method>flickr.test.echo</method>\n" +
        "\t\t\t<api_key>@key@</api_key>\n" +
        "\t\t\t<name>value</name>\n" +
        "\t\t\t<format>json</format>\n" +
        "\t\t</x:FlickrRequest>\n" +
        "\t</s:Body>\n" +
        "</s:Envelope>";
    postData = postData.replace("@key@", FlickrApiKey.PUBLIC_KEY);
    System.out.println("Posting: "+postData);
    
    URL url = new URL(baseURL);
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection)con;
    
    http.setRequestMethod("POST");
    http.setDoOutput(true);
    
    byte[] data = postData.getBytes(StandardCharsets.UTF_8);
    
    http.setFixedLengthStreamingMode(data.length);
    //http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
    http.connect();
    try(OutputStream os = http.getOutputStream()){
      os.write(data);
    }
  
    System.out.println("Sent request");
    
    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
  
    System.out.println("Response: "+http.getResponseMessage());
    
    br.lines().forEach(System.out::println);
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
