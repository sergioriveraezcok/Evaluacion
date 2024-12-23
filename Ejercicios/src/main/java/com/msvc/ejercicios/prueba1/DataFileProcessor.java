package com.msvc.ejercicios.prueba1;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.*;

/**
 * Clase que representa un usuario con nombre y edad.
 * Utiliza un record para simplificar la representación de datos inmutables.
 */
record User(String name, int age) {
}

/**
 * Clase que procesa un archivo de datos para filtrar usuarios en un rango de edad específico.
 */
public class DataFileProcessor {

    private static final Logger logger = Logger.getLogger(DataFileProcessor.class.getName());

    /**
     * Método principal para ejecutar el procesamiento de datos.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Rutas de entrada y salida
        String inputFilePath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "input_large.txt";
        String outputFilePath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "output_large.txt";

        try {
            logger.info("Iniciando procesamiento de datos...");
            processUserData(inputFilePath, outputFilePath);
            logger.info("El procesamiento ha finalizado exitosamente.");
        } catch (Exception e) {
            logger.severe("Error durante el procesamiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Procesa un archivo de entrada para filtrar usuarios entre las edades de 25 y 40,
     * y escribe los resultados en un archivo de salida.
     *
     * @param inputFilePath Ruta del archivo de entrada.
     * @param outputFilePath Ruta del archivo de salida.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void processUserData(String inputFilePath, String outputFilePath) throws IOException {
        // Leer líneas del archivo usando Streams
        try (Stream<String> lines = Files.lines(Paths.get(inputFilePath))) {
            logger.info("Procesando archivo: " + inputFilePath);

            // Configuración de un pool de hilos para el procesamiento paralelo
            ForkJoinPool customThreadPool = new ForkJoinPool(4);

            List<String> filteredUsers = customThreadPool.submit(() ->
                    lines.parallel()
                            .map(DataFileProcessor::parseUser) // Convertir cada línea a un objeto User
                            .filter(user -> user.age() >= 25 && user.age() <= 40) // Filtrar edades entre 25 y 40
                            .map(user -> user.name() + "," + user.age()) // Convertir de vuelta a String
                            .collect(Collectors.toList())
            ).join();

            // Escribir los resultados en el archivo de salida
            Files.write(Paths.get(outputFilePath), filteredUsers, StandardOpenOption.CREATE);
            logger.info("Archivo procesado y guardado en: " + outputFilePath);
        }
    }

    /**
     * Convierte una línea de texto en un objeto {@link User}.
     *
     * @param line Línea del archivo con formato "nombre,edad".
     * @return Objeto {@link User} con los datos procesados.
     * @throws IllegalArgumentException Si la línea tiene un formato inválido.
     */
    private static User parseUser(String line) {
        try {
            String[] parts = line.split(","); // Separar por comas: "nombre,edad"
            String name = parts[0].trim();
            int age = Integer.parseInt(parts[1].trim());
            return new User(name, age);
        } catch (Exception e) {
            logger.warning("Formato de línea inválido: " + line);
            throw new IllegalArgumentException("Formato de línea inválido: " + line);
        }
    }
}