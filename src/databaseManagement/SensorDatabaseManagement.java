/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseManagement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import sensors.*;

/**
 *
 * @author sunil
 */
public class SensorDatabaseManagement 
{
    
    public void addPhysicalSensor(AnimalBodySensor p)
    {
        try{
	// To connect to mongodb server

	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

	// Now connect to your databases
	DB db = mongoClient.getDB( "sensorDatabase" );
	DBCollection table = db.getCollection("sensors");       
	
	BasicDBObject document = new BasicDBObject();    
        
        document.put("ID", p.getID());
        document.put("name", p.getName());
        document.put("age", p.getAge());
        document.put("type", p.getType());
        
	table.insert(document);       
			
        }
        catch(Exception e)
        {            
        }
    }
    
    public void removePhysicalSensor(int nID)
    {
        try
        {
            // To connect to mongodb serve
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // Now connect to your databases
            DB db = mongoClient.getDB( "sensorDatabase" );
            DBCollection table = db.getCollection("sensors");       

            BasicDBObject document = new BasicDBObject();    

            document.put("ID", nID);

            table.remove(document);			
        }
        catch(Exception e)
        {            
        }
    }
    
    public DBCursor getAllPhysicalSensor()
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db = mongoClient.getDB( "sensorDatabase" );
            DBCollection table = db.getCollection("sensors");
            return table.find();
        }
        catch(Exception e)
        {            
        }
        return null;
    }
}
