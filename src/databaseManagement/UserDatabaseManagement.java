/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseManagement;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.List;
import sensors.AnimalBodySensor;
import userManagement.User;

/**
 *
 * @author sunil
 */
public class UserDatabaseManagement {
    
    public void updateSensor(User u)
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "userDatabase" );
            DBCollection table = db.getCollection("user"); 
            
            BasicDBObject query = new BasicDBObject();
            query.put("name", u.getName());

            BasicDBObject document = new BasicDBObject();
            document.put("name", u.getName());
            BasicDBList dbSensorList = mapSensors(u.getSensors());
            document.append("push", new BasicDBObject("sensors", dbSensorList));

            table.update(query, document);       

        }
        catch(Exception e)
        {            
        }
    }
    
    public void addUser(User u)
    {
        try
        {

            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "userDatabase" );
            DBCollection table = db.getCollection("user");       

            BasicDBObject document = new BasicDBObject();

            document.put("name", u.getName());

            BasicDBList dbSensorList = mapSensors(u.getSensors());
            document.append("push", new BasicDBObject("sensors", dbSensorList));

            table.insert(document);       

        }
        catch(Exception e)
        {            
        }
    }
    
    public void removeUser(String sz)
    {
        try
        {
            // To connect to mongodb serve
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // Now connect to your databases
            DB db = mongoClient.getDB( "userDatabase" );
            DBCollection table = db.getCollection("user");       

            BasicDBObject document = new BasicDBObject();    

            document.put("name", sz);

            table.remove(document);			
        }
        catch(Exception e)
        {            
        }
    }
    
    public DBCursor getAllUsers()
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db = mongoClient.getDB( "userDatabase" );
            DBCollection table = db.getCollection("user");
            //table.drop();
            return table.find();
        }
        catch(Exception e)
        {            
        }
        return null;
    }
    
    private BasicDBList mapSensors(List<AnimalBodySensor> sensors) 
    {
        BasicDBList result = new BasicDBList();
        
        for (AnimalBodySensor sensor: sensors) 
        {
          BasicDBObject dbSensor = new BasicDBObject();
          dbSensor.append("name", sensor.getName());
          dbSensor.append("id", sensor.getID());
          dbSensor.append("age", sensor.getAge());
          dbSensor.append("type", sensor.getType());
          dbSensor.append("number_user", sensor.getNumberUser());
          result.add(dbSensor);
        }

        return result;
    }
    
}
