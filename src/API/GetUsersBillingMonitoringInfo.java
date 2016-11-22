/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import billingMonitoring.BillingMonitoring;
import databaseManagement.BillingDatabaseManagement;
import java.text.NumberFormat;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class GetUsersBillingMonitoringInfo extends ServerResource
{    
    @Post
    public Representation getUsersBillingMonitoringInfo(Representation entity)
    {		
            BillingMonitoring billingMonitoring = new BillingMonitoring();
            
            Form form = new Form(entity);
            String szName = form.getFirstValue("username");
            String szDays = form.getFirstValue("Days");
            
            BillingDatabaseManagement billDatabase = new BillingDatabaseManagement();    
            
            JSONArray ja = new JSONArray();
            JSONObject obj = new JSONObject();
            
            ja = billDatabase.getBillInformation(szName, Integer.parseInt(szDays));
            
            JSONObject obj1 = new JSONObject();
            JSONArray ja1 = new JSONArray();
            double nTotalAmount = 0;
            double nDiscount = 0;
            for(int i = 0; i < ja.length(); i++)
            {
                obj1 = ja.getJSONObject(i);
                double bill = billingMonitoring.calculate(obj1);
                obj1.put("Amount", NumberFormat.getCurrencyInstance(Locale.US).format(bill));
                ja1.put(obj1);
                
                nTotalAmount += bill;
            }
            nDiscount = billingMonitoring.calculateDiscount(nTotalAmount, Integer.parseInt(szDays));
            obj.put("Total", NumberFormat.getCurrencyInstance(Locale.US).format(nTotalAmount));
            obj.put("Discount", NumberFormat.getCurrencyInstance(Locale.US).format(nDiscount));
            obj.put("Total_After_Discount", NumberFormat.getCurrencyInstance(Locale.US).format(nTotalAmount - nDiscount));
            obj.put("bill", ja1);           

            return new JsonRepresentation(obj);
    }     
}

