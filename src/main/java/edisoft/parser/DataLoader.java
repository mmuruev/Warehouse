package edisoft.parser;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DataLoader {

    private static Templates templates;

    public DataLoader(String xsltFilePath) {
        initTemplate(xsltFilePath);
    }

    private void initTemplate(String xsltFilePath) {
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

}
