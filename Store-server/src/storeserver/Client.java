/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package storeserver;

import interfaces.ClientInterface;


/**
 *
 * @author sylar
 */
public class Client implements Runnable {

    private String clientName;
    private String clientIp;
    private ClientInterface client;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String NombreCliente) {
        this.clientName = NombreCliente;
    }

    public ClientInterface getClient() {
        return client;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    public void run() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

}
