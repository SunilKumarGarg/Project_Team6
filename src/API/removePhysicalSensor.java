/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.util.List;
import sensorManagement.SensorManagement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import sensors.AnimalBodySensor;

/**
 *
 * @author sunil
 */
public class removePhysicalSensor extends ServerResource
{
    @Post
    public Representation deletePhysicalSensor(Representation entity)
    {		
            Form form = new Form(entity);
            String szID = form.getFirstValue("ID");
            
            JSONObject obj = new JSONObject();
            obj.put("Result", SensorManagement.getInstance().removePhysicalSensor(Integer.parseInt(szID)));

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
