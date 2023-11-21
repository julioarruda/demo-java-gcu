package com.scalesec.vulnado;

// Removed unused imports
// import org.springframework.boot.*;
// import org.springframework.stereotype.*;

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

  // Removed wildcard CORS origins and replaced with specific domain
  // Make sure to replace 'yourdomain.com' with your actual domain
  // @CrossOrigin(origins = "*")
  @CrossOrigin(origins = "http://yourdomain.com") // Alterado por GFT AI Impact Bot
  @PostMapping(value = "/login", produces = "application/json", consumes = "application/json") // Alterado por GFT AI Impact Bot
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
  private String username; // Alterado por GFT AI Impact Bot
  private String password; // Alterado por GFT AI Impact Bot

  // Added accessors
  public String getUsername() { return this.username; } // Incluido por GFT AI Impact Bot
  public String getPassword() { return this.password; } // Incluido por GFT AI Impact Bot
}

class LoginResponse implements Serializable {
  private static final String token; // Alterado por GFT AI Impact Bot
  public LoginResponse(String msg) { this.token = msg; }

  // Added accessor
  public String getToken() { return this.token; } // Incluido por GFT AI Impact Bot
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class Unauthorized extends RuntimeException {
  public Unauthorized(String exception) {
    super(exception);
  }
}