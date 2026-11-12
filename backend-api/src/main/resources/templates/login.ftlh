<#import "layout.ftlh" as layout>

<@layout.page title="Log In">
    <section class="card">
        <h2>Customer Login</h2>

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

        <form action="/login" method="post">
            <label for="email">Email Address</label>
            <input
                type="email"
                id="email"
                name="email"
                value="${email!''}"
                autocomplete="username"
                required
            >

            <label for="password">Password</label>
            <input
                type="password"
                id="password"
                name="password"
                autocomplete="current-password"
                required
            >

            <button type="submit">Log In</button>
        </form>

        <p>New customer? <a href="/signup">Register here</a>.</p>
    </section>
</@layout.page>