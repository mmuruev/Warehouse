<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" type="image/ico" href="images/favicon.ico"/>
    <!-- Page styles -->
    <link type='text/css' href='css/demo.css' rel='stylesheet' media='screen'/>

    <!-- Contact Form CSS files -->
    <link type='text/css' href='css/basic.css' rel='stylesheet' media='screen'/>
    <title>Warehouse</title>

    <style type="text/css" title="currentStyle">
        @import "css/demo_page.css";
        @import "css/demo_table.css";

    </style>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type='text/javascript' src='js/jquery.simplemodal.js'></script>
    <script type='text/javascript' src='js/basic.js'></script>


    <script type="text/javascript" charset="utf-8">

        var openDialog = function (id) {

            $('#details').dataTable({
                "sAjaxSource":"service/detail/" + 1,
                "bDestroy":true,
                "bProcessing":true,
                "bDeferRender":true,
                "aoColumns":[
                    { "mDataProp":"lineNumber" },
                    { "mDataProp":"supplierItemCode" },
                    { "mDataProp":"itemDescription" },
                    { "mDataProp":"invoiceQuantity" },
                    { "mDataProp":"invoiceUnitNetPrice" }
                ], "fnServerData":connectPost

            });
            $("#detailsBlock").modal({
                overlayClose:true,
                opacity:70,
                overlayCss:{backgroundColor:"#eee"},
                onClose: function (dialog) { $("#detailsBlock").remove(); $.modal.close();}
            });
        }
        var connectPost = function (sSource, aoData, fnCallback) {
            $.ajax({
                "dataType":'json',
                "type":"POST",
                "url":sSource,
                "data":aoData,
                "success":fnCallback
            })
        }
        $(document).ready(function () {

            $('#header').dataTable({

                "sAjaxSource":"service/headers",
                "bProcessing":true,
                "bDeferRender":true,
                "aoColumns":[

                    {   "bSortable":false,
                        "bSearchable":false,
                        "mDataProp":"id",
                        "bUseRendered":false,
                        "fnRender":function (oObj) {
                            var id = oObj.aData[oObj.oSettings.aoColumns[oObj.iDataColumn].mDataProp]
                            return '<div id="basic-modal"><input type="button" name="' + id + '" value="Detail" class="basic" onClick="javascript:openDialog(id)"/> </div>';
                        }},
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
                ], "fnServerData":connectPost

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

    <!-- preload the images -->
    <div style='display:none'>
        <img src='images/x.png' alt=''/>
    </div>
    <div id="demo">

        <table cellpadding="0" cellspacing="0" border="0" class="display" id="header">
            <thead>
            <tr>
                <th></th>
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
            <tr>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th></th>
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

        <div id="detailsBlock" style="display:none;background-color:#ddd;">
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
    </div>
    <div class="spacer"></div>
</div>
</body>
</html>
