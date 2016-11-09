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
import sensors.AnimalBodySensor;
import userManagement.User;
import userManagement.UserManager;

/**
 *
 * @author sunil
 */
public class GetAllUsers extends ServerResource{
    
    @Post
    public Representation getAllUsers(Representation entity)
    {       
        JSONObject obj = new JSONObject();
        
        List<User> userList = UserManager.getInstance().getListofUsers();
        JSONArray ja = new JSONArray();
        for(int i = 0; i < userList.size(); i++)
        {
                JSONObject jo = new JSONObject();
                jo.put("User", userList.get(i).getName());
                
                List<AnimalBodySensor> sensorList = userList.get(i).getSensors();
                JSONArray ja1 = new JSONArray();
                for(int j = 0; j < sensorList.size(); j++)
                {
                        JSONObject jo1 = new JSONObject();
                        jo1.put("ID", sensorList.get(j).getID());
                        jo1.put("name", sensorList.get(j).getName());
                        jo1.put("type", sensorList.get(j).getType());
                        jo1.put("Age", sensorList.get(j).getAge());
                        ja1.put(jo1);
                }
                jo.put("Sensors", ja1);
                ja.put(jo);
        }

        obj.put("Users", ja);

        return new JsonRepresentation(obj);
    }    
}
