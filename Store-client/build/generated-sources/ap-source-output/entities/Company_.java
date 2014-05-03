package entities;

import entities.Address;
import entities.Catalog;
import entities.Client;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T13:11:30")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, String> companyCode;
    public static volatile SingularAttribute<Company, Integer> companyId;
    public static volatile ListAttribute<Company, User> userList;
    public static volatile ListAttribute<Company, Catalog> catalogList;
    public static volatile ListAttribute<Company, Address> addressList;
    public static volatile SingularAttribute<Company, String> companyName;
    public static volatile SingularAttribute<Company, String> companyDescription;
    public static volatile ListAttribute<Company, Client> clientList;

}