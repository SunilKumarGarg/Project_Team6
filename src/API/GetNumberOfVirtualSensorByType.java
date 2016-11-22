
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
public class GetNumberOfVirtualSensorByType extends ServerResource
{    
    @Post
    public Representation getNumberOfVirtualSensorByType(Representation entity)
    {
        JSONObject obj = new JSONObject();
        List<AnimalBodySensor> sensorList = SensorManagement.getInstance().getListofPhysicalSensor();
        JSONArray ja = new JSONArray();
        int nTiger = 0;
        int nLion = 0;
        int nBear = 0;
        int nElephant = 0;
        int nDeer = 0;
        for(int i = 0; i < sensorList.size(); i++)
        {
            switch(sensorList.get(i).getType())
            {                
                case 1:
                    nTiger += sensorList.get(i).getNumberUser();
                    break;
                case 2:
                    nLion += sensorList.get(i).getNumberUser();
                    break;
                case 3:
                    nBear += sensorList.get(i).getNumberUser();
                    break;
                case 4:
                    nElephant += sensorList.get(i).getNumberUser();
                    break;
                case 5:
                    nDeer += sensorList.get(i).getNumberUser();
                    break;                    
            }               
        }

        JSONObject jo = new JSONObject();
        jo.put("label", "Tiger");
        jo.put("value", nTiger);
        ja.put(jo);
        
        jo = new JSONObject();
        jo.put("label", "Lion");
        jo.put("value", nLion);
        ja.put(jo);
        
        jo = new JSONObject();
        jo.put("label", "Bear");
        jo.put("value", nBear);
        ja.put(jo);
        
        jo = new JSONObject();
        jo.put("label", "Elephant");
        jo.put("value", nElephant);
        ja.put(jo);
        
        jo = new JSONObject();
        jo.put("label", "Deer");
        jo.put("value", nDeer);
        ja.put(jo);
                
        obj.put("Phsical_Sensors", ja);

        return new JsonRepresentation(obj);
    }
}

