package entities;

import entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-07T23:03:12")
@StaticMetamodel(Log.class)
public class Log_ { 

    public static volatile SingularAttribute<Log, Date> dateLog;
    public static volatile SingularAttribute<Log, Date> timeLog;
    public static volatile SingularAttribute<Log, String> ipUserLog;
    public static volatile SingularAttribute<Log, User> userId;
    public static volatile SingularAttribute<Log, Integer> idLog;
    public static volatile SingularAttribute<Log, String> actionLog;

}