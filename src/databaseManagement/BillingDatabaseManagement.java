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
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author sunil
 */
public class BillingDatabaseManagement {
    
    public void procureSensors(String userName, HashMap hm)
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "BillingDatabase" );
            DBCollection table = db.getCollection("BillInfo"); 
            
            Set keySet = hm.keySet();            
            Iterator i = keySet.iterator();
            long time = System.currentTimeMillis();
            
            while(i.hasNext())
            {
                //Get the key
                String key = (String)i.next();
                int numSensors = 0;
                //Get a list of all entry that match with sensor=key, running=1, username
                BasicDBObject query = new BasicDBObject();
                query.put("user_name", userName);
                query.put("sensor", key);
                query.put("running", 1);
                DBCursor listSensor = table.find(query);
                
                //if there are such entry
                    //sum the number of sensors in all these entry
                    //Update all these entry: Running = 0, end_date
                    //Enter new entry with number of sensor as sum of old sensors and new sensors, running 1, start data, user name
                
                if(listSensor.hasNext())
                {                    
                    while(listSensor.hasNext())
                    {
                        DBObject o = listSensor.next();
                        numSensors += (Integer)o.get("number");
                    }
                    numSensors += (int)hm.get(key);
                    BasicDBObject u = new BasicDBObject();                    
                    u.put("running", 0);
                    u.put("end_time", time);
                    
                    BasicDBObject setQuery = new BasicDBObject();
                    setQuery.append("$set", u);

                    table.update(query, setQuery);
                    
                    BasicDBObject a = new BasicDBObject();
                    
                    a.put("user_name", userName);
                    a.put("sensor", key);
                    a.put("running", 1);
                    a.put("start_time", time);
                    a.put("end_time", 0);
                    a.put("number", numSensors);
                    
                    table.insert(a);
                }
                
                //else
                    //Add the entry and start date
                
                else
                {
                    numSensors += (int)hm.get(key);
                    BasicDBObject a = new BasicDBObject();
                    
                    a.put("user_name", userName);
                    a.put("sensor", key);
                    a.put("running", 1);
                    a.put("start_time", time);
                    a.put("end_time", 0);
                    a.put("number", numSensors);
                    
                    table.insert(a);
                }
            }
        }
        catch(Exception e)
        {            
        }
    }
    
    public void releaseSensors(String userName, HashMap hm)
    {
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "BillingDatabase" );
            DBCollection table = db.getCollection("BillInfo"); 
            
            Set keySet = hm.keySet();            
            Iterator i = keySet.iterator();
            long time = System.currentTimeMillis();
            
            while(i.hasNext())
            {
                //Get the key
                String key = (String)i.next();
                int numSensors = 0;
                //Get a list of all entry that match with sensor=key, running=1, username
                BasicDBObject query = new BasicDBObject();
                query.put("user_name", userName);
                query.put("sensor", key);
                query.put("running", 1);
                DBCursor listSensor = table.find(query);
                
                //if there are such entry
                    //sum the number of sensors in all these entry
                    //Update all these entry: Running = 0, end_date
                    //Enter new entry with number of sensor as sum of old sensors and new sensors, running 1, start data, user name
                
                if(listSensor.hasNext())
                {                    
                    while(listSensor.hasNext())
                    {
                        DBObject o = listSensor.next();
                        numSensors += (Integer)o.get("number");
                    }
                    numSensors -= (int)hm.get(key);
                    BasicDBObject u = new BasicDBObject();                    
                    u.put("running", 0);
                    u.put("end_time", time);
                    
                    BasicDBObject setQuery = new BasicDBObject();
                    setQuery.append("$set", u);

                    table.update(query, setQuery);
                    
                    BasicDBObject a = new BasicDBObject();
                    
                    a.put("user_name", userName);
                    a.put("sensor", key);
                    a.put("running", 1);
                    a.put("start_time", time);
                    a.put("end_time", 0);
                    a.put("number", numSensors);
                    
                    table.insert(a);
                }
                
                //else
                    //Add the entry and start date
                
                else
                {
                    numSensors -= (int)hm.get(key);
                    BasicDBObject a = new BasicDBObject();
                    
                    a.put("user_name", userName);
                    a.put("sensor", key);
                    a.put("running", 1);
                    a.put("start_time", time);
                    a.put("end_time", 0);
                    a.put("number", numSensors);
                    
                    table.insert(a);
                }
            }
        }
        catch(Exception e)
        {            
        }
    }    
}
