<#import "layout.ftlh" as layout>

<@layout.page title="Book a Space">
    <section class="hero">
        <h2>Book ${space.title}</h2>
        <p>${space.location!"Location not provided"}</p>
    </section>

    <section class="card">
        <h2>Offered Availability</h2>

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

        <#if availabilityWindows?has_content>
            <ul>
                <#list availabilityWindows as window>
                    <li>${window.start} through ${window.end}</li>
                </#list>
            </ul>

            <p>
                Choose a future time entirely within one offered window.
                Existing reservations are checked when you submit.
                Times use the studio's local time.
            </p>

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
                    Your total is calculated from the hourly rate
                    and booking duration.
                </p>

                <button type="submit">Submit Booking</button>
            </form>
        <#else>
            <p>No upcoming availability has been offered for this space.</p>
        </#if>

        <p><a href="/my-bookings">View My Bookings</a></p>
        <p><a href="/spaces">Back to Browse Spaces</a></p>
    </section>
</@layout.page>