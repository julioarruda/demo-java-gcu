package com.scalesec.vulnado;

// import org.springframework.boot.*; // Removido por GFT AI Impact Bot
// import org.springframework.http.HttpStatus; // Removido por GFT AI Impact Bot
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
// import java.util.List; // Removido por GFT AI Impact Bot
// import java.io.Serializable; // Removido por GFT AI Impact Bot
import java.io.IOException;


@RestController
@EnableAutoConfiguration
public class LinksController {
  @RequestMapping(value = "/links", method = RequestMethod.GET, produces = "application/json") // Alterado por GFT AI Impact Bot
  List<String> links(@RequestParam String url) throws IOException{
    return LinkLister.getLinks(url);
  }
  @RequestMapping(value = "/links-v2", method = RequestMethod.GET, produces = "application/json") // Alterado por GFT AI Impact Bot
  List<String> linksV2(@RequestParam String url) throws BadRequest{
    return LinkLister.getLinksV2(url);
  }
}