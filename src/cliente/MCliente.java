package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MCliente extends Thread {
    
    private VCliente vemtana;
    private String url;
    private int puerto;
    private String nick;
    private Socket s;
    
    public MCliente(VCliente ventana, String url, int puerto, String nick) {
        this.vemtana = ventana;
        this.url = url;
        this.puerto = puerto;
        this.nick = nick;
    }
    
    public void enviarTrama(int codigo, String mensaje) {
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
                        vemtana.nuevaPersona(mensaje);
                        break;
                }
            }
        } catch (Exception e) {
        }
    }
}
