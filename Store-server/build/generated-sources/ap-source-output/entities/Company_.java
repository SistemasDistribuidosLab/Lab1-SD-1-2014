package entities;

import entities.Address;
import entities.Catalog;
import entities.Client;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, String> companyCode;
    public static volatile CollectionAttribute<Company, Address> addressCollection;
    public static volatile SingularAttribute<Company, Integer> companyId;
    public static volatile CollectionAttribute<Company, Catalog> catalogCollection;
    public static volatile SingularAttribute<Company, String> companyName;
    public static volatile SingularAttribute<Company, String> companyDescription;
    public static volatile CollectionAttribute<Company, User> userCollection;
    public static volatile CollectionAttribute<Company, Client> clientCollection;

}