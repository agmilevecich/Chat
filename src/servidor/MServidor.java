
package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MServidor extends Thread {
    
    private JFrame ventana;
    private int puerto;
    private ServerSocket ss;
    private Socket s;
    
    public MServidor(JFrame ventana, int puerto) {
        this.ventana = ventana;
        this.puerto = puerto;
    }
    
    public void run() {
        try {
            ss = new ServerSocket(puerto);
            while(true) {
                s = ss.accept();
                MSGestionConexiones.getInstancia().conectaNuevo(new MSConexion(s));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Error al abrir el puerto");
        } finally {
            cerrar();
        }
    }

    private void cerrar() {
        try {
            s.close();
            ss.close();
        } catch (Exception e) {
        }
    }

    public void cerrarPuerto() {
        cerrar();
    }
}
