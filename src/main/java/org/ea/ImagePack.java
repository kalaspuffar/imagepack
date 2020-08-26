package org.ea;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.rmi.server.ExportException;

public class ImagePack {

    public static void handleImage(PDDocument doc, String filename) throws Exception {
        final int PAGE_WIDTH = 595;

        PDImageXObject pdImage = PDImageXObject.createFromFile(filename, doc);
        int imageWidth = pdImage.getWidth();
        int imageHeight = pdImage.getHeight();

        double ratio = (double) imageHeight / (double) imageWidth;
        int height = (int)(PAGE_WIDTH * ratio);

        PDPage page = new PDPage(new PDRectangle(PAGE_WIDTH, height));
        PDPageContentStream contents = new PDPageContentStream(doc, page);
        contents.drawImage(pdImage, 0, 0, PAGE_WIDTH, height);
        contents.close();

        doc.addPage(page);
    }

    public static void main(String[] args) {
        try {
            PDDocument doc = new PDDocument();
            doc.setVersion(Math.max(doc.getVersion(), 1.4f));
            for(String arg : args) {
                handleImage(doc, arg);
            }
            doc.save("image.pdf");
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}