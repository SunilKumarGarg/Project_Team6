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
import databaseManagement.BillingDatabaseManagement;
import databaseManagement.UserDatabaseManagement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sensorManagement.SensorManagement;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class UserManager {
    private List<User> userList = new ArrayList();
    private SensorManagement sensorManagement = SensorManagement.getInstance();
    UserDatabaseManagement database = new UserDatabaseManagement();
    BillingDatabaseManagement billDatabase = new BillingDatabaseManagement();
    
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
        List<AnimalBodySensor> sensors = sensorManagement.procureSensors(type, age, num);
        
        for(int i = 0; i < userList.size(); i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(userName))
            {
                userList.get(i).addSensors(sensors);
                database.updateSensor(userList.get(i));
                
                HashMap hm = new HashMap();
                
                for(int j = 0; j < sensors.size(); j++)
                {
                    String s = String.valueOf(sensors.get(j).getType()).concat( "_").concat( String.valueOf(sensors.get(j).getAge()));
                    if(hm.containsKey(s))
                    {
                        int p = (int)hm.get(s);
                        hm.put(s, p+1);
                    }
                    else
                    {
                        hm.put(s, 1);                        
                    }
                }
                billDatabase.procureSensors(userName, hm);
                break;
            }
        }
        
        return true;
    }
    public Boolean releaseSensor(String userName, int type, int age, int num)
    { 
        List<AnimalBodySensor> currSensorList = new ArrayList();
        List<AnimalBodySensor> sensorListRemove = new ArrayList();
        int n = 0;
        int i = 0;
        for(i = 0; i < userList.size(); i++)
        {
            if(userList.get(i).getName().equalsIgnoreCase(userName))
            {
                currSensorList.addAll(userList.get(i).getSensors());
                break;
            }
        }
        
        for(int j =0; j < currSensorList.size(); j++)
        {
            if(currSensorList.get(j).getType() == type && currSensorList.get(j).getAge() == age && n < num)
            {
                sensorListRemove.add(currSensorList.get(j));
                n++;
            }
        }
        userList.get(i).removeSensors(sensorListRemove);
        sensorManagement.releaseSensors(sensorListRemove);
        database.updateSensor(userList.get(i));
        
        HashMap hm = new HashMap();
                
        for(int j = 0; j < sensorListRemove.size(); j++)
        {
            String s = String.valueOf(sensorListRemove.get(j).getType()).concat( "_").concat( String.valueOf(sensorListRemove.get(j).getAge()));
            if(hm.containsKey(s))
            {
                int p = (int)hm.get(s);
                hm.put(s, p+1);
            }
            else
            {
                hm.put(s, 1);                        
            }
        }
        billDatabase.releaseSensors(userName, hm);
                
        return true;
    }
    
    public void initialize()
    {
        DBCursor cursor = database.getAllUsers();        
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
                List<AnimalBodySensor> sensor = new ArrayList();
                for(int i =0; i < list.size(); i++)
                {
                    BasicDBObject o = (BasicDBObject)list.get(i);
                    AnimalBodySensor a = new AnimalBodySensor();
                    a.setName(o.getString("name"));
                    a.setID(o.getInt("id"));
                    a.setAge(o.getInt("age"));
                    a.setType(o.getInt("type"));
                    a.setNumberOfUser(o.getInt("number_user"));
                    sensor.add(a);
                }
                
                u.addSensors(sensor);
                userList.add(u);
            }                    
        }
    }    
}
