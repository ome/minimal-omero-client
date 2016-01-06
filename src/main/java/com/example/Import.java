/*
 * To the extent possible under law, the OME developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.example;

import java.io.File;

import omero.gateway.facility.TransferFacility;
import omero.gateway.model.ImportCallback;

/**
 * Example for importing images to the OMERO server.
 * 
 * @author The OME Team
 */
public class Import extends Connect {

    /**
     * Upload an image to the server
     * @param image The image file to upload
     * @throws Exception
     */
    private void importImage(File image)
        throws Exception
    {
        ImportCallback cb = new ImportCallback();
        TransferFacility tf = gateway.getFacility(TransferFacility.class);
        tf.uploadImage(ctx, image, cb);
        
        // wait for the upload to finish
        while (!cb.isFinished()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
        }
        
    }
    
    public static void main(String[] args) {
        Import i = new Import();

        try {
            i.connect(args);
            i.importImage(new File("/home/user/image1.dv"));
        } catch (Exception e) {
        } finally {
            i.disconnect();
        }
        
    }
    
}
