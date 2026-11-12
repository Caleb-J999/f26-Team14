<#import "layout.ftlh" as layout>

<@layout.page title="Space Unavailable">
    <section class="card">
        <h2>Space Unavailable</h2>

        <div class="error" role="alert">
            ${errorMessage!"The requested space could not be found."}
        </div>

        <a class="button" href="/spaces">Browse Spaces</a>
    </section>
</@layout.page>