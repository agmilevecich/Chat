package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class MSConexion extends Thread {
    
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String nick;
    
    public MSConexion(Socket s) {
        try {
            this.s = s;
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            start();
        } catch (Exception e) {
        }
    }
    
    public String getNick() {
        return nick;
    }
    
    public void enviarTrama(int codigo, String mensaje) {
        try {
            dos.writeInt(codigo);
            dos.writeUTF(mensaje);
        } catch (Exception e) {
        }
    }
    
    public void run() {
        try {
            while(true) {
                int codigo = dis.readInt();
                String mensaje = dis.readUTF();
                
                switch(codigo){
                    
                    case 1:
                        nick = mensaje ;
                        MSGestionConexiones.getInstancia().enviarTrama(codigo, mensaje);
                        break;
                        
                    case 2:
                        mensaje = "<" + nick + "> - " + mensaje;
                        MSGestionConexiones.getInstancia().enviarTrama(codigo, mensaje);
                        break;
                        
                    case 3:
                        MSGestionConexiones.getInstancia().desconectar(this);
                        break;
                        
                }
            }
        } catch (Exception e) {
        }
    }
}
