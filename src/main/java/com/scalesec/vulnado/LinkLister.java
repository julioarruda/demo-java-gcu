package com.scalesec.vulnado;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.*;
import java.util.logging.Logger; // Incluido por GFT AI Impact Bot

public class LinkLister {
  private static final Logger LOGGER = Logger.getLogger(LinkLister.class.getName()); // Incluido por GFT AI Impact Bot

  private LinkLister() { // Incluido por GFT AI Impact Bot
    // Construtor privado para ocultar o público implícito
  }

  public static List<String> getLinks(String url) throws IOException {
    List<String> result = new ArrayList<>(); // Alterado por GFT AI Impact Bot
    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a");
    for (Element link : links) {
      result.add(link.absUrl("href"));
    }
    return result;
  }

  public static List<String> getLinksV2(String url) throws BadRequest {
    try {
      URL aUrl= new URL(url);
      String host = aUrl.getHost();
      LOGGER.info(host); // Alterado por GFT AI Impact Bot
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")){
        throw new BadRequest("Use of Private IP");
      } else {
        return getLinks(url);
      }
    } catch(Exception e) {
      throw new BadRequest(e.getMessage());
    }
  }
}