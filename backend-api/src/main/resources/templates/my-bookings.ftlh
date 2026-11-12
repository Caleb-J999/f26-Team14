<#import "layout.ftlh" as layout>

<#macro bookingCard booking>
    <article class="card">
        <h3>${booking.title}</h3>
        <p><strong>Booking ID:</strong> ${booking.id?c}</p>
        <p><strong>Start:</strong> ${booking.startTime}</p>
        <p><strong>End:</strong> ${booking.endTime}</p>
        <p><strong>Status:</strong> ${booking.status}</p>
        <p><strong>Total:</strong> ${booking.total}</p>
    </article>
</#macro>

<@layout.page title="My Bookings">
    <section class="hero">
        <h2>My Bookings</h2>
        <p>View your upcoming and previous reservations.</p>
    </section>

    <h2>Upcoming and In Progress</h2>

    <section class="space-grid" aria-label="Upcoming bookings">
        <#list upcomingBookings as booking>
            <@bookingCard booking=booking />
        <#else>
            <div class="card">
                <p>You have no upcoming bookings.</p>
            </div>
        </#list>
    </section>

    <h2>Past Bookings</h2>

    <section class="space-grid" aria-label="Past bookings">
        <#list pastBookings as booking>
            <@bookingCard booking=booking />
        <#else>
            <div class="card">
                <p>You have no past bookings.</p>
            </div>
        </#list>
    </section>

    <p>
        <a class="button" href="/spaces">Browse Spaces</a>
    </p>
</@layout.page>