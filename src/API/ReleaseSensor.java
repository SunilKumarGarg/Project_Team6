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
import userManagement.UserManager;

public class ReleaseSensor extends ServerResource
{    
    @Post
    public Representation releaseSensor(Representation entity)
    {		
            Form form = new Form(entity);
            String szName = form.getFirstValue("username");
            String szAge = form.getFirstValue("age");
            String szType = form.getFirstValue("type");
            String szNumber = form.getFirstValue("num");

            JSONObject obj = new JSONObject();
            obj.put("Result", UserManager.getInstance().releaseSensor(szName, Integer.parseInt(szType), Integer.parseInt(szAge), Integer.parseInt(szNumber)));           

            return new JsonRepresentation(obj);
    }
}
