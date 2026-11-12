<#import "layout.ftlh" as layout>

<@layout.page title="Sign Up">
    <section class="hero">
        <h2>Join StudioShare</h2>
        <p>Register as a customer to get started.</p>
    </section>

    <section class="card">
        <h2>Create Your Customer Profile</h2>

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

        <#if errors??>
            <div class="error" role="alert">
                <p>Please correct the following:</p>
                <ul>
                    <#list errors as error>
                        <li>${error.defaultMessage!"Invalid input"}</li>
                    </#list>
                </ul>
            </div>
        </#if>

        <form action="/signup" method="post">
            <label for="firstName">First Name</label>
            <input
                type="text"
                id="firstName"
                name="firstName"
                value="${customer.firstName!''}"
                autocomplete="given-name"
                required
            >

            <label for="lastName">Last Name</label>
            <input
                type="text"
                id="lastName"
                name="lastName"
                value="${customer.lastName!''}"
                autocomplete="family-name"
                required
            >

            <label for="email">Email Address</label>
            <input
                type="email"
                id="email"
                name="email"
                value="${customer.email!''}"
                autocomplete="email"
                required
            >

            <label for="phone">Phone Number (optional)</label>
            <input
                type="tel"
                id="phone"
                name="phone"
                value="${customer.phone!''}"
                autocomplete="tel"
            >

            <label for="password">Password</label>
            <input
                type="password"
                id="password"
                name="password"
                minlength="8"
                autocomplete="new-password"
                aria-describedby="password-help"
                required
            >
            <p id="password-help">Use at least 8 characters.</p>

            <label for="confirmPassword">Confirm Password</label>
            <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                minlength="8"
                autocomplete="new-password"
                required
            >

            <button type="submit">Register</button>
        </form>

        <p>Already registered? <a href="/login">Log In</a>.</p>
    </section>
</@layout.page>