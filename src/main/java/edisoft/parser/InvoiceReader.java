package edisoft.parser;

import edisoft.model.entity.DetailRow;
import edisoft.model.entity.HeaderRow;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceReader {
    private static final Charset ENCODING = Charset.forName("UTF-8");
    private static final XPath PATH = XPathFactory.newInstance().newXPath();

    private static final String DATE_FORMAT = "yyyy.MM.dd'T'HH:mm:ss";

    public final HeaderRow headerRow = new HeaderRow();
    public final DetailRow detailRow = new DetailRow();

    private final Party sender;
    private final Party receiver;


    private static class Party {
        public final BigDecimal iln;
        public final BigDecimal codeByReceiver;
        public final String name;
        public final String address;

        private Party(final BigDecimal iln, final BigDecimal codeByReceiver, final String name, final String address) {
            this.iln = iln;
            this.codeByReceiver = codeByReceiver;
            this.name = name;
            this.address = address;
        }
    }

    public InvoiceReader(final InputStream stream, final Transformer transformer) {
        final Document document = emptyDocument();
        readInvoiceDocument(stream, transformer, document);

        final Node documentSettings = documentNode(document, "/Document/DocumentSettings");
        headerRow.documentType = nodeString(documentSettings, "DocumentType");
        headerRow.receiverSystemType = nodeString(documentSettings, "ReceiverSystemType");
        headerRow.documentNumber = nodeInteger(documentSettings, "DocumentNumber");
        headerRow.documentDate1 = nodeDate(documentSettings, "DocumentDate1");
        headerRow.documentDate2 = nodeDate(documentSettings, "DocumentDate2");

        sender = documentParty(documentSettings, "Sender");
        headerRow.senderILN = sender.iln;
        headerRow.senderCodeByReceiver = sender.codeByReceiver;
        headerRow.senderName = sender.name;
        headerRow.senderAddress = sender.address;

        receiver = documentParty(documentSettings, "Receiver");
        headerRow.receiverILN = receiver.iln;
        headerRow.receiverCodeByReceiver = receiver.codeByReceiver;
        headerRow.receiverName = receiver.name;
        headerRow.receiverAddress = receiver.address;

        final Node lineItem = documentNode(document, "/Document/Document-Invoice/Invoice-Lines/Line/Line-Item");
        detailRow.lineNumber = nodeInteger(lineItem, "LineNumber");
        detailRow.supplierItemCode = nodeInteger(lineItem, "SupplierItemCode");
        detailRow.itemDescription = nodeString(lineItem, "ItemDescription");
        detailRow.invoiceQuantity = nodeInteger(lineItem, "InvoiceQuantity");
        detailRow.invoiceUnitNetPrice = nodeBigDecimal(lineItem, "InvoiceUnitNetPrice");
    }

    public static Transformer newTransformer(final InputStream stream) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, ENCODING))) {
            return TransformerFactory.newInstance().newTransformer(new StreamSource(reader));
        } catch (final IOException exception) {
            throw new RuntimeException("Can't read XML transformation", exception);
        } catch (final TransformerConfigurationException exception) {
            throw new RuntimeException("Can't create XML transformation", exception);
        }
    }

    private static Document emptyDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (final ParserConfigurationException exception) {
            throw new RuntimeException("Can't initialize XML parsing", exception);
        }
    }

    private static void readInvoiceDocument(final InputStream stream, final Transformer transformer,
                                            final Document document) {
        try (final BufferedReader invoiceReader = new BufferedReader(new InputStreamReader(stream, ENCODING))) {
            transformer.transform(new StreamSource(invoiceReader), new DOMResult(document));
        } catch (final IOException exception) {
            throw new RuntimeException("Can't read XML document", exception);
        } catch (final TransformerException exception) {
            throw new RuntimeException("Can't transform XML document", exception);
        }
    }

    private static Node documentNode(final Node node, final String path) {
        return (Node) documentObject(node, path, XPathConstants.NODE);
    }

    private static String nodeString(final Node node, final String path) {

        return (String) documentObject(node, path, XPathConstants.STRING);
    }

    private static Date nodeDate(final Node node, final String path) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(nodeString(node, path));
        } catch (ParseException exception) {
            throw new RuntimeException("Can't read date format " + DATE_FORMAT, exception);
        }
    }

    private static BigDecimal nodeBigDecimal(final Node node, final String path) {
        return new BigDecimal(nodeString(node, path));
    }

    private static int nodeInteger(final Node node, final String path) {
        return Integer.parseInt(nodeString(node, path));
    }

    private static Object documentObject(final Node node, final String path, final QName xType) {
        try {
            return PATH.evaluate(path, node, xType);
        } catch (final XPathExpressionException exception) {
            throw new IllegalArgumentException("Invalid path to document node: " + path, exception);
        }
    }


    private static Party documentParty(final Node node, final String path) {
        final Node party = documentNode(node, path);
        return new Party(
                nodeBigDecimal(party, "ILN"),
                nodeBigDecimal(party, "CodeByReceiver"),
                nodeString(party, "Name"),
                nodeString(party, "Address"));
    }
}