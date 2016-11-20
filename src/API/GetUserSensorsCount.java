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
import org.restlet.data.Header;
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
public class GetUserSensorsCount extends ServerResource {
    
    @Post
    public Representation addUser(Representation entity)
    {
        Form form = new Form(entity);
        String szName = form.getFirstValue("name");
        
        User user = new User();
        user.setName(szName);
        
        JSONObject obj = new JSONObject();
        
        List<User> userList = UserManager.getInstance().getListofUsers();
        
        for(int i = 0; i < userList.size(); i++)
        {            
            if(userList.get(i).getName().compareToIgnoreCase(szName) == 0)   
            {
                List<AnimalBodySensor> sensorList = userList.get(i).getUniqueSensorList();
                List<Integer> sensorCount = userList.get(i).getUniqueSensorsCount();
                
                JSONArray ja1 = new JSONArray();
                
                for(int j = 0; j < sensorList.size(); j++)
                {
                        JSONObject jo1 = new JSONObject();
                        jo1.put("label", sensorList.get(j).getName());
                        jo1.put("value", sensorCount.get(j));
                        ja1.put(jo1);
                }
                obj.put("Sensors", ja1);
                break;
            }
        }
        

        return new JsonRepresentation(obj);
    }
    
}
