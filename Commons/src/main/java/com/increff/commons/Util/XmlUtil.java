package com.increff.commons.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import static com.increff.commons.Constants.ConstantNames.PDF_BASE_ADDRESS;

public class XmlUtil {


    //Generate PDF
    public static byte[] generatePDF(Long orderId, File xml_file, StreamSource xsl_source) throws Exception {

        String path = PDF_BASE_ADDRESS+orderId+"pdf";
        File pdfFile = new File(path);
        pdfFile.createNewFile();
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // Setup a buffer to obtain the content length
        ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        OutputStream os = new FileOutputStream(pdfFile);
        // Setup FOP
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xsl_source);
        // Make sure the XSL transformation's result is piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        // Setup input
        Source src = new StreamSource(xml_file);

        // Start the transformation and rendering process
        transformer.transform(src, res);

        byte[] bytes =  out.toByteArray();
        os.write(bytes);
        os.close();
        out.close();
        out.flush();

        return bytes;

    }

    //Generate XML
    public static void generateXml(File file,Object list,Class<?> class_type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(class_type);
        Marshaller m = context.createMarshaller();
        // for pretty-print XML in JAXB
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(list, file);

    }

    public static String getDateTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }


}
