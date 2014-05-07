package entities;

import entities.Address;
import entities.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-07T18:43:52")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Integer> itemId;
    public static volatile SingularAttribute<Item, Product> productId;
    public static volatile SingularAttribute<Item, String> itemCode;
    public static volatile SingularAttribute<Item, String> itemState;
    public static volatile SingularAttribute<Item, Address> addressId;

}