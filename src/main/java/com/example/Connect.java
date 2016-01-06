/*
 * To the extent possible under law, the OME developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.example;


import omero.gateway.Gateway;
import omero.gateway.LoginCredentials;
import omero.gateway.SecurityContext;
import omero.gateway.model.ExperimenterData;
import omero.log.SimpleLogger;

/**
 * A simple connection to an OMERO server using the Java gateway
 *
 * @author The OME Team
 */
public class Connect {

    /** Reference to the gateway.*/
    Gateway gateway;

    /** The security context.*/
    SecurityContext ctx;

    /** 
     * Creates a connection, the gateway will take care of the services
     * life-cycle.
     *
     * @param hostname The name of the server.
     * @param port The port to use.
     * @param userName The name of the user.
     * @param password The user's password.
     */
    void connect(String hostname, int port, String userName, String password)
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

    /** 
     * Creates a connection, the gateway will take care of the services
     * life-cycle.
     *
     * @param args The arguments used to connect.
     */
    void connect(String[] args)
        throws Exception
    {
        LoginCredentials cred = new LoginCredentials(args);
        ExperimenterData user = gateway.connect(cred);
        ctx = new SecurityContext(user.getGroupId());
    }
    
    /** Makes sure to disconnect to destroy sessions.*/
    void disconnect()
    {
        gateway.disconnect();
    }

    /** Creates a new instance.*/
    Connect()
    {
        gateway = new Gateway(new SimpleLogger());
    }

    /**
     * The main method
     * @param args The login credentials, in the form
     *             ["--omero.host=localhost", "--omero.port=4064",
     *              "--omero.user=root", "--omero.pass=omero"]
     */
    public static void main(String[] args) {
        Connect client = new Connect();
        try {
            client.connect(args);
            //Do something e.g. loading user's data.
        } catch (Exception e) {
        } finally {
            client.disconnect();
        }
    }
}
