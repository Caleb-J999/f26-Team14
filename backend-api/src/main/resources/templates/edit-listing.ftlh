
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudioShare - Edit Listing</title>

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
            align-items: center;
            gap: 25px;
        }

        nav a {
            text-decoration: none;
            color: #222;
        }

        .hero {
            background-color: #123d2b;
            color: white;
            padding: 70px 60px;
        }

        .hero h2 {
            font-size: 34px;
            margin: 0 0 10px;
        }

        .form-container {
            max-width: 750px;
            margin: 45px auto;
            padding: 35px;
            background-color: white;
            border: 1px solid #ddd;
        }

        label {
            display: block;
            font-weight: bold;
        }

        input,
        textarea {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 20px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            font-family: Arial, sans-serif;
        }

        button,
        .back-button {
            display: inline-block;
            padding: 11px 20px;
            border: none;
            border-radius: 3px;
            font-size: 16px;
            text-decoration: none;
            cursor: pointer;
        }

        button {
            background-color: #2f8f4e;
            color: white;
        }

        .back-button {
            background-color: #e4e4e4;
            color: #222;
            margin-left: 8px;
        }

        .success {
            color: #2f8f4e;
            margin-bottom: 20px;
        }

        .error {
            color: #c62828;
            margin-bottom: 20px;
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
            <a href="/provider/listings">My Listings</a>
            <a href="/provider/listings/create">Create Listing</a>
        </nav>
    </header>

    <section class="hero">
        <h2>Edit Your Creative Space</h2>
        <p>Update the information for one of your existing listings.</p>
    </section>

    <main class="form-container">

        <h2>Edit Listing: ${listing.title}</h2>

        <#if error??>
            <p class="error">${error}</p>
        </#if>

        <#if success??>
            <p class="success">${success}</p>
        </#if>

        <form action="/provider/listings/${listing.serviceId?c}/edit"
              method="post">

            <label for="space-name">Space Name</label>
            <input
                type="text"
                id="space-name"
                name="title"
                value="${listing.title}"
                required
            >

            <label for="location">Location</label>
            <input
                type="text"
                id="location"
                name="location"
                value="${(listing.location)!''}"
            >

            <label for="description">Description</label>
            <textarea
                id="description"
                name="description"
                rows="5"
                required
            >${listing.description}</textarea>

            <label for="pricePerHour">Price Per Hour ($)</label>
            <input
                type="number"
                id="pricePerHour"
                name="pricePerHour"
                step="0.01"
                min="0.01"
                value="${listing.pricePerHour?c}"
                required
            >

            <label for="capacity">Capacity</label>
            <input
                type="number"
                id="capacity"
                name="capacity"
                min="1"
                step="1"
                value="${(listing.capacity?c)!''}"
            >

            <label for="imageUrl">Image URL</label>
            <input
                type="text"
                id="imageUrl"
                name="imageUrl"
                value="${(listing.imageUrl)!''}"
            >

            <button type="submit">Save Changes</button>

            <a class="back-button" href="/provider/dashboard">
                Back to Dashboard
            </a>

        </form>

    </main>

    <footer>
        <p>StudioShare - Find your space, create your vision.</p>
    </footer>

</body>
</html>
