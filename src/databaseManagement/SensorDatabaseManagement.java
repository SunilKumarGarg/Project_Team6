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
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import sensors.*;

/**
 *
 * @author sunil
 */
public class SensorDatabaseManagement 
{
    public void Clean()
    {
        try
        {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

        // Now connect to your databases
        DB db = mongoClient.getDB( "sensorDatabase" );
        DBCollection table = db.getCollection("sensors");
        table.drop();
        
        db = mongoClient.getDB( "userDatabase" );
        table = db.getCollection("user");
        table.drop();
        
        db = mongoClient.getDB( "BillingDatabase" );
        table = db.getCollection("BillInfo");
        table.drop();
        }
        catch(Exception e)
        {
            
        }
    }
    
    public void updateSensor(AnimalBodySensor p)
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "sensorDatabase" );
            DBCollection table = db.getCollection("sensors");
            
            BasicDBObject query = new BasicDBObject();
            query.put("ID", p.getID());
            
            BasicDBObject document = new BasicDBObject();
            
            document.put("ID", p.getID());
            document.put("name", p.getName());
            document.put("age", p.getAge());
            document.put("type", p.getType());
            document.put("number_user", p.getNumberUser());
            
            table.update(query, document);
        }
        catch(Exception e)
        {
            
        }
    }
    
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
        document.put("number_user", 0);
        
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
