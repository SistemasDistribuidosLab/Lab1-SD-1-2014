package entities;

import entities.Address;
import entities.Country;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-02T23:38:21")
@StaticMetamodel(City.class)
public class City_ { 

    public static volatile SingularAttribute<City, String> cityType;
    public static volatile SingularAttribute<City, String> cityName;
    public static volatile ListAttribute<City, Address> addressList;
    public static volatile SingularAttribute<City, Integer> cityId;
    public static volatile SingularAttribute<City, Country> countryId;

}