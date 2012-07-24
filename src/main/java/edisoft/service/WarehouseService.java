package edisoft.service;

import edisoft.model.entity.DetailRow;
import edisoft.model.entity.HeaderRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import edisoft.model.DataAccess;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
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
        LOGGER.info("{} IN: request headers",
                new Object[]{new Date()});
        List<HeaderRow> headers = new LinkedList<>();
        try {
            headers = dataAccess.fetchAllHeaders();
        } catch (EmptyResultDataAccessException exception) {
            LOGGER.info("{} OUT: not found headers",
                    new Object[]{new Date()});
        }
        Map<String, List<HeaderRow>> responder = new HashMap<>();
        responder.put("aaData", headers);
        return responder;
    }

    @POST
    @Path("details")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<DetailRow>> getDetails() {
        LOGGER.info("{} IN: request details",
                new Object[]{new Date()});
        List<DetailRow> detailRows = new ArrayList<>(1);
        DetailRow detailRow;
        try {
            detailRow = dataAccess.getDetailById(0);
            detailRows.add(detailRow);
        } catch (EmptyResultDataAccessException exception) {
            LOGGER.info("{} OUT: not found details",
                    new Object[]{new Date()});
        }

        Map<String, List<DetailRow>> responder = new HashMap<>();
        responder.put("aaData", detailRows);
        return responder;
    }
}
