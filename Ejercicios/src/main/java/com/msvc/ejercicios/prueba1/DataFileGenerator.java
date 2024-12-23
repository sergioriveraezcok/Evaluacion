package com.msvc.ejercicios.prueba1;

import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Generador de datos para crear un archivo con registros aleatorios.
 */
public class DataFileGenerator {

    private static final Logger logger = Logger.getLogger(DataFileGenerator.class.getName());

    /**
     * Método principal para generar un archivo de datos.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Ruta para el archivo de entrada
        String inputFilePath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "input_large.txt";

        try {
            logger.info("Generando archivo con datos aleatorios...");
            generateLargeFile(inputFilePath, 1_000_000);
            logger.info("Archivo generado exitosamente en: " + inputFilePath);
        } catch (IOException e) {
            logger.severe("Error durante la generación del archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Genera un archivo con registros aleatorios de usuarios.
     *
     * @param filePath Ruta del archivo a generar.
     * @param recordCount Cantidad de registros a generar.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void generateLargeFile(String filePath, int recordCount) throws IOException {
        Random random = new Random();
        String[] names = {
                "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank",
                "Ivan", "Judy", "Kevin", "Laura", "Megan", "Nate", "Olivia", "Paul",
                "Quincy", "Rachel", "Steve", "Tracy", "Uma", "Victor", "Wendy", "Xander",
                "Yvonne", "Zack"
        };

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (int i = 0; i < recordCount; i++) {
                String name = names[random.nextInt(names.length)];
                int age = random.nextInt(60) + 18; // Edad aleatoria entre 18 y 77
                writer.write(name + "," + age);
                writer.newLine();
            }
        }
    }
}