package com.narp.ocr.upload;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractExample
{
    /*public static void main(String[] args) throws Exception{
        System.out.println("asfd");
    }*/
    public static void main(String[] args) {
        File imageFile = new File("/mnt/home/naresh/projects/ocr/samples-img/12points.png");
        Tesseract instance = new Tesseract(); // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
