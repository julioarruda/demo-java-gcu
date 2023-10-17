package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Cowsay {

  private static final Logger LOGGER = Logger.getLogger(Cowsay.class.getName());

  private Cowsay() {
    // private constructor
  }

  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    // Validate input before using it in command
    if (!isValidInput(input)) {
      throw new IllegalArgumentException("Invalid input");
    }
    String cmd = "/usr/games/cowsay '" + input + "'";
    LOGGER.info(cmd);
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
      LOGGER.severe(e.getMessage());
    }
    return output.toString();
  }

  private static boolean isValidInput(String input) {
    // TBD: Implement input validation
    return true;
  }

}