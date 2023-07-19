# Authentication Service Progress

---
## Messaging 
- [ ] AMQP Messaging
  - [x] Listening to the `auth-validation-req-q` queue
  - [x] Created the `auth-validation-res-q` queue
  - [x] Link it to `auth-validation-res-exchange` Exchange
## Authentication
- [x] User Registration
  - [x] Password Encryption with BCrypt
- [x] User Login
  - [x] Login Logic
  - [x] if the token is valid, bypass login
    - [ ] ... after verifying the credentials with the token issuer
  - [x] JWT Token Generation
  - [x] JWT Token Validation
  - [x] Logout Logic
  - [x] JWT Token Blacklisting
- [ ] Link with other services
  - [ ] User manager service
  - [ ] Api Gateway
## Misc
- [x] Configuring Dev and Prod configuration for separate profiles
  - [x] Use an in-memory database for dev profile
- [x] The endpoints `register` and `login` and `logout` and `validate-jwt` should be accessible without authentication
