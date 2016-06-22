package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class MCliente extends Thread {
    
    private VCliente ventana;
    private String url;
    private int puerto;
    private String nick;
    private Socket s;
    
    public MCliente(VCliente ventana, String url, int puerto, String nick) {
        this.ventana = ventana;
        this.url = url;
        this.puerto = puerto;
        this.nick = nick;
    }
    
    public void enviarMensaje(String mensaje) {
        enviarTrama(2, mensaje);
    }
    
    public void desconectar() {
        enviarTrama(3, "");
    }
    
    private void enviarTrama(int codigo, String mensaje) {
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(codigo);
            dos.writeUTF(mensaje);
        } catch (Exception e) {
        }
    }
    
    public void run() {
        try {
            s = new Socket(url, puerto);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            enviarTrama(1, nick);
            while(true) {
                int codigo = dis.readInt();
                String mensaje = dis.readUTF();
                
                switch(codigo) {
                    case 1:
                        ventana.nuevaPersona(mensaje);
                        break;
                        
                    case 2:
                        ventana.mensajeRecibido(mensaje);
                        
                    case 3:
                        try {
                            int nPos = Integer.parseInt(mensaje);
                            ventana.borrarPersona(nPos);
                        } catch (Exception e) {
                        }
                        
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "No se encuentra el puerto abierto para iniciar el chat");
            ventana.desconectar();
        }
    }
}
