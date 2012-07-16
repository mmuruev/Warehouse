package erisoft.model;

import erisoft.model.entity.DetailRow;
import erisoft.model.entity.HeaderRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DataAccess {
    private static final HeaderMapper HEADER_MAPPER = new HeaderMapper();
    private static final DetailMapper DETAIL_MAPPER = new DetailMapper();
    private static final String HEADER_FIELDS = " DOCUMENT_TYPE, RECEIVER_SYSTEM_TYPE, DOCUMENT_NUMBER, DOCUMENT_DATE_1, DOCUMENT_DATE_2," +
            " SENDER_ILN, SENDER_CODE_BY_RECEIVER, SENDER_NAME, SENDER_ADDRESS," +
            " RECEIVER_ILN, RECEIVER_CODE_BY_RECEIVER, RECEIVER_NAME, RECEIVER_ADDRESS";
    private final JdbcTemplate database;

    @Autowired
    public DataAccess(final DataSource dataSource) {
        database = new JdbcTemplate(dataSource);
    }

    public List<HeaderRow> fetchAllHeaders() {
        return database.query("SELECT * FROM HEADERS", HEADER_MAPPER);
    }

    public List<DetailRow> fetchAllDetails() {
        return database.query("SELECT * FROM DETAILS", DETAIL_MAPPER);
    }

    public void setHeader(final HeaderRow header) {
        database.update("INSERT INTO HEADERS (" + HEADER_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                header.documentType, header.receiverSystemType, header.documentNumber, header.documentDate1, header.documentDate2,
                header.senderILN, header.senderCodeByReceiver, header.senderName, header.senderAddress,
                header.receiverILN, header.receiverCodeByReceiver, header.receiverName, header.receiverAddress);
    }

    public void setDetail(final DetailRow detail) {
        database.update("INSERT INTO DETAILS (LINE_NUMBER, SUPPLIER_ITEM_CODE, ITEM_DESCRIPTION, INVOICE_QUANTITY," +
                " INVOICE_UNIT_NET_PRICE ) VALUES (?, ?, ?, ?, ?)",
                detail.lineNumber, detail.supplierItemCode, detail.itemDescription, detail.invoiceQuantity,
                detail.invoiceUnitNetPrice);
    }

    private static class HeaderMapper implements RowMapper<HeaderRow> {
        @Override
        public HeaderRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final HeaderRow header = new HeaderRow();
            header.id = rs.getInt("ID");
            header.documentType = rs.getString("DOCUMENT_TYPE");
            header.receiverAddress = rs.getString("RECEIVER_SYSTEM_TYPE");
            header.documentNumber = rs.getInt("DOCUMENT_NUMBER");
            header.documentDate1 = rs.getDate("DOCUMENT_DATE_1");
            header.documentDate2 = rs.getDate("DOCUMENT_DATE_2");

            header.senderILN = rs.getBigDecimal("SENDER_ILN");
            header.senderCodeByReceiver = rs.getBigDecimal("SENDER_CODE_BY_RECEIVER");
            header.senderName = rs.getString("SENDER_NAME");
            header.senderAddress = rs.getString("SENDER_ADDRESS");

            header.receiverILN = rs.getBigDecimal("RECEIVER_ILN");
            header.receiverCodeByReceiver =  rs.getBigDecimal("RECEIVER_CODE_BY_RECEIVER");
            header.receiverName = rs.getString("RECEIVER_NAME");
            header.receiverAddress = rs.getString("RECEIVER_ADDRESS");

            return header;
        }
    }

    private static class DetailMapper implements RowMapper<DetailRow> {
        @Override
        public DetailRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final DetailRow detail = new DetailRow();
            detail.id = rs.getInt("ID");
            detail.lineNumber = rs.getInt("LINE_NUMBER");
            detail.supplierItemCode = rs.getInt("SUPPLIER_ITEM_CODE");
            detail.itemDescription = rs.getString("ITEM_DESCRIPTION");
            detail.invoiceQuantity = rs.getInt("INVOICE_QUANTITY");
            detail.invoiceUnitNetPrice = rs.getBigDecimal("INVOICE_UNIT_NET_PRICE");
            return detail;
        }
    }
}
