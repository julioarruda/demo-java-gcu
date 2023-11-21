package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Cowsay {
  private static final Logger LOGGER = Logger.getLogger(Cowsay.class.getName());

  // Incluido por GFT AI Impact Bot
  private Cowsay() {
    throw new IllegalStateException("Utility class");
  }

  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    // Alterado por GFT AI Impact Bot
    // Evitando a injeção de comandos indesejados
    String cmd = "/usr/games/cowsay";
    processBuilder.command("bash", "-c", cmd, input);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      // Alterado por GFT AI Impact Bot
      // Substituído o System.err por um logger
      LOGGER.severe(e.getMessage());
    }
    // Alterado por GFT AI Impact Bot
    // Removido o comando de impressão para evitar a exposição de informações sensíveis
    return output.toString();
  }
}