package ui.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PDFGenerator {
    private static final Logger logger = Logger.getLogger(PDFGenerator.class);
    public static void generatePDF(List<String> screenshotsLocation, String pdfFileName) throws IOException, DocumentException {
    	if(screenshotsLocation.size()>0){
        Document document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
        PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));

        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();

        document.open();

        for ( int i=0; i<screenshotsLocation.size(); i++ )
        {
            Image img = Image.getInstance(screenshotsLocation.get(i).toString());
            img.scaleToFit(documentWidth, documentHeight);
            document.add(img);
        }
        document.close();
        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Done making pdfs");
    	}
    }
}
