/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import sensorManagement.SensorManagement;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class GetListofPhysicalSensorWithData extends ServerResource
{    
    @Post
    public Representation getListofPhysicalSensorWithData(Representation entity)
    {
        JSONObject obj = new JSONObject();
        List<AnimalBodySensor> sensorList = SensorManagement.getInstance().getListofPhysicalSensor();
        JSONArray ja = new JSONArray();
        for(int i = 0; i < sensorList.size(); i++)
        {
                JSONObject jo1 = new JSONObject();
                jo1.put("Name", sensorList.get(i).getName());
                jo1.put("Cluster", sensorList.get(i).getType());
                jo1.put("user", sensorList.get(i).getNumberUser());
                jo1.put("Speed", sensorList.get(i).getSpeed());
                jo1.put("Temp", sensorList.get(i).getTemp());
                jo1.put("Heart", sensorList.get(i).getHeartRate());
                ja.put(jo1);
        }

        obj.put("Phsical_Sensors", ja);

        return new JsonRepresentation(obj);
    }
}

