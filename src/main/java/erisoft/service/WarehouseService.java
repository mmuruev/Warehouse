package erisoft.service;

import erisoft.model.entity.DetailRow;
import erisoft.model.entity.HeaderRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import erisoft.model.DataAccess;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Component
@Scope("request")
@Path("/")
public class WarehouseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseService.class);
    private final DataAccess dataAccess;

    @Autowired
    public WarehouseService(final DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @POST
    @Path("headers")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<HeaderRow>> getHeaders() {
        LOGGER.info("IN: request headers {}",
                new Object[]{System.currentTimeMillis()});
        List<HeaderRow> headers = new LinkedList<>();
        try {
            headers = dataAccess.fetchAllHeaders();
        } catch (EmptyResultDataAccessException exception) {

            LOGGER.info("OUT: not found headers {} ",
                    new Object[]{System.currentTimeMillis()});

        }
        HeaderRow header = new HeaderRow();
        header.receiverAddress = "my streat";
        header.receiverILN =  new BigDecimal("123454323");
        header.documentDate1 = new Date();
        header.documentDate2 = new Date();
        header.senderName = "Vasja";
        dataAccess.setHeader(header);


        Map<String, List<HeaderRow>> responder = new HashMap<>();
        responder.put("aaData", headers);
        return responder;
    }

    @POST
    @Path("details")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<DetailRow>> getDetails() {
        LOGGER.info("IN: request details {}",
                new Object[]{System.currentTimeMillis()});
        List<DetailRow> headers = new LinkedList<>();
        try {
            headers = dataAccess.fetchAllDetails();
        } catch (EmptyResultDataAccessException exception) {

            LOGGER.info("OUT: not found details {} ",
                    new Object[]{System.currentTimeMillis()});

        }

        DetailRow detail = new DetailRow();
        detail.invoiceUnitNetPrice = new BigDecimal(1234565432);
        detail.supplierItemCode = 1;
        detail.lineNumber = 50;
        detail.itemDescription =" hz cho eto";
        detail.invoiceQuantity =1000;
        dataAccess.setDetail(detail);

        Map<String, List<DetailRow>> responder = new HashMap<>();
        responder.put("aaData", headers);
        return responder;
    }
}
