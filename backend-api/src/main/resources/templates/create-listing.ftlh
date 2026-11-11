<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudioShare - Create Listing</title>

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
            align-items: center;
        }

        nav a {
            text-decoration: none;
            color: #222;
        }

        .hero {
            background-color: #123d2b;
            color: white;
            padding: 70px 60px;
            background-image: url("/images/studio2.jpg");
            background-size: cover;
            background-position: center;
        }

        .hero h2 {
            margin: 0 0 10px;
            font-size: 34px;
        }

        .form-container {
            max-width: 750px;
            margin: 45px auto;
            background-color: white;
            border: 1px solid #ddd;
            padding: 35px;
        }

        .form-container h2 {
            margin-top: 0;
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
            text-decoration: none;
            font-size: 16px;
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

        .error {
            background-color: #ffe5e5;
            color: #9b1c1c;
            padding: 12px;
            margin-bottom: 20px;
        }

        .success {
            background-color: #e4f5e9;
            color: #2f8f4e;
            padding: 12px;
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
            <a href="/provider/listings/create">List Your Space</a>
        </nav>
    </header>

    <section class="hero">
        <h2>List Your Creative Space</h2>
        <p>Create a new listing for customers to discover and book.</p>
    </section>

    <main class="form-container">

        <h2>Create Listing</h2>

        <#if error??>
            <div class="error">${error}</div>
        </#if>

        <#if success??>
            <div class="success">${success}</div>
        </#if>

        <form action="/provider/listings/create" method="post">

            <label for="providerId">Provider ID</label>
            <input
                type="number"
                id="providerId"
                name="providerId"
                min="1"
                required
            >

            <label for="title">Space Name</label>
            <input
                type="text"
                id="title"
                name="title"
                placeholder="Example: Downtown Recording Studio"
                required
            >

            <label for="location">Location</label>
            <input
                type="text"
                id="location"
                name="location"
                placeholder="Example: Greensboro, NC"
            >

            <label for="pricePerHour">Price Per Hour ($)</label>
            <input
                type="number"
                id="pricePerHour"
                name="pricePerHour"
                min="0.01"
                step="0.01"
                required
            >

            <label for="capacity">Capacity</label>
            <input
                type="number"
                id="capacity"
                name="capacity"
                min="1"
            >

            <label for="imageUrl">Image URL</label>
            <input
                type="text"
                id="imageUrl"
                name="imageUrl"
                placeholder="Example: /images/studio2.jpg"
            >

            <label for="description">Description</label>
            <textarea
                id="description"
                name="description"
                rows="5"
                placeholder="Describe your creative space..."
                required
            ></textarea>

            <button type="submit">Create Listing</button>

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