/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import interfaces.ClientInterface;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import storeclient.ConnectionRMI;

/**
 *
 * @author ABS Bodega
 */
public class Chat extends javax.swing.JFrame {
    public Boolean PrimerMensaje = true;
    public Boolean MostrarMensaje = true;
    private ConnectionRMI connection = new ConnectionRMI();
    public String MiNombre;
    public String NombreReceptor;
    
    public void activarBotonEnviar(Boolean activar){
        EnviarButton.setEnabled(activar);
    }
    
    /**
     * Creates new form Chat
     */
    public Chat(String MiNombre, String nombre_receptor, Boolean EnviarMensaje) throws RemoteException {
        initComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.out.println("Me elimine :c");
                setVisible(false);
            }
        });
        this.MiNombre = MiNombre;
        this.NombreReceptor = nombre_receptor;
        ChatConLabel.setText("soy " + this.MiNombre  + ": Chat con " + nombre_receptor);
        if (connection.beginRegistry()){
            if(EnviarMensaje){
                //System.out.println("enviarMensaje( " + MiNombre + ", "+ nombre_receptor + ", Hola Mundo 2 !!!" );
                connection.getServer().enviarMensaje(MiNombre, nombre_receptor, this.MensajeTextField.getText());
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "No se pudo Conectar", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        MensajeTextField = new javax.swing.JTextField();
        EnviarButton = new javax.swing.JButton();
        ChatConLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MensajesTextArea = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        MensajeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MensajeTextFieldKeyPressed(evt);
            }
        });

        EnviarButton.setText("Enviar");
        EnviarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarButtonActionPerformed(evt);
            }
        });

        ChatConLabel.setText("Chat con");

        MensajesTextArea.setEditable(false);
        MensajesTextArea.setFocusable(false);
        jScrollPane2.setViewportView(MensajesTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MensajeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EnviarButton))
                    .addComponent(ChatConLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChatConLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MensajeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EnviarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnviarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarButtonActionPerformed
        try {
            if (connection.beginRegistry()){
                //System.out.println("enviarMensaje( " + MiNombre + ", "+NombreReceptor+", Hola Mundo 1");
                String mensaje = this.MensajeTextField.getText();
                connection.getServer().enviarMensaje(MiNombre, NombreReceptor, mensaje);
                StyledDocument document = MensajesTextArea.getStyledDocument();
                Style style = MensajesTextArea.addStyle("Prueba", null);
                StyleConstants.setForeground(style, Color.DARK_GRAY);
                document.insertString(document.getLength(), MiNombre + ": " + mensaje+"\n", style);
                this.MensajeTextField.setText("");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se pudo Conectar", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EnviarButtonActionPerformed

    private void MensajeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MensajeTextFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            EnviarButtonActionPerformed(null);
        }
    }//GEN-LAST:event_MensajeTextFieldKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChatConLabel;
    private javax.swing.JButton EnviarButton;
    private javax.swing.JTextField MensajeTextField;
    private javax.swing.JTextPane MensajesTextArea;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    void AgregarMensaje(String emisor, String mensaje) throws BadLocationException {
        System.out.println("\t\t================ Mostrar Mensaje: "+MostrarMensaje);
        if(MostrarMensaje){
        setVisible(true);
        //MensajesTextArea.setText(MensajesTextArea.getText()+"\n"+mensaje);
        //MensajesTextAreaa.append(emisor + ": " + mensaje+"\n");
        //MensajesTextArea.
        StyledDocument document = MensajesTextArea.getStyledDocument();
        Style style = MensajesTextArea.addStyle("Prueba", null);
        StyleConstants.setForeground(style, Color.DARK_GRAY);
        document.insertString(document.getLength(), emisor + ": " + mensaje+"\n", style);
        }else{
            MostrarMensaje = true;
        }
        
        //MensajesTextArea.setText("Texto !!!!!!");
        //System.out.println("Anadir el mensaje D: !!!!");
    }

    void appendText(String mensaje, Color color) throws BadLocationException {
        StyledDocument document = MensajesTextArea.getStyledDocument();
        Style style = MensajesTextArea.addStyle("Prueba", null);
        StyleConstants.setForeground(style, color);
        document.insertString(document.getLength(), mensaje, style);
    }

    void activarTextField(boolean b) {
        MensajeTextField.setEnabled(b);
    }

    void activarTextArea(boolean b) {
        MensajesTextArea.setEnabled(b);
    }
}
