package entities;

import entities.Category;
import entities.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> productCode;
    public static volatile SingularAttribute<Product, Integer> productId;
    public static volatile CollectionAttribute<Product, Item> itemCollection;
    public static volatile SingularAttribute<Product, Integer> productStock;
    public static volatile SingularAttribute<Product, String> productName;
    public static volatile SingularAttribute<Product, String> productDescription;
    public static volatile SingularAttribute<Product, Category> categoryId;

}