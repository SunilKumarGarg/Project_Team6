/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userManagement;

import java.util.ArrayList;
import java.util.List;
import sensorManagement.SensorManagement;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class User {
    
    private String name;
    private List<AnimalBodySensor> sensorList = new ArrayList();
    private List<AnimalBodySensor> uniqueSensorList = new ArrayList();
    private List<Integer> uniqueSensorCount = new ArrayList();
    
    private SensorManagement sensorManagement = SensorManagement.getInstance();
    
    public void updateData()
    {
        for(int i=0; i < sensorList.size(); i++)
        {
            AnimalBodySensor newSensorData = sensorManagement.getUpdateSensorData(sensorList.get(i).getID());
            if(newSensorData != null)
            {
                sensorList.get(i).setAge(newSensorData.getAge());
                sensorList.get(i).setNumberOfUser(newSensorData.getNumberUser());
            }
        }
        
        for(int i=0; i < uniqueSensorList.size(); i++)
        {
            AnimalBodySensor newSensorData = sensorManagement.getUpdateSensorData(uniqueSensorList.get(i).getID());
            if(newSensorData != null)
            {
                uniqueSensorList.get(i).setAge(newSensorData.getAge());
                uniqueSensorList.get(i).setNumberOfUser(newSensorData.getNumberUser());
            }
        }
    }
    
    public void setName(String sz)
    {
        name = sz;
    }
    public String getName()
    {
        return name;
    }
    public List<AnimalBodySensor> getSensors()
    {
        updateData();
        return sensorList;
    }
    
    public List<AnimalBodySensor> getUniqueSensorList()
    {
        updateData();
        return uniqueSensorList;
    }
    
    public List<Integer> getUniqueSensorsCount()
    {
        return uniqueSensorCount;
    }
    
    public void addSensors(List<AnimalBodySensor> s)
    {
        sensorList.addAll(s);
        
        boolean bFound = false;
        for(int i = 0; i < s.size(); i++)
        {
            bFound = false;
            for(int j = 0; j < uniqueSensorList.size(); j++)
            {
                if(uniqueSensorList.get(j).getID() == s.get(i).getID())
                {
                    uniqueSensorCount.set(j, uniqueSensorCount.get(j) + 1);
                    bFound = true;
                    break;
                }
            }
            
            if(bFound == false)
            {
                uniqueSensorList.add(0, s.get(i));
                uniqueSensorCount.add(0,1);
            }
        }
    }
    
    public void addSensor(AnimalBodySensor s)
    {
        sensorList.add(s);
        
        boolean bFound = false;
        for(int j = 0; j < uniqueSensorList.size(); j++)
        {
            if(uniqueSensorList.get(j).getID() == s.getID())
            {
                uniqueSensorCount.set(j, uniqueSensorCount.get(j) + 1);
                bFound = true;
                break;
            }
        }

        if(bFound)
        {
            uniqueSensorList.add(0, s);
            uniqueSensorCount.add(0,1);
        }
    }
    
    public void removeSensors(List<AnimalBodySensor> s)
    {
        for(int i = 0; i < s.size(); i++)
        {
            for(int j = 0; j < sensorList.size(); j++)
            {
                if(sensorList.get(j).getID() == s.get(i).getID())
                {
                    sensorList.remove(j);
                    break;
                }
            }
        }
        
        for(int i = 0; i < s.size(); i++)
        {
            for(int j = 0; j < uniqueSensorList.size(); j++)
            {
                if(uniqueSensorList.get(j).getID() == s.get(i).getID())
                {
                    uniqueSensorCount.set(j, uniqueSensorCount.get(j)-1);
                    break;
                }
            }
        }
    }
    
    public void removeSensor(int n)
    {
        for(int i = 0; i < sensorList.size(); i++)
        {
            if(sensorList.get(i).getID() == n)
            {
                sensorList.remove(i);
            }
                    
        }
        
        for(int j = 0; j < uniqueSensorList.size(); j++)
        {
            if(uniqueSensorList.get(j).getID() == n)
            {
                uniqueSensorCount.set(j, uniqueSensorCount.get(j)-1);
                break;
            }
        }
    }
}
