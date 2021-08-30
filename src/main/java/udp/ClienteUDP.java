
package udp;

import java.net.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al solicitador de numeros aleatorios");
        System.out.println("Ingrese la ip del servidor: ");
        String ip = scanner.nextLine();
        System.out.println("Ingrese el puerto del servidor: ");
        int puerto = scanner.nextInt();
        try {
            DatagramSocket clienteSocket = new DatagramSocket();
            int nroSolicitudes = 1;
            while (nroSolicitudes!=0) {

                System.out.print("¿Cuantas solicitudes desea hacer?: ");
                nroSolicitudes = scanner.nextInt(); // Número de horas que se desea recibir
                InetAddress address = InetAddress.getByName(ip); // Dirección IP del servidor

                //address = InetAddress.getByName("localhost"); // Se asigna la IP local


                String mensaje = String.valueOf(nroSolicitudes); //Convertimos el nroDeSolictudes de int a String
                byte[] mensaje_bytes = mensaje.getBytes(); //Del string obtengo su valor en bytes
                System.out.println(Arrays.toString(mensaje_bytes));
                DatagramPacket paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
                clienteSocket.send(paquete);

                int contador = 0;
                clienteSocket.setSoTimeout(1000);
                try {
                    do {

                        byte[] recogerServidor_bytes = new byte[256];
                        DatagramPacket servPaquete = new DatagramPacket(recogerServidor_bytes, 256);

                        clienteSocket.receive(servPaquete); // Se recibe la respuesta
                        String cadenaMensaje = new String(recogerServidor_bytes).trim(); /* Se almacena la respuesta
                                                                                         en una variable String */
                        if (!cadenaMensaje.equals("")) {
                            System.out.println(cadenaMensaje); // Se imprime la hora recibida del servidor
                            contador++;
                        }
                    } while (contador < nroSolicitudes);
                } catch (SocketTimeoutException e) {
                    System.out.println("No se ha podido conectar al servidor");
                    clienteSocket.close();
                }
            }
            } catch (InputMismatchException e) { /* Este mensaje de error se despliega en caso de no introducir un
                                           número para enviar, pero no afecta a la continuidad misma del programa */
                System.err.println("Error, ¡ingrese un numero! " + e.getMessage());
                scanner.nextLine();
            } catch (Exception e) { /* En caso de sufrir cualquier otro error, se despliega el mensaje de error y
                                     el programa se cierra */
                System.err.println(e.getMessage());

            }

    }
}
