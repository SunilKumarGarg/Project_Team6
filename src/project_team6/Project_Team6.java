package project_team6;

import API.AdminSensorManagement;
import sensorManagement.SensorManagement;
import API.removePhysicalSensor;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.data.Protocol;
import org.restlet.Component;
import API.AddUser;
import API.GetAllUsers;
import API.RemoveUser;
import userManagement.UserManager;

public class Project_Team6 extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attach("/AddNewPhysicalSensor", AdminSensorManagement.class);
        router.attach("/RemovePhysicalSensor", removePhysicalSensor.class);
        router.attach("/AddUser", AddUser.class);
        router.attach("/RemoveUser", RemoveUser.class);
        router.attach("/GetAllUsers", GetAllUsers.class);

        return router;
    }
    
    public static void main(String[] args) throws Exception 
    {  
        // Create a new Component.  
        Component component = new Component(); 
        
        // Add a new HTTP server listening on port 8080.  
        component.getServers().add(Protocol.HTTP, 8080); 	

        // Attach the sample application.  
        component.getDefaultHost().attach("/ProjectTeam6",  
            new Project_Team6());

        // Start the component.  
        component.start();
        SensorManagement.getInstance().initialize();
        UserManager.getInstance().initialize();
    }  


}
