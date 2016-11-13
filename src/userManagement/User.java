/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userManagement;

import java.util.ArrayList;
import java.util.List;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class User {
    
    private String name;
    private List<AnimalBodySensor> sensorList = new ArrayList();
    
    
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
        return sensorList;
    }
    
    public void addSensors(List<AnimalBodySensor> s)
    {
        sensorList.addAll(s);
    }
    
    public void addSensor(AnimalBodySensor s)
    {
        sensorList.add(s);
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
    }
}
