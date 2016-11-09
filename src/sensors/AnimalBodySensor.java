/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensors;

/**
 *
 * @author sunil
 */
public class AnimalBodySensor implements Comparable<AnimalBodySensor>{
    private int ID;
    private TempSensor tempSensor;
    private SpeedSensor speedSensor;
    private HeartRateSensor heartRateSensor;
    private String szName;
    private int nAge;
    private int nType;
    private int nNumberUser;
    
    public int compareTo(AnimalBodySensor obj)
    {     
        if(obj.nNumberUser > this.nNumberUser)
            return -1;
        else if(obj.nNumberUser == this.nNumberUser)
            return 0;
        else if(obj.nNumberUser < this.nNumberUser)
            return 1;
        
        return 0;
    } 
    
    public void increaseNumberUser()
    {
        nNumberUser++;
    }
    
    public void decreaseNumberUser()
    {
        nNumberUser--;
    }
    
    public int getNumberUser()
    {
        return nNumberUser;
    }
    
    public int getID()
    {
        return ID;
    }
    public void setID(int n)
    {
        ID = n;
    }
    public float getTemp()
    {
        return tempSensor.getTemp();
    }
    public float getHeartRate()
    {
        return heartRateSensor.getHeartRate();
    }
    public float getSpeed()
    {
        return speedSensor.getSpeed();
    }
    public String getName()
    {
        return szName;
    }
    public void setName(String name)
    {
        szName = name;
    }
    public int getAge()
    {
        return nAge;
    }
    public void setAge(int n)
    {
        nAge = n;
    }
    
    public int getType()
    {
        return nType;
    }
    
    public void setType(int n)
    {
        nType = n;
    }
}
