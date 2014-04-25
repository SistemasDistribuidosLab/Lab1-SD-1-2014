package entities;

import entities.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(Country.class)
public class Country_ { 

    public static volatile CollectionAttribute<Country, City> cityCollection;
    public static volatile SingularAttribute<Country, String> countryName;
    public static volatile SingularAttribute<Country, Integer> countryId;

}