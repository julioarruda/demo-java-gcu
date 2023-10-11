package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Cowsay {
  private static final Logger LOGGER = Logger.getLogger(Cowsay.class.getName());

  private Cowsay() {
    // private constructor to hide the implicit public one
  }

  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String sanitizedInput = sanitizeInput(input);
    String cmd = "/usr/games/cowsay '" + sanitizedInput + "'";

    if (System.getenv("DEBUG") == null) {
      LOGGER.log(Level.INFO, cmd);
    }

    processBuilder.command("bash", "-c", cmd);
    processBuilder.environment().put("PATH", "/usr/games/");

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Exception: ", e);
    }
    return output.toString();
  }

  private static String sanitizeInput(String input) {
    return input.replaceAll("[^a-zA-Z0-9]", "");
  }
}