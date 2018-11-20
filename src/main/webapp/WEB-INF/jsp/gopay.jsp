<!DOCTYPE html>
<html>
    <head>
        <title>GO-PAY Payment QR Code</title>
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
            <p>Scan this QR code to pay.</p>
            <img src="gopay/qr?c=${code}" />
        </div>
    </body>
</html>
