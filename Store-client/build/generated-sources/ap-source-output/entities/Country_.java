package entities;

import entities.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-07T18:44:19")
@StaticMetamodel(Country.class)
public class Country_ { 

    public static volatile SingularAttribute<Country, String> countryName;
    public static volatile ListAttribute<Country, City> cityList;
    public static volatile SingularAttribute<Country, Integer> countryId;

}