package entities;

import entities.City;
import entities.Company;
import entities.Item;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-07T18:43:52")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, Company> companyId;
    public static volatile SingularAttribute<Address, String> addressDetail;
    public static volatile SingularAttribute<Address, String> addressType;
    public static volatile SingularAttribute<Address, String> addressName;
    public static volatile ListAttribute<Address, Item> itemList;
    public static volatile SingularAttribute<Address, City> cityId;
    public static volatile SingularAttribute<Address, User> userId;
    public static volatile SingularAttribute<Address, Integer> addressId;

}