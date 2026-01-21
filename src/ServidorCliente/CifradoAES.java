package ServidorCliente;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CifradoAES {

    // Esta clave debe tener EXACTAMENTE 16 caracteres para AES-128
    // En un entorno real, esto no se guardaría en el código, pero para la práctica es perfecto.
    private static final String CLAVE = "Secreto123456789";
    private static final String ALGORITMO = "AES";

    /**
     * Toma un texto normal y lo convierte en galimatías cifrado.
     * @param datos El mensaje original (ej: "Hola mundo")
     * @return El mensaje cifrado en formato Base64 (para que sea texto imprimible)
     */
    public static String cifrar(String datos) {
        try {
            // 1. Crear la llave AES a partir de nuestro String secreto
            SecretKeySpec secretKey = new SecretKeySpec(CLAVE.getBytes(StandardCharsets.UTF_8), ALGORITMO);

            // 2. Instanciar el cifrador
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // 3. Inicializar en modo ENCRIPTAR
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // 4. Cifrar los datos
            byte[] datosCifrados = cipher.doFinal(datos.getBytes(StandardCharsets.UTF_8));

            // 5. Convertir bytes a texto Base64 para poder enviarlo por el socket sin romperse
            return Base64.getEncoder().encodeToString(datosCifrados);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Toma el galimatías cifrado y recupera el mensaje original.
     * @param datosCifrados El texto en Base64 que recibimos
     * @return El mensaje original legible
     */
    public static String descifrar(String datosCifrados) {
        try {
            // 1. Crear la misma llave
            SecretKeySpec secretKey = new SecretKeySpec(CLAVE.getBytes(StandardCharsets.UTF_8), ALGORITMO);

            // 2. Instanciar el cifrador
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // 3. Inicializar en modo DESENCRIPTAR
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // 4. Decodificar de Base64 a bytes
            byte[] bytesCifrados = Base64.getDecoder().decode(datosCifrados);

            // 5. Descifrar
            byte[] datosDescifrados = cipher.doFinal(bytesCifrados);

            // 6. Convertir a String
            return new String(datosDescifrados, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.err.println("Error al descifrar: " + e.getMessage());
            return null;
        }
    }

    // Un main pequeñito solo para probar que la clase funciona antes de usarla en red
    public static void main(String[] args) {
        String mensajeOriginal = "Prueba de seguridad";
        String cifrado = cifrar(mensajeOriginal);
        String descifrado = descifrar(cifrado);

        System.out.println("Original:  " + mensajeOriginal);
        System.out.println("Cifrado:   " + cifrado);
        System.out.println("Descifrado: " + descifrado);
    }
}