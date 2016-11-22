/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseManagement;

import billingMonitoring.UserBillingData;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sunil
 */
public class BillingDatabaseManagement {   
    
    public JSONArray getBillInformation(int ndays)
    {
        JSONArray ja = new JSONArray();
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "BillingDatabase" );
            DBCollection table = db.getCollection("BillInfo");
            long timeToStart = ( System.currentTimeMillis() - (ndays*24*60*60*1000));
            
            BasicDBObject query = new BasicDBObject();                
            
            List<BasicDBObject> orQuery = new ArrayList<BasicDBObject>();
            
            orQuery.add(new BasicDBObject("end_time", new BasicDBObject("$gt",timeToStart)));
            orQuery.add(new BasicDBObject("running", 1));
            query.put("$or", orQuery);            
            
            DBCursor cursor = table.find(query);        
            
            
            while(cursor.hasNext())
            {    
                JSONObject obj = new JSONObject();
                DBObject dbObj = cursor.next();
                String sensor = dbObj.get("sensor").toString();
                String[] sens = sensor.split("_");
                obj.put("type", sens[0]);
                obj.put("age", sens[1]);
                obj.put("userName", dbObj.get("user_name"));
                obj.put("sensor", dbObj.get("sensor"));
                obj.put("number", dbObj.get("number"));
                obj.put("start_time", dbObj.get("start_time"));
                obj.put("end_time", dbObj.get("end_time"));
                ja.put(obj);                   
            }  
            
        }
        catch(Exception e)
        {            
        }
        return ja;        
    }
    
    public JSONArray getBillInformation(String userName, int ndays)
    {
        JSONArray ja = new JSONArray();
        try
        {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            DB db = mongoClient.getDB( "BillingDatabase" );
            DBCollection table = db.getCollection("BillInfo");
            long timeToStart = ( System.currentTimeMillis() - (ndays*24*60*60*1000));
            
            BasicDBObject query = new BasicDBObject();
            query.put("user_name", userName);      
            
            List<BasicDBObject> orQuery = new ArrayList<BasicDBObject>();
            
            orQuery.add(new BasicDBObject("end_time", new BasicDBObject("$gt",timeToStart)));
            orQuery.add(new BasicDBObject("running", 1));
            query.put("$or", orQuery);            
            
            DBCursor cursor = table.find(query);        
            
            
            while(cursor.hasNext())
            {    
                JSONObject obj = new JSONObject();
                DBObject dbObj = cursor.next();
                String sensor = dbObj.get("sensor").toString();
                String[] sens = sensor.split("_");
                obj.put("type", sens[0]);
                obj.put("age", sens[1]);
                obj.put("userName", dbObj.get("user_name"));
                obj.put("sensor", dbObj.get("sensor"));
                obj.put("number", dbObj.get("number"));
                obj.put("start_time", dbObj.get("start_time"));
                obj.put("end_time", dbObj.get("end_time")); 
                
                long et = obj.getLong("end_time");
                if(et == 0)
                    obj.put("end_Date", "");
                else
                    obj.put("end_Date", new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date(et)));
                
                et = obj.getLong("start_time");
                if(et == 0)
                    obj.put("start_Date", "");
                else
                    obj.put("start_Date", new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date(et)));
                
                ja.put(obj);                   
            }  
            
        }
        catch(Exception e)
        {            
        }
        return ja;
    }
    
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
