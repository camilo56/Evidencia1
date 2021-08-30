package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerTCP {


    public static void main(String[] args) {
        String ruta = "C:/Users/camilo/Desktop/textos/cuento1.txt";
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingrese el puerto a escuchar: ");
        int puerto = teclado.nextInt();
        try {


            ServerSocket serverSocket = new ServerSocket(puerto);
            Socket cliente = serverSocket.accept(); //aca el programa para hasta que un cliente se conecte

            System.out.println("Se ha conectado un CLiente :)");
            DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());
            String mensajeCliente = "";

            while (!mensajeCliente.equals("EXIT")){



                mensajeCliente = dataInputStream.readUTF();
                if (mensajeCliente.equals(1)){}

                System.out.println(mensajeCliente);
                dataOutputStream.writeUTF("Mensaje recibido: "+mensajeCliente);


            }
            dataInputStream.close();
            dataOutputStream.close();
            cliente.close();



        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
