/*
 * To the extent possible under law, the OME developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.example;

import java.util.Collection;
import java.util.NoSuchElementException;

import omero.api.ThumbnailStorePrx;
import omero.sys.ParametersI;

import omero.gateway.Gateway;
import omero.gateway.LoginCredentials;
import omero.gateway.SecurityContext;
import omero.gateway.facility.BrowseFacility;
import omero.gateway.model.ExperimenterData;
import omero.gateway.model.ProjectData;
import omero.gateway.model.ImageData;
import omero.gateway.model.PixelsData;
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
        cred.getServer().setHost(hostname);
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
    private void connect(String[] args)
        throws Exception
    {
        LoginCredentials cred = new LoginCredentials(args);
        ExperimenterData user = gateway.connect(cred);
        System.out.println("Connected as " + user.getUserName());
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

    /** Loads the image with the id 1.*/
    private void loadFirstImage()
        throws Exception
    {
        ParametersI params = new ParametersI();
        params.acquisitionData();
        BrowseFacility browse = gateway.getFacility(BrowseFacility.class);
        try {
            ImageData image = browse.getImage(ctx, 1L, params);
            PixelsData pixels = image.getDefaultPixels();
            ThumbnailStorePrx store = gateway.getThumbnailService(ctx);
            store.setPixelsId(pixels.getId());
            System.out.println("Ready to get thumbnail");
        } catch(NoSuchElementException e) {
            System.out.println("Image:1 not found");
        }
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
            client.connect(args);
            // Do something e.g. loading user's data.
            // Load the projects/datasets owned by the user currently logged in.
            client.loadProjects();
            client.loadFirstImage();
        } finally {
            client.disconnect();
        }
    }
}
