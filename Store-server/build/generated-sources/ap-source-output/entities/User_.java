package entities;

import entities.Address;
import entities.Client;
import entities.Company;
import entities.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Address> addressCollection;
    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, Company> companyId;
    public static volatile CollectionAttribute<User, Role> roleCollection;
    public static volatile SingularAttribute<User, Client> client;
    public static volatile CollectionAttribute<User, Client> clientCollection;
    public static volatile SingularAttribute<User, String> userEmail;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, Integer> userId;

}