package com.narp.ocr.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import net.sourceforge.lept4j.util.LoadLibs;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Path("/file")
public class FileController {
    
    public static void main(String[] args) {
        System.out.println("ts");
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String uploadedFileLocation = "/mnt/home/naresh/projects/ocr/backend/uploads/"
                + fileDetail.getFileName();

        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;
       File tessFolder = net.sourceforge.tess4j.util.LoadLibs.extractTessResources("/tessdata");
        File imageFile = new File(uploadedFileLocation);
        
        Tesseract instance = new Tesseract(); // JNA Interface Mapping
        instance.setDatapath(tessFolder.getAbsolutePath());
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping
        System.out.println(imageFile.toString());
        String result = "";
        try {
            result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        return Response.status(200).entity(result).build();

    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}