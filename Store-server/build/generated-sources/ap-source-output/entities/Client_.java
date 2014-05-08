package entities;

import entities.Company;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-07T23:45:03")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> clientNames;
    public static volatile SingularAttribute<Client, Company> companyId;
    public static volatile SingularAttribute<Client, Integer> clientId;
    public static volatile SingularAttribute<Client, String> clientEmail;
    public static volatile SingularAttribute<Client, User> userId;
    public static volatile SingularAttribute<Client, String> clientPhone;
    public static volatile SingularAttribute<Client, String> clientLastnames;
    public static volatile SingularAttribute<Client, String> clientUnr;

}