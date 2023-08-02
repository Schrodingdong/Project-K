# API GATEWAY SERVICE

---
Api Gateway service is the entry point for all the requests coming from the client. It is responsible for routing the requests to the appropriate service. It also handles the authentication and authorization of the requests.

Gateway port : `8888`

The authorization is handled via **Filters**

## JWT Validation Filter
- [ ] Work on the routes 
- [ ] AMQP Messaging
  - [x] Created the `auth-validation-req-q` queue
  - [x] Linked it to `auth-validation-req-exchange` Exchange
  - [x] Listening to the `auth-validation-res-q` queue
- [ ] Handle if the Authorization is not in the request
	For now it give us internal server error, and an error in the console
- [ ] Handle when the JWT is not validated
	Concretely speaking we should redirect to the login page in the front