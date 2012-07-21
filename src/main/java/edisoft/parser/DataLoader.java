package edisoft.parser;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public class DataLoader {

    private static  Templates templates;

    public DataLoader(String xsltFilePath){
        TransformerFactory tf = TransformerFactory.newInstance();
        if (!tf.getFeature(SAXTransformerFactory.FEATURE)) {
            throw new RuntimeException(
                    "Did not find a SAX-compatible TransformerFactory.");
        }
        SAXTransformerFactory stf = (SAXTransformerFactory) tf;
        StreamSource stylesheetSrc = new StreamSource(
                getClass().getResourceAsStream(xsltFilePath));
        try {
            templates = stf.newTemplates(stylesheetSrc);
        } catch (TransformerConfigurationException e) {
            System.err.println("Can't create template: ");
            e.printStackTrace();
        }
    }

    public StreamResult transformer(StreamSource sourceStream) throws TransformerException {
        Transformer t = templates.newTransformer();
        StreamResult xmlResult = new StreamResult();
        t.transform(sourceStream, xmlResult);
        return xmlResult;
    }

    public Document parse(InputStream xmlInputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlInputStream);
    }


}
