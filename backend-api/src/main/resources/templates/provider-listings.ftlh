<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudioShare - My Listings</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #222;
        }

        header {
            background-color: white;
            padding: 20px 60px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ddd;
        }

        header h1 {
            margin: 0;
        }

        nav {
            display: flex;
            gap: 25px;
        }

        nav a {
            text-decoration: none;
            color: #222;
        }

        .hero {
            background-color: #123d2b;
            color: white;
            padding: 60px;
        }

        .hero h2 {
            font-size: 34px;
            margin: 0 0 10px;
        }

        main {
            max-width: 950px;
            margin: 45px auto;
            padding: 0 20px;
        }

        .listing {
            background-color: white;
            border: 1px solid #ddd;
            padding: 25px;
            margin-bottom: 20px;
        }

        .listing h3 {
            margin-top: 0;
        }

        .button {
            display: inline-block;
            background-color: #2f8f4e;
            color: white;
            padding: 11px 20px;
            text-decoration: none;
            border-radius: 3px;
            margin-top: 10px;
        }

        footer {
            background-color: #123d2b;
            color: white;
            text-align: center;
            padding: 25px;
            margin-top: 50px;
        }
    </style>
</head>

<body>

    <header>
        <h1>StudioShare</h1>

        <nav>
            <a href="/provider/dashboard">Home</a>
            <a href="/provider/listings/create">Create Listing</a>
        </nav>
    </header>

    <section class="hero">
        <h2>My Listings</h2>
        <p>View and edit your existing creative spaces.</p>
    </section>

    <main>

        <h2>Existing Listings</h2>

        <#if listings?has_content>

            <#list listings as listing>

                <div class="listing">

                    <h3>${listing.title}</h3>

                    <p>
                        <strong>Location:</strong>
                        ${(listing.location)!'Not provided'}
                    </p>

                    <p>
                        <strong>Description:</strong>
                        ${listing.description}
                    </p>

                    <p>
                        <strong>Price Per Hour:</strong>
                        $${listing.pricePerHour?c}
                    </p>

                    <p>
                        <strong>Capacity:</strong>
                        ${(listing.capacity?c)!'Not provided'}
                    </p>

                    <a class="button"
                       href="/provider/listings/${listing.serviceId?c}/edit">
                        Edit Listing
                    </a>

                </div>

            </#list>

        <#else>

            <p>You have no listings yet.</p>

            <a class="button" href="/provider/listings/create">
                Create Your First Listing
            </a>

        </#if>

    </main>

    <footer>
        <p>StudioShare - Find your space, create your vision.</p>
    </footer>

</body>
</html>