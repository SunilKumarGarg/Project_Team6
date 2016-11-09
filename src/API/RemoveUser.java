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
import userManagement.User;
import userManagement.UserManager;

/**
 *
 * @author sunil
 */
public class RemoveUser extends ServerResource {
    
    @Post
    public Representation removeUser(Representation entity)
    {		
            Form form = new Form(entity);
            String szName = form.getFirstValue("name");
            
            JSONObject obj = new JSONObject();
            obj.put("Result", UserManager.getInstance().removeUser(szName));

            List<User> userList = UserManager.getInstance().getListofUsers();
            JSONArray ja = new JSONArray();
            for(int i = 0; i < userList.size(); i++)
            {
                    JSONObject jo = new JSONObject();
                    jo.put("name", userList.get(i).getName());
                    ja.put(jo);
            }

            obj.put("Users:", ja);

            return new JsonRepresentation(obj);
    }
    
}
