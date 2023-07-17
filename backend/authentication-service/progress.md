# Authentication Service Progress

---
- [x] User Registration
  - [x] Password Encryption with BCrypt
- [ ] User Login
  - [x] Login Logic
  - [x] if the token is valid, bypass login
  - [x] JWT Token Generation
  - [x] JWT Token Validation
  - [x] Logout Logic
  - [x] JWT Token Blacklisting
- [ ] Configuring Dev and Prod configuration for separate profiles
  - [x] Use an in-memory database for dev profile
- [ ] The endpoints `register` and `login` and `validate-jwt` should be accessible without authentication
- [ ] The endpoints `logout` should be accessible only if authenticated
