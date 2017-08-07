/*
 * To the extent possible under law, the OME developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.example;

import java.util.Collection;

import omero.gateway.Gateway;
import omero.gateway.LoginCredentials;
import omero.gateway.SecurityContext;
import omero.gateway.facility.BrowseFacility;
import omero.gateway.model.ExperimenterData;
import omero.gateway.model.ProjectData;
import omero.log.SimpleLogger;

/**
 * A simple connection to an OMERO server using the Java gateway
 *
 * @author The OME Team
 */
public class SimpleConnection {

    /** Reference to the gateway.*/
    private Gateway gateway;

    /** The security context.*/
    private SecurityContext ctx;

    /** 
     * Creates a connection, the gateway will take care of the services
     * life-cycle.
     *
     * @param hostname The name of the server.
     * @param port The port to use.
     * @param userName The name of the user.
     * @param password The user's password.
     */
    private void connect(String hostname, int port, String userName, String password)
        throws Exception
    {
        LoginCredentials cred = new LoginCredentials();
        cred.getServer().setHostname(hostname);
        if (port > 0) {
            cred.getServer().setPort(port);
        }
        cred.getUser().setUsername(userName);
        cred.getUser().setPassword(password);
        ExperimenterData user = gateway.connect(cred);
        ctx = new SecurityContext(user.getGroupId());
    }
    
    /** Makes sure to disconnect to destroy sessions.*/
    private void disconnect()
    {
        gateway.disconnect();
    }

    /** Loads the projects owned by the user currently logged in.*/
    private void loadProjects()
        throws Exception
    {
        BrowseFacility browse = gateway.getFacility(BrowseFacility.class);
        Collection<ProjectData> projects = browse.getProjects(ctx);
    }

    /** Creates a new instance.*/
    SimpleConnection()
    {
        gateway = new Gateway(new SimpleLogger());
    }

    /**
     */
    public static void main(String[] args) throws Exception {
        SimpleConnection client = new SimpleConnection();
        try {
            String host = null;
            int port = 4064;
            String user = null;
            String password = null;
            for (String arg : args) {
                String[] tmp = arg.split("=");
                tmp[1].replace("'", "");
                tmp[1].replace("\"", "");
                if (tmp[0].trim().equals("--omero.host"))
                    host = tmp[1].trim();
                if (tmp[0].trim().equals("--omero.port"))
                    port = Integer.parseInt(tmp[1].trim());
                if (tmp[0].trim().equals("--omero.user"))
                    user = tmp[1].trim();
                if (tmp[0].trim().equals("--omero.pass"))
                    password = tmp[1].trim();
            }

            if (host != null && user != null && password != null) {
                client.connect(host, port, user, password);
                // Do something e.g. loading user's data.
                // Load the projects/datasets owned by the user currently logged
                // in.
                client.loadProjects();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            client.disconnect();
        }
    }
}
