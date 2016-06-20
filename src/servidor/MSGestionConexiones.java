package servidor;

import java.util.ArrayList;


public class MSGestionConexiones {

    private static MSGestionConexiones singleton = new MSGestionConexiones();
    private ArrayList<MSConecion> conexiones = new ArrayList<MSConecion>();
    
    public static MSGestionConexiones getInstancia() {
        return singleton;
    }
    
    public void conectaNuevo(MSConecion nuevo) {
        for(MSConecion ms:conexiones) {
            nuevo.enviarTrama(1, ms.getNick());
        }
        conexiones.add(nuevo);
    }
    
    public void enviarTrama(int codigo, String mensaje) {
        for(MSConecion ms:conexiones) {
            ms.enviarTrama(codigo, mensaje);
        }
    }
    
    public void desconectar(MSConecion eliminar) {
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
