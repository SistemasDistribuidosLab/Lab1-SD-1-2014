package storeserver;
import implementations.InterfaceServerImpl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerView extends javax.swing.JFrame {
    //Se instancia la clase conexion para poder iniciar una,
    //o eventualmente detenerla
    private RMIConnection connection = new RMIConnection();

    public ServerView() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opciones = new javax.swing.JPanel();
        ButtonIniciar = new javax.swing.JButton();
        ButtonDetener = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor RMI");

        opciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        ButtonIniciar.setText("Iniciar Sever");
        ButtonIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iniciarServer(evt);
            }
        });

        ButtonDetener.setText("Detener Server");
        ButtonDetener.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detenerServer(evt);
            }
        });

        javax.swing.GroupLayout opcionesLayout = new javax.swing.GroupLayout(opciones);
        opciones.setLayout(opcionesLayout);
        opcionesLayout.setHorizontalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(ButtonDetener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        opcionesLayout.setVerticalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonIniciar)
                .addGap(18, 18, 18)
                .addComponent(ButtonDetener)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarServer(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iniciarServer
        Registry registry;
        try {
            registry = connection.getRegistry();
            //Se instancia el objeto que implementa la interfaz del Servidor
            //Ahora hay que hacerlo remoto, para ello se registra en el Registry
            //con el método "rebind" que lo registra con un nombre para poder ser visto en ese espacio
            //en este caso se le dio el nombre "Implementacion".
            InterfaceServerImpl object = new InterfaceServerImpl();
            registry.rebind("Implementation", (Remote) object);
            this.ButtonIniciar.setEnabled(false);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_iniciarServer

    private void detenerServer(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detenerServer
        try {
            connection.stop();
            this.ButtonIniciar.setEnabled(true);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_detenerServer
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDetener;
    private javax.swing.JButton ButtonIniciar;
    private javax.swing.JPanel opciones;
    // End of variables declaration//GEN-END:variables

}
