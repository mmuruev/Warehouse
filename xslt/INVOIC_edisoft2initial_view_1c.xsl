<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:date="http://exslt.org/dates-and-times"
 xmlns:exsl="http://exslt.org/common"
 extension-element-prefixes="date exsl">
	
	<xsl:output method="xml" encoding="Windows-1251" indent="yes"/>
	
	<xsl:template match="/">
		<Document>
			<DocumentSettings>
		 		<DocumentType>INVOIC</DocumentType> 
		 		<ReceiverSystemType>AS</ReceiverSystemType>
				<ReceiverSystemModule>AS.ert</ReceiverSystemModule> 
				<DocumentNumber><xsl:value-of select="Document-Invoice/Invoice-Header/InvoiceNumber"/></DocumentNumber>
				<DocumentDate1><xsl:value-of select="Document-Invoice/Invoice-Header/InvoiceDate"/></DocumentDate1>
				<DocumentDate2><xsl:value-of select="Document-Invoice/Invoice-Header/InvoicePaymentDueDate"/></DocumentDate2>
				<Sender>
					<ILN><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/ILN"/></ILN>
					<CodeByReceiver><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/UtilizationRegisterNumber"/></CodeByReceiver>
					<ReceiverSystemLookUp>Firmad</ReceiverSystemLookUp>
					<ReceiverSystemLookUpField>РегНомер</ReceiverSystemLookUpField>
					<Name><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/Name"/></Name>
					<Address><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/StreetAndNumber"/></Address>
				</Sender>
				<Receiver>
					<ILN><xsl:value-of select="Document-Invoice/Invoice-Parties/Seller/ILN"/></ILN>
					<CodeByReceiver><xsl:value-of select="Document-Invoice/Invoice-Parties/Seller/UtilizationRegisterNumber"/></CodeByReceiver>
					<ReceiverSystemLookUp>Firmad</ReceiverSystemLookUp>
					<ReceiverSystemLookUpField>РегНомер</ReceiverSystemLookUpField>
					<Name><xsl:value-of select="Document-Invoice/Invoice-Parties/Seller/Name"/></Name>
					<Address><xsl:value-of select="Document-Invoice/Invoice-Parties/Seller/StreetAndNumber"/></Address>
				</Receiver>
				<Delivery>
					<ILN><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/ILN"/></ILN>
					<CodeByReceiver><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/UtilizationRegisterNumber"/></CodeByReceiver>
					<ReceiverSystemLookUp>Firmad</ReceiverSystemLookUp>
					<ReceiverSystemLookUpField>РегНомер</ReceiverSystemLookUpField>
					<Name><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/Name"/></Name>
					<Address><xsl:value-of select="Document-Invoice/Invoice-Parties/Buyer/StreetAndNumber"/></Address>
				</Delivery>
		 
				<Document-Lines>
					<LinesTag>Document-Invoice/Invoice-Lines/Line</LinesTag>
					<ItemCodeBySenderTag>Line-Item/BuyerItemCode</ItemCodeBySenderTag>
					<ItemCodeByReceiverTag>Line-Item/SupplierItemCode</ItemCodeByReceiverTag>
					<ReceiverItemSystemLookUp>Nomenklatuur</ReceiverItemSystemLookUp>
					<!-- <ReceiverItemSystemLookUpTag>Line-Delivery/SellerLocationCode</ReceiverItemSystemLookUpTag>--> <!-- Таг в XML, в котором находится название справочника для товаров, если разные товары в заказе подставляются из разных справочников (Товары/Услуги) в одном документе. Если этот таг пустой, то справочник определяется по ReceiverItemSystemLookUp --> 
					<ReceiverItemSystemLookUpField>АртКод</ReceiverItemSystemLookUpField>
					<ItemDescriptionTag>Line-Item/ItemDescription</ItemDescriptionTag>
					<ItemQuantityTag>Line-Item/InvoiceQuantity</ItemQuantityTag>
					<ItemUnitOfMeasureTag>Line-Item/UnitOfMeasure</ItemUnitOfMeasureTag>
					<ReceiverUnitOfMeasureSystemLookUp>Ьhik</ReceiverUnitOfMeasureSystemLookUp>
					<ReceiverUnitOfMeasureSystemLookUpTag>UnitOfMeasure</ReceiverUnitOfMeasureSystemLookUpTag> 
					<ReceiverUnitOfMeasureSystemLookUpField>Наим</ReceiverUnitOfMeasureSystemLookUpField>
					<ItemNetPriceTag>Line-Item/fNetPrice</ItemNetPriceTag>
					<!--<ItemGrossPriceTag>Line-Item/OrderedUnitGrossPrice</ItemGrossPriceTag>--> <!-- Таг в XML для цены 1 единицы товара без с налогом (брутто)--> 
				</Document-Lines>
			</DocumentSettings>	
			<xsl:apply-templates select="node()|@*"/>
		</Document>
	</xsl:template>
	
	<xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*" />
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>
