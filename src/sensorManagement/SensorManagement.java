package sensorManagement; 
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import databaseManagement.SensorDatabaseManagement;
import java.util.*;
import sensors.*; 

public class SensorManagement
{
	List<AnimalBodySensor> sensorList = new ArrayList();
        SensorDatabaseManagement database = new SensorDatabaseManagement();

	private static SensorManagement instance = null;
   	private SensorManagement() {	}
	
	public static SensorManagement getInstance() 
	{
      		if(instance == null) {
         		instance = new SensorManagement();
      		}
      		return instance;
   	}
        
        public List<AnimalBodySensor> procureSensors(int nType, int nAge, int num)
        {
            List<AnimalBodySensor> sensor = new ArrayList();
            
            for(int i =0; i < sensorList.size(); i++)
            {
                if(sensorList.get(i).getType() == nType && sensorList.get(i).getAge() == nAge)
                {
                    for(int j = 0; j < num; j++)
                    {
                        sensor.add(sensorList.get(i));
                        sensorList.get(i).increaseNumberUser();
                        database.updateSensor(sensorList.get(i));
                    }
                    break;
                }
            }
            Collections.sort(sensorList);
            return sensor;
        }
        
        public void releaseSensors(List<AnimalBodySensor> sensors)
        {
            for(int i =0; i < sensors.size(); i++)
            {
                for(int j = 0; j < sensorList.size(); j++)
                {
                    if(sensors.get(i).getID() == sensorList.get(j).getID())
                    {
                        sensorList.get(j).decreaseNumberUser();
                        database.updateSensor(sensorList.get(j));
                        break;
                    }
                }
            }     
            Collections.sort(sensorList);
        }

	public Boolean addNewPysicalSensor(AnimalBodySensor newSensor)
	{
            for(int i = 0; i < sensorList.size(); i++)
            {
                if(sensorList.get(i).getID() == newSensor.getID() || sensorList.get(i).getName().equalsIgnoreCase(newSensor.getName()))
                        return false;
            }		

            sensorList.add(newSensor);
            database.addPhysicalSensor(newSensor);
            Collections.sort(sensorList);
            return true;
	}
        
	public Boolean removePhysicalSensor(int nID)
	{
		for(int i = 0; i < sensorList.size(); i++)
		{
                    if(sensorList.get(i).getID() == nID)
                    {
                            sensorList.remove(i);
                            database.removePhysicalSensor(nID);
                            Collections.sort(sensorList);
                            return true;
                    }
		}
		
		return false;
	}	
	
	public List<AnimalBodySensor> getListofPhysicalSensor()
	{
		return sensorList;
	}
        
        public void initialize()
        {
            //database.Clean();
            DBCursor cursor = database.getAllPhysicalSensor();
            if(cursor.hasNext())
            {            
                while(cursor.hasNext())
                {                    
                    DBObject obj = cursor.next();   
                    AnimalBodySensor s = new AnimalBodySensor();
                    s.setID(Integer.parseInt(obj.get("ID").toString()));
                    s.setName(obj.get("name").toString());
                    s.setAge(Integer.parseInt(obj.get("age").toString()));
                    s.setType(Integer.parseInt(obj.get("type").toString()));
                    s.setNumberOfUser(Integer.parseInt(obj.get("number_user").toString()));
                    sensorList.add(s);
                }  
                
                Collections.sort(sensorList);
            }
        }
	
}