package servidor;

import java.util.ArrayList;


public class MSGestionConexiones {

    private static MSGestionConexiones singleton = new MSGestionConexiones();
    private ArrayList<MSConexion> conexiones = new ArrayList<MSConexion>();
    
    public static MSGestionConexiones getInstancia() {
        return singleton;
    }
    
    public void conectaNuevo(MSConexion nuevo) {
        for(MSConexion ms:conexiones) {
            nuevo.enviarTrama(1, ms.getNick());
        }
        conexiones.add(nuevo);
    }
    
    public void enviarTrama(int codigo, String mensaje) {
        for(MSConexion ms:conexiones) {
            ms.enviarTrama(codigo, mensaje);
        }
    }
    
    public void desconectar(MSConexion eliminar) {
        int nPos = - 1;
        for (int i = 0; i < conexiones.size(); i++) {
            if(conexiones.get(i) == eliminar) {
                nPos = i;
            }
        }
        
        if(nPos != - 1){
            for (int i = 0; i < conexiones.size(); i++) {
                conexiones.get(i).enviarTrama(3, ""+nPos);
            }
            conexiones.remove(nPos);
        }
    }
}
