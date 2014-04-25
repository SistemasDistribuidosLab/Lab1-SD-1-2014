package entities;

import entities.ClientPK;
import entities.Company;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> clientNames;
    public static volatile SingularAttribute<Client, Company> companyId;
    public static volatile SingularAttribute<Client, ClientPK> clientPK;
    public static volatile SingularAttribute<Client, String> clientEmail;
    public static volatile CollectionAttribute<Client, User> userCollection;
    public static volatile SingularAttribute<Client, User> user;
    public static volatile SingularAttribute<Client, String> clientPhone;
    public static volatile SingularAttribute<Client, String> clientLastnames;
    public static volatile SingularAttribute<Client, String> clientUnr;

}