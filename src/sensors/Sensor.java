/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensors;

import java.util.Random;

/**
 *
 * @author sunil
 */
public class Sensor{
    private float fValue;
    private String unit;    
    Random randomNumberGenerator = new Random();   
    
    public void setUnit(String u)
    {
        unit = u;
    }
    public String getUnit(String u)
    {
        return unit;
    }
    
    public float getValue()
    {
        return randomNumberGenerator.nextInt(10);
        //return fValue;
    }      
}
