package com.scalesec.vulnado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.beans.factory.annotation.*;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
public class LoginController {
  @Value("${app.secret}")
  private String secret;

  // Corrigido: substituído "@RequestMapping(method = RequestMethod.POST)" por "@PostMapping"
  // Alterado por GFT AI Impact Bot
  @CrossOrigin(origins = "*") // Atenção: Certifique-se de que habilitar o CORS é seguro aqui.
  @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
  LoginResponse login(@RequestBody LoginRequest input) {
    User user = User.fetch(input.getUsername());
    if (Postgres.md5(input.getPassword()).equals(user.getHashedPassword())) {
      return new LoginResponse(user.token(secret));
    } else {
      throw new Unauthorized("Access Denied");
    }
  }
}

class LoginRequest implements Serializable {
  private String username; // Corrigido: tornar username não público e fornecer acessor se necessário. Alterado por GFT AI Impact Bot
  private String password; // Corrigido: tornar password não público e fornecer acessor se necessário. Alterado por GFT AI Impact Bot

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}

class LoginResponse implements Serializable {
  private static final String token; // Corrigido: tornar token uma constante estática final ou não pública e fornecer acessor se necessário. Alterado por GFT AI Impact Bot

  public LoginResponse(String msg) { this.token = msg; }

  public String getToken() {
    return token;
  }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class Unauthorized extends RuntimeException {
  public Unauthorized(String exception) {
    super(exception);
  }
}