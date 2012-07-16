<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/media/images/favicon.ico"/>
    <title>Warehouse</title>

    <style type="text/css" title="currentStyle">
        @import "css/demo_page.css";
        @import "css/demo_table.css";
        @import "css/dataTables.scroller.css";
    </style>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript" src="js/dataTables.scroller.min.js"></script>

    <script type="text/javascript" charset="utf-8">
        $(document).ready(function () {
             var connectPost =  function (sSource, aoData, fnCallback) {
                                $.ajax({
                                    "dataType":'json',
                                    "type":"POST",
                                    "url":sSource,
                                    "data":aoData,
                                    "success":fnCallback
                                })
                            }
            $('#header').dataTable({

                "sAjaxSource":"service/headers",
                "bProcessing":true,
                "bDeferRender":true,
                "aoColumns":[
                    { "mDataProp":"documentType" },
                    { "mDataProp":"receiverSystemType" },
                    { "mDataProp":"documentNumber" },
                    { "mDataProp":"documentDate1" },
                    { "mDataProp":"documentDate2" },

                    { "mDataProp":"senderILN" },
                    { "mDataProp":"senderCodeByReceiver" },
                    { "mDataProp":"senderName" },
                    { "mDataProp":"senderAddress" },

                    { "mDataProp":"receiverILN" },
                    { "mDataProp":"receiverCodeByReceiver" },
                    { "mDataProp":"receiverName" },
                    { "mDataProp":"receiverAddress" }
                ],"fnServerData": connectPost

            });
            $('#details').dataTable({
                           "sAjaxSource":"service/details",
                           "bProcessing":true,
                           "bDeferRender":true,
                           "aoColumns":[
                               { "mDataProp":"lineNumber" },
                               { "mDataProp":"supplierItemCode" },
                               { "mDataProp":"itemDescription" },
                               { "mDataProp":"invoiceQuantity" },
                               { "mDataProp":"invoiceUnitNetPrice" }
                           ],"fnServerData": connectPost

                       });
        });
    </script>


</head>
<body id="dt_example">
<div id="container" style="width:980px">
    <div class="full_width big">
        Warehouse
    </div>

    <h1>Header</h1>

    <div id="demo">
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="header">
            <thead>
            <tr>
                <th>DocumentType</th>
                <th>ReceiverSystemType</th>
                <th>DocumentNumber</th>
                <th>DocumentDate1</th>
                <th>DocumentDate2</th>

                <th>SenderILN</th>
                <th>SenderCodeByReceiver</th>
                <th>SenderName</th>
                <th>SenderAddres</th>

                <th>ReceiverILN</th>
                <th>ReceiverCodeByReceiver</th>
                <th>ReceiverName</th>
                <th>ReceiverAddres</th>

            </tr>
            </thead>
            <tbody>

            </tbody>
            <tfoot>
            <tr>
                <th>DocumentType</th>
                <th>ReceiverSystemType</th>
                <th>DocumentNumber</th>
                <th>DocumentDate1</th>
                <th>DocumentDate2</th>

                <th>SenderILN</th>
                <th>SenderCodeByReceiver</th>
                <th>SenderName</th>
                <th>SenderAddres</th>

                <th>ReceiverILN</th>
                <th>ReceiverCodeByReceiver</th>
                <th>ReceiverName</th>
                <th>ReceiverAddres</th>

            </tr>
            </tfoot>
        </table>

        <h1>Details</h1>

        <table cellpadding="0" cellspacing="0" border="0" class="display" id="details">
            <thead>
            <tr>
                <th>LineNumber</th>
                <th>SupplierItemCode</th>
                <th>ItemDescription</th>
                <th>InvoiceQuantity</th>
                <th>InvoiceUnitNetPrice</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
            <tfoot>
            <tr>
                <th>LineNumber</th>
                <th>SupplierItemCode</th>
                <th>ItemDescription</th>
                <th>InvoiceQuantity</th>
                <th>InvoiceUnitNetPrice</th>
            </tr>
            </tfoot>
        </table>
    </div>
    <div class="spacer"></div>
</div>
</body>
</html>
