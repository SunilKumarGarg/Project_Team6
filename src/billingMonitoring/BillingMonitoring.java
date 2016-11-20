/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billingMonitoring;

import org.json.JSONObject;

/**
 *
 * @author sunil
 */
public class BillingMonitoring {
    
    public double calculateDiscount(double nTotalAmount, int nDays)
    {
        //Prorated 5% on any amount: promotional discount
        //Prorated 10% if total amount is more than 1000 per month
        //Prorated 20% if total amount is more than 2000 per month 
        //Prorated 30% if total amount is more than 3000 per month 
        //Prorated 40% if total amount is more than 4000 per month 
        
        double nDiscount = 0;
        
        double nProratedAmount = nTotalAmount * 30 / nDays;
        
        if(nProratedAmount > 4000)
        {
            nDiscount = nTotalAmount * 0.4;
        }
        else if(nProratedAmount > 3000)
        {
            nDiscount = nTotalAmount * 0.3;
        }
        else if(nProratedAmount > 2000)
        {
            nDiscount = nTotalAmount * 0.2;
        }
        else if(nProratedAmount > 1000)
        {
            nDiscount = nTotalAmount * 0.1;
        }
        else
        {
            nDiscount = nTotalAmount * 0.05;
        }   
        
        return nDiscount;
    }
    
    public double calculate(JSONObject obj)
    {
        //Type 1 Tiger: $0.1 per hour per sensor
        //Type 2 Lion: $0.1 per hour per sensor
        //Type 3 Beer: $0.01 per hour per sensor
        //Type 4 Elephant: $0.09 per hour per sensor
        //Type 5 Deer: $0.05 per hour per sensor
        double PRICE_SENSOR_1 = 0.1;
        double PRICE_SENSOR_2 = 0.1;
        double PRICE_SENSOR_3 = 0.01;
        double PRICE_SENSOR_4 = 0.09;
        double PRICE_SENSOR_5 = 0.05;
        
        String sensor = obj.get("sensor").toString();
        String[] parts = sensor.split("_"); 
        int sensorType = Integer.parseInt(parts[0]);
        
        long nEndDate = obj.getLong("end_time");
        long nStartDate = obj.getLong("start_time");       
        
        int number = obj.getInt("number");
        
        if(nEndDate == 0)
        {
            nEndDate = System.currentTimeMillis();
        }
        
        double nTime = (nEndDate - nStartDate)/(1000*60*60.0);
        double nPrice = 0;
        switch(sensorType)
        {
            case 1:
                nPrice = number*nTime*PRICE_SENSOR_1;
                break;
            case 2:
                nPrice = number*nTime*PRICE_SENSOR_2;
                break;
            case 3:
                nPrice = number*nTime*PRICE_SENSOR_3;
                break;
            case 4:
                nPrice = number*nTime*PRICE_SENSOR_4;
                break;
            case 5:
                nPrice = number*nTime*PRICE_SENSOR_5;
                break;
        }
        return nPrice;
    } 
    
}
