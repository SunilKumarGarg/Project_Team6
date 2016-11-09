/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userManagement;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import databaseManagement.UserDatabaseManagement;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.UIManager.get;
import sensorManagement.SensorManagement;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class UserManager {
    private List<User> userList = new ArrayList();
    private SensorManagement sm = SensorManagement.getInstance();
    UserDatabaseManagement database = new UserDatabaseManagement();
    
    private static UserManager instance = null;
    private UserManager() {	}
	
    public static UserManager getInstance() 
    {
            if(instance == null) {
                    instance = new UserManager();
            }
            return instance;
    }
    
    public Boolean addUser(User u)
    {
        for(int i = 0; i < userList.size(); i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(u.getName()))
                return false;
        }
        userList.add(u);
        database.addUser(u);
        return true;
    }
    
    public Boolean removeUser(String name)
    {
            for(int i = 0; i < userList.size(); i++)
            {
                if(userList.get(i).getName().equalsIgnoreCase(name))
                {
                    userList.remove(i);
                    database.removeUser(name);
                    return true;
                }
            }

            return false;
    }	

    public List<User> getListofUsers()
    {
            return userList;
    }
    
    public Boolean procureSensors(String userName, int type, int age, int num)
    {
        return true;
    }
    public Boolean releaseSensor(String userName, int type, int age, int num)
    {
        return true;
    }
    
    public void initialize()
    {
        DBCursor cursor = database.getAllUsers();
        List<AnimalBodySensor> sensor = new ArrayList();
        if(cursor.hasNext())
        {            
            while(cursor.hasNext())
            {                    
                DBObject obj = cursor.next();   
                User u = new User();
                u.setName(obj.get("name").toString());
                
                //Get list of sensors 
                BasicDBObject obj1 = (BasicDBObject)obj.get("push");
                BasicDBList list = (BasicDBList)obj1.get("sensors");
                for(int i =0; i < list.size(); i++)
                {
                    BasicDBObject o = (BasicDBObject)list.get(i);
                    AnimalBodySensor a = new AnimalBodySensor();
                    a.setName(o.getString("name"));
                    a.setID(o.getInt("id"));
                    a.setID(o.getInt("age"));
                    a.setID(o.getInt("type"));
                    sensor.add(a);
                }
                
                u.addSensors(sensor);
                userList.add(u);
            }                    
        }
    }    
}
