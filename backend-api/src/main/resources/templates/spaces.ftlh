<#import "layout.ftlh" as layout>

<@layout.page title="Browse Spaces">
    <section class="hero">
        <h2>Find Your Creative Space</h2>
        <p>Explore studios, rehearsal rooms, and maker spaces.</p>
    </section>

    <section class="card">
        <h2>Search Spaces</h2>

        <form action="/spaces" method="get">
            <label for="query">Space Name or Location</label>
            <input
                type="search"
                id="query"
                name="query"
                value="${query!''}"
                placeholder="Enter a space name or city"
            >

            <button type="submit">Search Spaces</button>
            <a class="button" href="/spaces">Show All</a>
        </form>
    </section>

    <h2>Creative Spaces</h2>

    <section class="space-grid" aria-label="Search results">
        <#list spaces as space>
            <article class="card">
                <h3>${space.title}</h3>

                <p>
                    <strong>Location:</strong>
                    ${space.location!"Location not provided"}
                </p>

                <p>${space.description}</p>

                <p>
                    <strong>Price:</strong>
                    $${space.pricePerHour?string["0.00"]} / hour
                </p>

                <#if space.capacity??>
                    <p>
                        <strong>Capacity:</strong>
                        ${space.capacity?c} guests
                    </p>
                </#if>
            </article>
        <#else>
            <div class="card">
                <p>No active spaces match your search.</p>
            </div>
        </#list>
    </section>
</@layout.page>