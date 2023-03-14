package com.api.backend1.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    /**
     * Comprime uma imagem no formato de array de bytes.
     *
     * @param data O array de bytes da imagem a ser comprimida.
     * @return Um array de bytes contendo a imagem comprimida.
     */
    public static byte[] compressImage(byte[] data) {
        // Cria um objeto Deflater para comprimir a imagem.
        Deflater deflater = new Deflater();

        deflater.setLevel(Deflater.BEST_COMPRESSION); // Define o nível de compressão.
        deflater.setInput(data); // Define o input para o Deflater como o array de bytes da imagem a ser comprimida.
        deflater.finish(); // Marca o fim da entrada.

        // Cria um objeto ByteArrayOutputStream para armazenar a saída comprimida.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp); // Comprime os dados e retorna o tamanho do array resultante.
            outputStream.write(tmp, 0, size); // Escreve o array resultante no outputStream.
        }
        try {
            outputStream.close();
        }
        catch (Exception ignored) {
        }
        return outputStream.toByteArray(); // Retorna o array de bytes comprimido.
    }

    /**
     * Descomprime uma imagem no formato de array de bytes.
     *
     * @param data O array de bytes da imagem a ser descomprimida.
     * @return Um array de bytes contendo a imagem descomprimida.
     */
    public static byte[] decompressImage(byte[] data) {
        // Cria um objeto Inflater para descomprimir a imagem.
        Inflater inflater = new Inflater();
        inflater.setInput(data); // Define o input para o Inflater como o array de bytes da imagem a ser descomprimida.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp); // Descomprime os dados e retorna o tamanho do array resultante.
                outputStream.write(tmp, 0, count); // Escreve o array resultante no outputStream.
            }
            outputStream.close();
        }
        catch (Exception ignored) {
        }
        // Retorna o array de bytes descomprimido.
        return outputStream.toByteArray();
    }
}

