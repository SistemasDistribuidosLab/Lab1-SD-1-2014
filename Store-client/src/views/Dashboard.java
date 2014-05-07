/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.*;
import java.awt.Color;
import java.awt.Component;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import otherclasses.ComboItem;
import storeclient.ConnectionRMI;

/**
 *
 * @author sylar
 */
public class Dashboard extends javax.swing.JFrame {

    ArrayList Conectados = new ArrayList();
    private ConnectionRMI connection = new ConnectionRMI();
    private String MiNombre;
    ArrayList Chats = new ArrayList();

    public Dashboard(ConnectionRMI conn, String MiNombre) {
        initComponents();
        this.MiNombre = MiNombre;
        this.WarningLabel.setVisible(false);
        System.out.println("Mi nombre es: " + MiNombre);
        UsernameLabel.setText("Bienvenido " + MiNombre);
        ListConectados.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (ListConectados.getSelectedValuesList().size() > 0) {
                    IniciarChatButton.setEnabled(true);
                } else {
                    IniciarChatButton.setEnabled(false);
                }
            }
        });
        IniciarChatButton.setEnabled(false);
        this.connection = conn;
        /*  Se establecen los items del ComboBox de búsqueda de usuarios    */
        this.jComboBoxUserSearchType.addItem(new ComboItem("Nombre de usuario", 1));
        this.jComboBoxUserSearchType.addItem(new ComboItem("Email de usuario", 2));
    }

    public void warning(String message) {
        //this.WarningLabel.setText("Se conecto: " + message);
        this.WarningLabel.setVisible(false);
            //Thread thread = new Thread((Runnable) new Delay(this));
        //thread.start();
    }

    public void AgregarConectado(String nombre) throws RemoteException {
        if (!Conectados.contains(nombre)) {
            Conectados.add(nombre);
        }
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            if (nombre.equals(a.NombreReceptor)) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("\t!!!!!!");
                Chat ChatReceptor = (Chat) Chats.get(i);
                a.activarBotonEnviar(true);
                a.activarTextField(true);
                //a.activarTextArea(true);
                if (connection.beginRegistry()) {
                    //System.out.println("enviarMensaje( " + MiNombre + ", "+ nombre_receptor + ", Hola Mundo 2 !!!" );
                    connection.getServer().enviarMensaje(MiNombre, nombre, "");
                }
            }
        }
        ListConectados.setModel(ObtenerDefaultListModel());
        //this.IniciarChatButton.setEnabled(true);
    }

    private void AbrirChat(java.awt.event.MouseEvent evt) throws RemoteException {//GEN-FIRST:event_AbrirChat
        if (!ListConectados.isSelectionEmpty()) {
            Chat Chat;
            String receptor = this.ListConectados.getSelectedValue().toString();
            Boolean existeChat = false;
            int index_chat = -1;
            for (int i = 0; i < Chats.size(); i++) {
                Chat a = (Chat) Chats.get(i);
                if (receptor.equals(a.NombreReceptor)) {
                    existeChat = true;
                    index_chat = i;
                }
            }
            if (!existeChat) {
                Chat = new Chat(MiNombre, ListConectados.getSelectedValue().toString(), true);
                int centro_x_padre = this.getLocation().x + this.getWidth() / 2;
                int centro_y_padre = this.getLocation().y + this.getHeight() / 2;
                Chat.setLocation(centro_x_padre, centro_y_padre);
                Chat.setVisible(true);
                Chat.activarBotonEnviar(true);
                Chat.activarTextField(true);
                //Chat.activarTextArea(true);
                Chats.add(Chat);
            } else {
                Chat activo = (Chat) Chats.get(index_chat);
                activo.setVisible(true);
                activo.activarBotonEnviar(true);
                activo.activarTextField(true);
                //activo.activarTextArea(true);
            }
        } else {
            this.IniciarChatButton.setEnabled(false);
        }
        //this.setVisible(false);
    }//GEN-LAST:event_AbrirChat

    public DefaultListModel ObtenerDefaultListModel() {
        this.UsernameLabel.repaint();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < Conectados.size(); i++) {
            System.out.println("Lista: " + Conectados.get(i).toString());
            model.addElement(Conectados.get(i));
        }
        return model;
    }

    public void AbrirChat(String emisor, String receptor, String mensaje) throws RemoteException, BadLocationException {
        Boolean estaAbierto = false;
        System.out.println("AbrirChat(" + emisor + ", " + receptor + ", " + mensaje + ");");
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            System.out.println("Comparando: " + emisor + ", " + receptor + ", " + a.MiNombre + ", " + a.NombreReceptor);
            if (emisor.equals(a.MiNombre) && receptor.equals(a.NombreReceptor)) {
                if (a.PrimerMensaje) {
                    a.PrimerMensaje = false;
                } else {
                    //a.appendText("se conecto...\n", Color.GREEN);
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
        if (!estaAbierto) {
            System.out.println("neu Chat(" + emisor + ", " + receptor + ", false)");
            Chat Nuevo = new Chat(emisor, receptor, false);
            //Nuevo.setVisible(true);
            //Nuevo.AgregarMensaje(mensaje);
            Chats.add(Nuevo);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUserList = new javax.swing.JList();
        jTextFieldUserSearch = new javax.swing.JTextField();
        jComboBoxUserSearchType = new javax.swing.JComboBox();
        jButtonUserSearch = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jTextFieldUserEmail = new javax.swing.JTextField();
        jTextFieldPassword = new javax.swing.JTextField();
        jTextFieldConfirmPassword = new javax.swing.JTextField();
        jButtonUserCreate = new javax.swing.JButton();
        jComboBoxUserRole = new javax.swing.JComboBox();
        jLabelUserMandatoryFields = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelUserPasswordConsistency = new javax.swing.JLabel();
        jLabelUserSuccessCreate = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldRoleName = new javax.swing.JTextField();
        jTextFieldRoleDescription = new javax.swing.JTextField();
        jButtonRoleCreate = new javax.swing.JButton();
        jLabelRoleMandatoryFields = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelRoleSuccessCreate = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListConectados = new javax.swing.JList();
        UsernameLabel = new javax.swing.JLabel();
        IniciarChatButton = new javax.swing.JButton();
        WarningLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jListUserList);
        jListUserList.getAccessibleContext().setAccessibleName("jListUser");

        jTextFieldUserSearch.setText("Buscar usuarios");
        jTextFieldUserSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldUserSearchMouseClicked(evt);
            }
        });
        jTextFieldUserSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUserSearchActionPerformed(evt);
            }
        });

        jButtonUserSearch.setText("Buscar");
        jButtonUserSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonUserSearchMouseClicked(evt);
            }
        });

        jButton2.setText("Editar");

        jButton3.setText("Eliminar");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTextFieldUserSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxUserSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUserSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUserSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxUserSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUserSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextFieldUserSearch.getAccessibleContext().setAccessibleName("jTextFieldSearchUser");
        jComboBoxUserSearchType.getAccessibleContext().setAccessibleName("jComboBoxUserSearchType");
        jButtonUserSearch.getAccessibleContext().setAccessibleName("jButtonSearchUser");
        jButton2.getAccessibleContext().setAccessibleName("jButtonEditUser");
        jButton3.getAccessibleContext().setAccessibleName("jButtonUserDestroy");

        jTabbedPane2.addTab("Listar Usuarios", jPanel8);
        jPanel8.getAccessibleContext().setAccessibleName("tabListUsers");

        jLabel1.setText("Nombre de usuario:");

        jLabel2.setText("Rol del usuario:");

        jLabel3.setText("Correo electrónico*:");

        jLabel4.setText("Contraseña*:");

        jLabel5.setText("Repetir Contraseña*:");

        jButtonUserCreate.setText("Registrar");
        jButtonUserCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonUserCreateMouseClicked(evt);
            }
        });

        jLabelUserMandatoryFields.setForeground(new java.awt.Color(255, 0, 0));
        jLabelUserMandatoryFields.setText("Debe completar los campos obligatorios.");

        jLabel7.setText("(*) Campos obligatorios.");

        jLabelUserPasswordConsistency.setForeground(new java.awt.Color(255, 0, 0));
        jLabelUserPasswordConsistency.setText("Ambas contraseñas deben ser iguales.");

        jLabelUserSuccessCreate.setForeground(new java.awt.Color(18, 221, 1));
        jLabelUserSuccessCreate.setText("¡El usuario se ha creado exitosamente!");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                            .addComponent(jButtonUserCreate))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldUserName)
                                .addComponent(jTextFieldPassword)
                                .addComponent(jTextFieldUserEmail)
                                .addComponent(jTextFieldConfirmPassword)
                                .addComponent(jComboBoxUserRole, 0, 246, Short.MAX_VALUE))))
                    .addComponent(jLabelUserMandatoryFields)
                    .addComponent(jLabelUserPasswordConsistency)
                    .addComponent(jLabelUserSuccessCreate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUserCreate)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jLabelUserMandatoryFields)
                .addGap(18, 18, 18)
                .addComponent(jLabelUserPasswordConsistency)
                .addGap(18, 18, 18)
                .addComponent(jLabelUserSuccessCreate)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Nuevo Usuario", jPanel9);
        jPanel9.getAccessibleContext().setAccessibleName("tabNewUser");
        jPanel9.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Listar Roles", jPanel7);

        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel6.setText("Nombre de Rol*:");

        jLabel10.setText("Descripción del Rol:");

        jButtonRoleCreate.setText("Registrar");
        jButtonRoleCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRoleCreateMouseClicked(evt);
            }
        });

        jLabelRoleMandatoryFields.setForeground(new java.awt.Color(255, 0, 0));
        jLabelRoleMandatoryFields.setText("Debe completar los campos obligatorios.");

        jLabel12.setText("(*) Campos obligatorios.");

        jLabelRoleSuccessCreate.setForeground(new java.awt.Color(18, 221, 1));
        jLabelRoleSuccessCreate.setText("¡El rol se ha creado exitosamente!");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                            .addComponent(jButtonRoleCreate))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel6))
                            .addGap(28, 28, 28)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldRoleName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .addComponent(jTextFieldRoleDescription))))
                    .addComponent(jLabelRoleMandatoryFields)
                    .addComponent(jLabelRoleSuccessCreate))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRoleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRoleDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jButtonRoleCreate))
                .addGap(18, 18, 18)
                .addComponent(jLabelRoleMandatoryFields)
                .addGap(18, 18, 18)
                .addComponent(jLabelRoleSuccessCreate)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Nuevo Rol", jPanel10);

        jScrollPane2.setViewportView(ListConectados);

        UsernameLabel.setText("Bienvenido");

        IniciarChatButton.setText("Iniciar Chat");
        IniciarChatButton.setEnabled(false);
        IniciarChatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IniciarChatButtonMouseClicked(evt);
            }
        });

        WarningLabel.setText("Se conecto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(UsernameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(WarningLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                .addComponent(IniciarChatButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(UsernameLabel)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IniciarChatButton)
                    .addComponent(WarningLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("tabPanelUser");

        jTabbedPane1.addTab("Usuarios", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Proveedores", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Catálogos", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Categorías", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Productos", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Clientes", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Usuarios");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldUserSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUserSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUserSearchActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        // Nuevo Usuario Click Tab
        /*  Se establecen los items del jComboBoxUserRole   */
        List<Role> roleList = null;
        try {
            roleList = connection.getServer().getRoleList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!roleList.isEmpty()) {
            for (int i = 0; i < roleList.size(); i++) {
                this.jComboBoxUserRole.addItem(new ComboItem(
                        roleList.get(i).getRoleName(),
                        roleList.get(i).getRoleId()));
            }
        }

        /*  Mandatory Fields    */
        this.jLabelRoleMandatoryFields.setVisible(false);
        this.jLabelUserMandatoryFields.setVisible(false);
        /*  Password Consisntency   */
        this.jLabelUserPasswordConsistency.setVisible(false);
        /*  Success Creating   */
        this.jLabelRoleSuccessCreate.setVisible(false);
        this.jLabelUserSuccessCreate.setVisible(false);
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void jButtonUserCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUserCreateMouseClicked
        String userName = this.jTextFieldUserName.getText();
        String userEmail = this.jTextFieldUserEmail.getText();
        ComboItem userRoleComboItem = (ComboItem) this.jComboBoxUserRole.getSelectedItem();
        String userPasswordOne = this.jTextFieldPassword.getText();
        String userPasswordTwo = this.jTextFieldConfirmPassword.getText();
        //Validación Mandatory Fields
        Boolean userCreable = true;
        if (userEmail.isEmpty() || userPasswordOne.isEmpty() && userPasswordTwo.isEmpty()) {
            this.jLabelUserMandatoryFields.setVisible(true);
            userCreable = false;
        }
        //Validación comparación de Paswords 
        if (!userPasswordOne.contentEquals(userPasswordTwo)) {
            this.jLabelUserPasswordConsistency.setVisible(true);
            userCreable = false;
        }
        //Si el usuario es creable
        if (userCreable) {
            this.jLabelUserMandatoryFields.setVisible(false);
            this.jLabelUserPasswordConsistency.setVisible(false);
            /*  Pre-Persistence */
            User user = new User();
            user.setUserName(userName);
            user.setUserEmail(userEmail);
            user.setUserPassword(userPasswordOne);
            Role role = null;
            try {
                role = connection.getServer().findRole(userRoleComboItem.getValue());
            } catch (RemoteException ex) {
                ex.printStackTrace();
                //Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*  Persistence */
            try {
                connection.getServer().createUser(user, role);
            } catch (Exception ex) {
                ex.printStackTrace();
                //Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*  Success Message */
            this.jLabelUserSuccessCreate.setVisible(true);
            /*  Cleaning Fields */
            this.jTextFieldUserName.setText("");
            this.jTextFieldUserEmail.setText("");
            this.jTextFieldPassword.setText("");
            this.jTextFieldConfirmPassword.setText("");

        }
    }//GEN-LAST:event_jButtonUserCreateMouseClicked

    private void jButtonRoleCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRoleCreateMouseClicked
        // TODO add your handling code here:
        String roleName = this.jTextFieldRoleName.getText();
        String roleDescription = this.jTextFieldRoleDescription.getText();
        //Validación Mandatory Fields
        Boolean roleCreable = true;
        if (roleName.isEmpty()) {
            this.jLabelRoleMandatoryFields.setVisible(true);
            roleCreable = false;
        }
        //Si el usuario es creable
        if (roleCreable) {
            this.jLabelRoleMandatoryFields.setVisible(false);
            this.jLabelRoleSuccessCreate.setVisible(false);
            /*  Pre-Persistence */
            Role role = new Role();
            role.setRoleName(roleName);
            role.setRoleDescription(roleDescription);
            /*  Persistence */
            try {
                connection.getServer().createRole(role);
            } catch (Exception ex) {
                ex.printStackTrace();
                //Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*  Success Message */
            this.jLabelRoleSuccessCreate.setVisible(true);
            /*  Cleaning Fields */
            this.jTextFieldRoleName.setText("");
            this.jTextFieldRoleDescription.setText("");
        }
    }//GEN-LAST:event_jButtonRoleCreateMouseClicked

    public void seDesconecto(String message) throws BadLocationException {
        Conectados.remove(message);
        ListConectados.setModel(ObtenerDefaultListModel());
        for (int i = 0; i < Chats.size(); i++) {
            Chat a = (Chat) Chats.get(i);
            System.out.println(a.NombreReceptor + " y " + message);
            if (a.NombreReceptor.equals(message)) {
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

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jTextFieldUserSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldUserSearchMouseClicked
        // TODO add your handling code here:
        this.jTextFieldUserSearch.setText("");
    }//GEN-LAST:event_jTextFieldUserSearchMouseClicked

    private void jButtonUserSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUserSearchMouseClicked
        // TODO add your handling code here:
        DefaultListModel model = new DefaultListModel();
        if (this.jTextFieldUserSearch.getText().contentEquals("")
                || this.jTextFieldUserSearch.getText().contentEquals("Buscar usuarios")) {
            List<User> userList = null;
            try {
                userList = connection.getServer().getUserList();
            } catch (RemoteException ex) {
                ex.printStackTrace();
                //Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!userList.isEmpty()) {
                for (int i = 0; i < userList.size(); i++) {
                    model.addElement(userList.get(i).getUserName() + ", " + userList.get(i).getUserEmail());

                    //model.addElement(new ComboItem(userList.get(i).getUserName(),
                    //                                userList.get(i).getUserId()));
                }
                this.jListUserList.setModel(model);
            }
        }
    }//GEN-LAST:event_jButtonUserSearchMouseClicked

    private void IniciarChatButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IniciarChatButtonMouseClicked
        try {
            AbrirChat(evt);
        } catch (RemoteException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_IniciarChatButtonMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (connection.beginRegistry()) {
                connection.getServer().meDesconecte(MiNombre);
                System.out.println("Me desconecte");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SecondView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IniciarChatButton;
    private javax.swing.JList ListConectados;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel WarningLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonRoleCreate;
    private javax.swing.JButton jButtonUserCreate;
    private javax.swing.JButton jButtonUserSearch;
    private javax.swing.JComboBox jComboBoxUserRole;
    private javax.swing.JComboBox jComboBoxUserSearchType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelRoleMandatoryFields;
    private javax.swing.JLabel jLabelRoleSuccessCreate;
    private javax.swing.JLabel jLabelUserMandatoryFields;
    private javax.swing.JLabel jLabelUserPasswordConsistency;
    private javax.swing.JLabel jLabelUserSuccessCreate;
    private javax.swing.JList jListUserList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextFieldConfirmPassword;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldRoleDescription;
    private javax.swing.JTextField jTextFieldRoleName;
    private javax.swing.JTextField jTextFieldUserEmail;
    private javax.swing.JTextField jTextFieldUserName;
    private javax.swing.JTextField jTextFieldUserSearch;
    // End of variables declaration//GEN-END:variables
}
