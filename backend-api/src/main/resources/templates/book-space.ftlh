<#import "layout.ftlh" as layout>

<@layout.page title="Book a Space">
    <section class="hero">
        <h2>Book ${space.title}</h2>
        <p>${space.location!"Location not provided"}</p>
    </section>

    <section class="card">
        <h2>Choose Your Booking Time</h2>

        <p>${space.description}</p>

        <p>
            <strong>Hourly Rate:</strong>
            $${space.pricePerHour?string["0.00"]}
        </p>

        <#if successMessage??>
            <div class="success" role="status">
                ${successMessage}
            </div>
        </#if>

        <#if errorMessage??>
            <div class="error" role="alert">
                ${errorMessage}
            </div>
        </#if>

        <form action="/spaces/${space.serviceId?c}/book" method="post">
            <label for="startTime">Start Date and Time</label>
            <input
                type="datetime-local"
                id="startTime"
                name="startTime"
                value="${startTime!''}"
                required
            >

            <label for="endTime">End Date and Time</label>
            <input
                type="datetime-local"
                id="endTime"
                name="endTime"
                value="${endTime!''}"
                required
            >

            <p>
                The total is calculated from the hourly rate and
                booking duration. Times use the studio's local time.
            </p>

            <button type="submit">Submit Booking</button>
        </form>

        <p><a href="/spaces">Back to Browse Spaces</a></p>
    </section>
</@layout.page>