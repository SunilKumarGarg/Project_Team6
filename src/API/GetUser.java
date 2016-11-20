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
import userManagement.User;
import userManagement.UserManager;

/**
 *
 * @author sunil
 */
public class GetUser extends ServerResource {
    
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
                obj.put("Sensors", userList.get(i).getSensors());
                break;
            }
        }
        

        return new JsonRepresentation(obj);
    }
    
}
