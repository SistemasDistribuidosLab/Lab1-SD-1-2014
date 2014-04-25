package entities;

import entities.Address;
import entities.Country;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-04-22T01:46:59")
@StaticMetamodel(City.class)
public class City_ { 

    public static volatile SingularAttribute<City, String> cityType;
    public static volatile CollectionAttribute<City, Address> addressCollection;
    public static volatile SingularAttribute<City, String> cityName;
    public static volatile SingularAttribute<City, Integer> cityId;
    public static volatile SingularAttribute<City, Country> countryId;

}