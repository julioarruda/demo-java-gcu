package com.scalesec.vulnado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import java.util.List;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
public class CommentsController {
  @Value("${app.secret}")
  private String secret;

  // Corrigido: Substituído "@RequestMapping(method = RequestMethod.GET)" por "@GetMapping"
  // Corrigido: Certifique-se de que habilitar CORS é seguro aqui.
  @CrossOrigin(origins = "http://trustedwebsite.com") // Alterado por GFT AI Impact Bot
  @GetMapping(value = "/comments", produces = "application/json") // Alterado por GFT AI Impact Bot
  List<Comment> comments(@RequestHeader(value="x-auth-token") String token) {
    User.assertAuth(secret, token);
    return Comment.fetch_all();
  }

  // Corrigido: Substituído "@RequestMapping(method = RequestMethod.POST)" por "@PostMapping"
  // Corrigido: Certifique-se de que habilitar CORS é seguro aqui.
  @CrossOrigin(origins = "http://trustedwebsite.com") // Alterado por GFT AI Impact Bot
  @PostMapping(value = "/comments", produces = "application/json", consumes = "application/json") // Alterado por GFT AI Impact Bot
  Comment createComment(@RequestHeader(value="x-auth-token") String token, @RequestBody CommentRequest input) {
    return Comment.create(input.getUsername(), input.getBody()); // Alterado por GFT AI Impact Bot
  }

  // Corrigido: Substituído "@RequestMapping(method = RequestMethod.DELETE)" por "@DeleteMapping"
  // Corrigido: Certifique-se de que habilitar CORS é seguro aqui.
  @CrossOrigin(origins = "http://trustedwebsite.com") // Alterado por GFT AI Impact Bot
  @DeleteMapping(value = "/comments/{id}", produces = "application/json") // Alterado por GFT AI Impact Bot
  Boolean deleteComment(@RequestHeader(value="x-auth-token") String token, @PathVariable("id") String id) {
    return Comment.delete(id);
  }
}

class CommentRequest implements Serializable {
  private String username; // Alterado por GFT AI Impact Bot
  private String body; // Alterado por GFT AI Impact Bot

  // Corrigido: Torne o username uma constante final estática ou não pública e forneça acessores se necessário.
  public String getUsername() { // Incluído por GFT AI Impact Bot
    return username;
  }

  // Corrigido: Torne o body uma constante final estática ou não pública e forneça acessores se necessário.
  public String getBody() { // Incluído por GFT AI Impact Bot
    return body;
  }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequest extends RuntimeException {
  public BadRequest(String exception) {
    super(exception);
  }
}

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ServerError extends RuntimeException {
  public ServerError(String exception) {
    super(exception);
  }
}