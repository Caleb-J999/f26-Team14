<#ftl output_format="HTML" auto_esc=true>
<#macro page title>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudioShare - ${title}</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <header>
        <h1>StudioShare</h1>
        <nav aria-label="Main navigation">
            <a href="/">Home</a>
            <a href="/spaces">Browse Spaces</a>
            <a href="/signup">Sign Up</a>
        </nav>
    </header>

    <main>
        <#nested>
    </main>

    <footer>
        <p>StudioShare - Find your space, create your vision.</p>
    </footer>
</body>
</html>
</#macro>