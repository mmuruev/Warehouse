package erisoft.model.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@XmlRootElement
public class HeaderRow {
    public int id;
    public String documentType;
    public String receiverSystemType;
    public int documentNumber;
    public Date documentDate1;
    public Date documentDate2;

    public BigDecimal senderILN;
    public BigDecimal senderCodeByReceiver;
    public String senderName;
    public String senderAddress;

    public BigDecimal receiverILN;
    public BigDecimal receiverCodeByReceiver;
    public String receiverName;
    public String receiverAddress;
}
