<!DOCTYPE html>
<html>
    <head>
        <title>OVO Payment Barcode</title>
        <style>
            body {
                margin: 0;
                width: 100vw;
                height: 100vh;
            }
            .container {
                display: flex;
                flex-direction: column;
            }
            .content {
                align-self: center;
                margin-top: 20px;
            }
        </style>
    </head>
    <body class="container">
        <div class="content">
            <p>Scan this barcode to pay.</p>
            <img src="ovo/qr?c=${code}" />
        </div>
    </body>
</html>
