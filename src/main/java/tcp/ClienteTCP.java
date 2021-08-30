package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese la dirección ip a conectar: ");
        String ipDestino = teclado.nextLine();
        System.out.println("Ingrese el puerto: ");
        int puertoDestino = teclado.nextInt();
        System.out.println("Conectando....");
        try {
            Socket socketCliente = new Socket(ipDestino,puertoDestino);

            System.out.println("Conexion establecida :)");
            DataOutputStream dataOutputStream = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socketCliente.getInputStream());
            String mensaje = "";
            while (!mensaje.equals("EXIT")) {
                mensaje = teclado.nextLine();
                System.out.println("Enviando Mensaje: " + mensaje);

                dataOutputStream.writeUTF(mensaje);
                socketCliente.setKeepAlive(true);
                System.out.println(dataInputStream.readUTF());
                if (mensaje.equals("EXIT")) {
                    //dataOutputStream.close();
                    //socketCliente.close();
                }
            }
        }catch (ConnectException e){
            System.err.println("Error: Conexion rechazada");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
