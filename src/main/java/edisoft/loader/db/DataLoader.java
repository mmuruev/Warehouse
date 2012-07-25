package edisoft.loader.db;

import edisoft.model.DataAccess;
import edisoft.loader.parser.InvoiceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.*;
import java.io.*;
import java.util.Date;

@Component
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
    @Autowired
    private DataAccess dataAccess;

    private Transformer transformer;


    public DataLoader() throws TransformerConfigurationException {
        transformer = TransformerFactory.newInstance().newTransformer();
    }

    public void save(final String xmlInput) {
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
            commitToDB(invoice);
        } catch (RuntimeException exception) {
            String errorMessage = "Can't commit XML in database";
            LOGGER.error("{}" + errorMessage,new Date());
            LOGGER.debug("{}" + errorMessage, new Date(),exception);
        }
    }

    @Transactional
    private void commitToDB(final InvoiceReader invoice) {
        try {
            dataAccess.setHeader(invoice.headerRow);
        } catch (DataAccessException exception) {
            LOGGER.error("{} Error occurred while save HEADER\n {}",
                    new Object[]{new Date(), exception});
            throw new RuntimeException(exception);
        }
        LOGGER.info("{} HEADER have been updated successful",
                new Object[]{new Date()});

        try {
            dataAccess.setDetail(invoice.detailRow);
        } catch (DataAccessException exception) {
            LOGGER.error("{} Error occurred while save DETAIL.\n Rollback HEADER update.\n {}",
                    new Object[]{new Date(), exception});
            throw new RuntimeException(exception);
        }
        LOGGER.info("{} DETAILS have been updated successful",
                new Object[]{new Date()});
    }

    public void setTransformer(final Transformer transformer) {
        this.transformer = transformer;
    }

}
