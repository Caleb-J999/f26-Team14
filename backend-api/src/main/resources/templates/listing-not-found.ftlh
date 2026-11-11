
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listing Not Found - StudioShare</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
            padding: 80px 20px;
            color: #222;
        }

        .container {
            background-color: white;
            max-width: 550px;
            margin: auto;
            padding: 40px;
            border: 1px solid #ddd;
        }

        h1 {
            color: #123d2b;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 20px;
            background-color: #2f8f4e;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Listing Not Found</h1>

        <p>The listing you requested could not be found.</p>

        <#if error??>
            <p>${error}</p>
        </#if>

        <a href="/provider/listings">Back to My Listings</a>
    </div>
</body>
</html>
