/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

/**
 *
 * @author sunil
 */

import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.data.Form;

import java.util.*;
import sensorManagement.SensorManagement;
import sensors.*;

public class AdminSensorManagement extends ServerResource
{    
    @Post
    public Representation addNewPhysicalSensor(Representation entity)
    {		
            Form form = new Form(entity);
            String szID = form.getFirstValue("ID");
            String szName = form.getFirstValue("name");
            String szAge = form.getFirstValue("age");
            String szType = form.getFirstValue("type");

            AnimalBodySensor p = new AnimalBodySensor();
            p.setID(Integer.parseInt(szID.toString()));
            p.setName(szName);
            p.setAge(Integer.parseInt(szAge.toString()));
            p.setType(Integer.parseInt(szType.toString()));


            JSONObject obj = new JSONObject();
            obj.put("Result", SensorManagement.getInstance().addNewPysicalSensor(p));

            List<AnimalBodySensor> sensorList = SensorManagement.getInstance().getListofPhysicalSensor();
            JSONArray ja = new JSONArray();
            for(int i = 0; i < sensorList.size(); i++)
            {
                    JSONObject jo = new JSONObject();
                    jo.put("ID", sensorList.get(i).getID());
                    jo.put("name", sensorList.get(i).getName());
                    jo.put("age", sensorList.get(i).getAge());
                    jo.put("type", sensorList.get(i).getType());
                    ja.put(jo);
            }

            obj.put("Phsical_Sensors", ja);

            return new JsonRepresentation(obj);
    }
}
