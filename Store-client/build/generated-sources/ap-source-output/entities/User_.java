package entities;

import entities.Address;
import entities.Client;
import entities.Company;
import entities.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-01T02:13:58")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, Company> companyId;
    public static volatile ListAttribute<User, Address> addressList;
    public static volatile SingularAttribute<User, Role> roleId;
    public static volatile SingularAttribute<User, String> userEmail;
    public static volatile ListAttribute<User, Client> clientList;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, Integer> userId;

}