/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sylar
 */
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByClientId", query = "SELECT c FROM Client c WHERE c.clientId = :clientId"),
    @NamedQuery(name = "Client.findByClientNames", query = "SELECT c FROM Client c WHERE c.clientNames = :clientNames"),
    @NamedQuery(name = "Client.findByClientLastnames", query = "SELECT c FROM Client c WHERE c.clientLastnames = :clientLastnames"),
    @NamedQuery(name = "Client.findByClientUnr", query = "SELECT c FROM Client c WHERE c.clientUnr = :clientUnr"),
    @NamedQuery(name = "Client.findByClientEmail", query = "SELECT c FROM Client c WHERE c.clientEmail = :clientEmail"),
    @NamedQuery(name = "Client.findByClientPhone", query = "SELECT c FROM Client c WHERE c.clientPhone = :clientPhone")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLIENT_ID")
    private Integer clientId;
    @Basic(optional = false)
    @Column(name = "CLIENT_NAMES")
    private String clientNames;
    @Basic(optional = false)
    @Column(name = "CLIENT_LASTNAMES")
    private String clientLastnames;
    @Column(name = "CLIENT_UNR")
    private String clientUnr;
    @Basic(optional = false)
    @Column(name = "CLIENT_EMAIL")
    private String clientEmail;
    @Column(name = "CLIENT_PHONE")
    private String clientPhone;
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")
    @ManyToOne(optional = false)
    private Company companyId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private User userId;

    public Client() {
    }

    public Client(Integer clientId) {
        this.clientId = clientId;
    }

    public Client(Integer clientId, String clientNames, String clientLastnames, String clientEmail) {
        this.clientId = clientId;
        this.clientNames = clientNames;
        this.clientLastnames = clientLastnames;
        this.clientEmail = clientEmail;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientNames() {
        return clientNames;
    }

    public void setClientNames(String clientNames) {
        this.clientNames = clientNames;
    }

    public String getClientLastnames() {
        return clientLastnames;
    }

    public void setClientLastnames(String clientLastnames) {
        this.clientLastnames = clientLastnames;
    }

    public String getClientUnr() {
        return clientUnr;
    }

    public void setClientUnr(String clientUnr) {
        this.clientUnr = clientUnr;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ clientId=" + clientId + " ]";
    }
    
}
