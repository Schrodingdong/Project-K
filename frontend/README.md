# Project K Frontend

---
## Auth
- registration : localhost:8888/auth/register
- login : localhost:8888/auth/login
  - [x] Check if jwt exists and compare its subject with the subject saved in cookies
- [ ] logout : localhost:8888/auth/logout
- [ ] Put an Auth wrapper around all the other pages 
  - [ ] Use of react context
## Misc
- fallback routes 
  - https://www.robinwieruch.de/react-router-authentication/
- Alerts
  - [x] Component
  - [ ] Animation
    - [x] Start
    - [ ] End
  - [ ] Stacking
- Authentication
  - [ ] Auth wrapper
  - [x] Registration error alerts
    - [x] matching password 
    - [x] invalid credentials
    - [x] user already exists
  - [ ] Login error alerts
    - [ ] invalid username/password