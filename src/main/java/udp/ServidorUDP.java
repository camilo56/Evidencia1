
package udp;

import java.net.*;
import java.util.Scanner;

/*
* Monitorear desde el cliente, la hora de servidor, incluidos segundos y milisegundos.
* */
public class ServidorUDP {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el puerto a escucha: ");
        int puertoServidor = teclado.nextInt();
        
        DatagramSocket socket;
        try{

            socket = new DatagramSocket(puertoServidor);//OJO: puertos de los sockets deben ser valores altos
            System.out.println("Escuchando puerto: "+puertoServidor);
            String mensajeRecibido = "";

            String mensajeComp = "";

            int puertoCliente;
            InetAddress addressCliente;

            do{
                byte[] mensajeRecibidoBytes = new byte[256]; //Mensaje que recibe, cuantas "holas" desea el cliente

                DatagramPacket paquete = new DatagramPacket(mensajeRecibidoBytes,256);
                socket.receive(paquete);
                mensajeRecibido = new String(mensajeRecibidoBytes).trim();
                System.out.println(mensajeRecibido);

                puertoCliente = paquete.getPort(); /* El puerto para enviar la respuesta se obtiene del mismo
                                                                    paquete recibido de la solicitud */
                addressCliente = paquete.getAddress(); /* La dirección IP para enviar la respuesta se obtiene
                                                           del mismo  paquete recibido de la solicitud */
                if(mensajeRecibido !=null) {
                    int numeroDeSolicitudes = Integer.parseInt(mensajeRecibido);
                    for (int i = 0; i < numeroDeSolicitudes; i++) {     /* Se registra el tiempo exacto, se
                       comprime en el mensaje y se envía en un paquete al cliente en forma de un arreglo de bytes */
                        int random = (int)(Math.random()*(numeroDeSolicitudes-1.0) + 1);
                        mensajeComp = "("+i+") "+random; // Se crea el mensaje que irá en el paquete UDP
                        byte[] mensajeEnviarBytes = mensajeComp.getBytes(); /* Se obtiene el arreglo de bytes equivalente del
                                                                                 mensaje(Lo que se envía) */
                        System.out.println(mensajeComp); // Se imprime para el servidor el mensaje que se enviará

                        DatagramPacket paqueteEnv = new DatagramPacket(mensajeEnviarBytes, mensajeComp.length(), addressCliente, puertoCliente);
                                                 // Se crea el paquete y se inicializa el paquete

                        socket.send(paqueteEnv); // Se envía el paquete a su destino
                    }
                }
            }while(!mensajeRecibido.equals("0"));
            socket.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
