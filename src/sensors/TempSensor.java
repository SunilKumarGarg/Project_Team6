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
public class TempSensor extends Sensor 
{
    public float getTemp()
    {
        return getValue();
    }
}
