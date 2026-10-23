## Customer API

Implemented by Caleb James.

Base URL for local testing: `http://localhost:8080`

| Method | Endpoint | Purpose | Success | Errors |
|---|---|---|---|---|
| POST | `/api/customers` | Create a customer profile | 201 Created | 400 invalid input |
| GET | `/api/customers` | Get all customers | 200 OK | — |
| GET | `/api/customers/{id}` | Get a customer by ID | 200 OK | 404 not found |
| PUT | `/api/customers/{id}` | Update a customer profile | 200 OK | 400 invalid input; 404 not found |
| DELETE | `/api/customers/{id}` | Delete a customer | 204 No Content | 404 not found |
| GET | `/api/bookings?customerId={id}` | Get bookings for a customer | 200 OK | — |

It returns an empty array when no bookings match the customer ID.

### Create Customer

Send a POST request to `/api/customers` with this JSON body:

```json
{
  "firstName": "Caleb",
  "lastName": "James",
  "email": "caleb@example.com",
  "phone": "3365550100",
  "profileImageUrl": ""
}
```

The server generates the customer ID and timestamps. First name, last name, and email are required. Email must have a valid format.

### Update Customer

Send a PUT request to `/api/customers/{id}` with this JSON body:

```json
{
  "firstName": "Caleb",
  "lastName": "James",
  "email": "caleb@example.com",
  "phone": "3365550199",
  "profileImageUrl": ""
}
```

### Customer Booking History

Send a GET request to:

```text
/api/bookings?customerId=1
```

Each matching booking includes its status, start and end times, total price, studio listing, and provider.

Cancellation through `DELETE /api/bookings/{id}` changes the booking status to `CANCELLED` and retains the booking in the customer's history.

### Manual Verification

Tested locally using Bruno and a Neon PostgreSQL development database:

- Created a customer: 201 Created.
- Retrieved and updated a customer: 200 OK.
- Retrieved a missing customer: 404 Not Found.
- Submitted an invalid customer: 400 Bad Request.
- Deleted a test customer: 204 No Content; subsequent retrieval returned 404.
- Retrieved customer 1's booking with studio and provider details.
- Queried a customer ID with no matching bookings and received an empty array.
- Cancelled a booking and verified its status changed to CANCELLED.
- Restarted the app and verified the cancelled booking remained available.