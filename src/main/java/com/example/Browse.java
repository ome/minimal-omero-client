/*
 * To the extent possible under law, the OME developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.example;

import java.util.Collection;

import omero.gateway.facility.BrowseFacility;
import omero.gateway.model.ProjectData;

/**
 * Example for browsing through the data hierarchy
 * 
 * @author The OME Team
 */
public class Browse extends Connect {

    /** Loads the projects owned by the user currently logged in.*/
    private Collection<ProjectData> loadProjects()
        throws Exception
    {
        BrowseFacility browse = gateway.getFacility(BrowseFacility.class);
        Collection<ProjectData> projects = browse.getProjects(ctx);
        return projects;
    }
    
    public static void main(String[] args) {
        Browse b = new Browse();

        try {
            b.connect(args);
            b.loadProjects();
        } catch (Exception e) {
        } finally {
            b.disconnect();
        }
        
    }

}
