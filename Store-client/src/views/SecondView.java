package views;
import com.sun.management.VMOption;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.omg.CORBA.Environment;
import storeclient.ConnectionRMI;
import storeclient.Delay;

public class SecondView extends javax.swing.JFrame {

    public void seDesconecto(String message) throws BadLocationException {
        Conectados.remove(message);
        ConectadosList.setModel(ObtenerDefaultListModel());
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            System.out.println(a.NombreReceptor + " y " + message);
            if(a.NombreReceptor.equals(message)){
                //Chats.remove(i);
                //a.setVisible(false);
                //a.setVisible(false);
                a.activarBotonEnviar(false);
                a.activarTextField(false);
                //a.activarTextArea(false);
                a.MostrarMensaje = false;
                
                a.appendText("se desconecto...\n", Color.RED);
                
                i = 0;
            }
        }
    }
    
    ArrayList Conectados = new ArrayList();
    private ConnectionRMI connection = new ConnectionRMI();
    private String MiNombre;
    ArrayList Chats = new ArrayList();
    
    public SecondView(String MiNombre) {
        this.MiNombre = MiNombre;
        initComponents();
        this.WarningLabel.setVisible(false);
        System.out.println("Mi nombre es: " + MiNombre);
        UsernameLabel.setText("Bienvenido "+MiNombre);
        ConectadosList.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if(ConectadosList.getSelectedValuesList().size() > 0)
                {
                    IniciarChatButton.setEnabled(true);
                }
                else
                {
                    IniciarChatButton.setEnabled(false);
                }
            }
        });
        IniciarChatButton.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WarningLabel = new javax.swing.JLabel();
        IniciarChatButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ConectadosList = new javax.swing.JList();
        UsernameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ejemplo Cliente RMI");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        WarningLabel.setForeground(new java.awt.Color(51, 51, 255));

        IniciarChatButton.setText("Iniciar Chat");
        IniciarChatButton.setEnabled(false);
        IniciarChatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirChat(evt);
            }
        });
        IniciarChatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarChatButtonActionPerformed(evt);
            }
        });

        ConectadosList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ConectadosListFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ConectadosListFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(ConectadosList);

        UsernameLabel.setText("Bienvenido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(UsernameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(WarningLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(IniciarChatButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UsernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(WarningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IniciarChatButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AbrirChat(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirChat
    if(!ConectadosList.isSelectionEmpty()){
        Chat Chat;
        try {
            String receptor = this.ConectadosList.getSelectedValue().toString();
            Boolean existeChat = false;
            int index_chat = -1;
            for (int i = 0; i < Chats.size(); i++) {
                Chat a = (Chat) Chats.get(i);
                if(receptor.equals(a.NombreReceptor)){
                    existeChat = true;
                    index_chat = i;
                }
            }
            if(!existeChat){
                Chat = new Chat( MiNombre, ConectadosList.getSelectedValue().toString(), true);
                int centro_x_padre = this.getLocation().x + this.getWidth() / 2;
                int centro_y_padre = this.getLocation().y + this.getHeight() / 2;
                Chat.setLocation( centro_x_padre, centro_y_padre);
                Chat.setVisible(true);
                Chat.activarBotonEnviar(true);
                Chat.activarTextField(true);
                //Chat.activarTextArea(true);
                Chats.add(Chat);
            }else{
                Chat activo = (Chat) Chats.get(index_chat);
                activo.setVisible(true);
                activo.activarBotonEnviar(true);
                activo.activarTextField(true);
                //activo.activarTextArea(true);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SecondView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else{
        this.IniciarChatButton.setEnabled(false);
    }
    //this.setVisible(false);
    }//GEN-LAST:event_AbrirChat

    private void IniciarChatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarChatButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IniciarChatButtonActionPerformed

    private void ConectadosListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConectadosListFocusLost

    }//GEN-LAST:event_ConectadosListFocusLost

    private void ConectadosListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ConectadosListFocusGained

    }//GEN-LAST:event_ConectadosListFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (connection.beginRegistry()){
                connection.getServer().meDesconecte(MiNombre);
                System.out.println("Me desconecte");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SecondView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing
    
    public void warning(String message){
        this.WarningLabel.setText(message);
        this.WarningLabel.setVisible(true);
        //Thread thread = new Thread((Runnable) new Delay(this));
        //thread.start();
    }
    
    public DefaultListModel ObtenerDefaultListModel(){
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < Conectados.size(); i++) {
            System.out.println(Conectados.get(i).toString());
            model.addElement(Conectados.get(i));
        }
        return model;
    }
    
    public void AgregarConectado(String nombre) throws RemoteException{
        if(!Conectados.contains(nombre))
            Conectados.add(nombre);
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            if(nombre.equals(a.NombreReceptor)){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("\t!!!!!!");
                Chat ChatReceptor = (Chat) Chats.get(i);
                a.activarBotonEnviar(true);
                a.activarTextField(true);
                //a.activarTextArea(true);
                if (connection.beginRegistry()){
                        //System.out.println("enviarMensaje( " + MiNombre + ", "+ nombre_receptor + ", Hola Mundo 2 !!!" );
                        connection.getServer().enviarMensaje(MiNombre, nombre, "");
                }
            }
        }
        ConectadosList.setModel(ObtenerDefaultListModel());
        //this.IniciarChatButton.setEnabled(true);
    }
    
    public void hideLabel(){
        this.WarningLabel.setVisible(false);
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList ConectadosList;
    private javax.swing.JButton IniciarChatButton;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel WarningLabel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void AbrirChat(String emisor, String receptor, String mensaje) throws RemoteException, BadLocationException {
        Boolean estaAbierto = false;
        System.out.println("AbrirChat("+emisor+", "+receptor+", "+mensaje+");");
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            System.out.println("Comparando: " + emisor + ", "+receptor+ ", "+a.MiNombre+ ", "+a.NombreReceptor);
            if(emisor.equals(a.MiNombre) && receptor.equals(a.NombreReceptor)){
                if(a.PrimerMensaje){
                    a.PrimerMensaje = false;
                }else{
                    a.appendText("se conecto...\n", Color.GREEN);
                }
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("\t!!!!!!");
                estaAbierto = true;
                Chat ChatReceptor = (Chat) Chats.get(i);
                a.activarBotonEnviar(true);
                a.activarTextField(true);
                //a.activarTextArea(true);
                //ChatReceptor.setVisible(true);
                ChatReceptor.AgregarMensaje(receptor, mensaje);
            }
        }
        if(!estaAbierto){
            System.out.println("neu Chat("+emisor+", "+receptor+", false)");
            Chat Nuevo = new Chat(emisor, receptor, false);
            //Nuevo.setVisible(true);
            //Nuevo.AgregarMensaje(mensaje);
            Chats.add(Nuevo);
        }
    }

}
