/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import billingMonitoring.BillingMonitoring;
import databaseManagement.BillingDatabaseManagement;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author sunil
 */
public class GetClusterRevenueInfo extends ServerResource
{    
    @Post
    public Representation getClusterRevenueInfo(Representation entity)
    {		
            BillingMonitoring billingMonitoring = new BillingMonitoring();
            BillingDatabaseManagement billDatabase = new BillingDatabaseManagement();    
            
            Form form = new Form(entity);
            String szDays = form.getFirstValue("Days");    
            
            
            JSONArray ja = new JSONArray();
            
            
            ja = billDatabase.getBillInformation(Integer.parseInt(szDays)); //Day and cluster
            
            JSONObject obj1 = new JSONObject();
            
            double[] nTotalAmount = new double[6];
            
            for(int i = 0; i < ja.length(); i++)
            {
                obj1 = ja.getJSONObject(i);
                double bill = billingMonitoring.calculate(obj1);
                
                int nType = obj1.getInt("type");
                nTotalAmount[nType] += bill;
            }
            
            ja = new JSONArray();
            
            DecimalFormat formatter = new DecimalFormat("#0.00");
            
            JSONObject obj = new JSONObject();
            obj.put("label", "Tiger");
            obj.put("value", formatter.format(nTotalAmount[1]));
            
            ja.put(obj);
            
            obj = new JSONObject();
            obj.put("label", "Lion");
            obj.put("value", formatter.format(nTotalAmount[2]));
            
            ja.put(obj);
            
            obj = new JSONObject();
            obj.put("label", "Bear");
            obj.put("value", formatter.format(nTotalAmount[3]));
            
            ja.put(obj);
            
            obj = new JSONObject();
            obj.put("label", "Elephant");
            obj.put("value", formatter.format(nTotalAmount[4]));
            
            ja.put(obj);

            obj = new JSONObject();
            obj.put("label", "Deer");
            obj.put("value", formatter.format(nTotalAmount[5]));
            
            ja.put(obj);
            
            JSONObject obj2 = new JSONObject();
            obj2.put("Revenue", ja);
            
            return new JsonRepresentation(obj2);
    }     
}

