package edisoft.parser;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import edisoft.model.DataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Date;

public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
    private final DataAccess dataAccess;

    private Transformer transformer;

    @Autowired
    public DataLoader(final DataAccess dataAccess) throws TransformerConfigurationException{
        this.dataAccess = dataAccess;
        transformer = TransformerFactory.newInstance().newTransformer();
    }

    public void save(String xmlInput) {
        final InvoiceReader invoice;

        try {
            invoice = new InvoiceReader(new FileInputStream(xmlInput), transformer);
        } catch (FileNotFoundException exception) {
            LOGGER.error("{} File {} not found\n {}",
                    new Object[]{new Date(), xmlInput, exception});
            return;
        } catch (RuntimeException exception) {
            LOGGER.error("{} File {} reading error\n {}",
                    new Object[]{new Date(), xmlInput, exception});
            return;
        }
        LOGGER.info("{} File {} was read successful",
                new Object[]{new Date(), xmlInput});

        try {
            dataAccess.setHeader(invoice.headerRow);
        } catch (DataAccessException exception) {
            LOGGER.error("{} Error occurred while save HEADER\n {}",
                    new Object[]{new Date(), exception});
        }
        LOGGER.info("{} HEADER have been updated successful",
                new Object[]{new Date()});

        try {
            dataAccess.setDetail(invoice.detailRow);
        } catch (DataAccessException exception) {
            LOGGER.error("{} Error occurred while save DETAIL\n {}",
                    new Object[]{new Date(), exception});
        }
        LOGGER.info("{} HEADER have been updated successful",
                new Object[]{new Date()});
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

}
