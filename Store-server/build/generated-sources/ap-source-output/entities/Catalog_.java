package entities;

import entities.Category;
import entities.Company;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(Catalog.class)
public class Catalog_ { 

    public static volatile SingularAttribute<Catalog, String> catalogName;
    public static volatile SingularAttribute<Catalog, Company> companyId;
    public static volatile SingularAttribute<Catalog, Integer> catalogId;
    public static volatile CollectionAttribute<Catalog, Category> categoryCollection;
    public static volatile SingularAttribute<Catalog, String> catalogDescription;

}