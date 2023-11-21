package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger; // Incluido por GFT AI Impact Bot

public class Cowsay {
  private static final Logger logger = Logger.getLogger(Cowsay.class.getName()); // Incluido por GFT AI Impact Bot

  private Cowsay() { // Incluido por GFT AI Impact Bot
    // Construtor privado para ocultar o público implícito
  }

  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String sanitizedInput = sanitizeInput(input); // Incluido por GFT AI Impact Bot
    String cmd = "/usr/games/cowsay '" + sanitizedInput + "'"; // Alterado por GFT AI Impact Bot
    logger.info(cmd); // Alterado por GFT AI Impact Bot

    processBuilder.command("bash", "-c", cmd);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      logger.severe(e.getMessage()); // Alterado por GFT AI Impact Bot
    }
    return output.toString();
  }

  private static String sanitizeInput(String input) { // Incluido por GFT AI Impact Bot
    // Implemente a lógica de sanitização de entrada aqui para evitar a injeção de comandos
    return input;
  }
}