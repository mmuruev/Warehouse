package edisoft.model.entity;

import java.math.BigDecimal;

public class DetailRow {
    public int id;
    public int lineNumber;
    public int supplierItemCode;
    public String itemDescription;
    public BigDecimal invoiceQuantity;
    public BigDecimal invoiceUnitNetPrice = BigDecimal.ZERO;
}
