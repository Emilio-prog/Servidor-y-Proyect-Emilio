package ServidorCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            // 1. Crear un ServerSocket en el puerto 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor escuchando en el puerto 12345...");

            // 2. Esperar y aceptar conexiones de clientes
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

            // 3. Crear flujos para la comunicación
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            //Recepción y Descifrado
            String mensajeRecibido = in.readLine();
            System.out.println("Recibido (Cifrado): " + mensajeRecibido);

            // 1. Desciframos el mensaje
            String mensajeDescifrado = CifradoAES.descifrar(mensajeRecibido);

            // 2. Mostramos el mensaje real
            System.out.println("Mensaje Descifrado (Original): " + mensajeDescifrado);

            //Responder al cliente
            out.println("Mensaje recibido y descifrado correctamente.");

            // 6. Cerrar los recursos
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}