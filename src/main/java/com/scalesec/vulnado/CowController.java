package com.scalesec.vulnado;

import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;

// Removido import não utilizado 'java.io.Serializable'
// Alterado por GFT AI Impact Bot

@RestController
@EnableAutoConfiguration
public class CowController {
    // Adicionado método HTTP específico para garantir a segurança
    // Alterado por GFT AI Impact Bot
    @RequestMapping(value = "/cowsay", method = RequestMethod.GET)
    String cowsay(@RequestParam(defaultValue = "I love Linux!") String input) {
        return Cowsay.run(input);
    }
}