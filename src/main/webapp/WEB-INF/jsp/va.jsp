<!DOCTYPE html>
<html>
    <head>
        <title>Virtual Account Payment Console</title>
        <style>
            ul.form {
                list-style: none;
                padding: 0;

                display: table;
                border-spacing: 0.2em;
            }

            ul.form > li {
                margin: .2em 0;

                display: table-row;
            }

            ul.form > li > * {
                display: table-cell;
            }
        </style>
    </head>
    <body>
        <h1>Virtual Account Payment Console</h1>

        <form method="POST">
            <ul class="form">
                <li>
                    <label for="accountNumber">Account Number:</label>
                    <input type="text" name="accountNumber" />
                </li>
                <li>
                    <label for="amount">Amount:</label>
                    <input type="text" name="amount" />
                </li>
            </ul>

            <button type="submit">Submit</button>
        </form>
    </body>
</html>
