# Quote Manager Service

---
## Todos
- [ ] Use pagination ??
- [ ] CRUD
  - [ ] Create
  - [ ] Read
  - [ ] Update
  - [x] Delete
    - [ ] delete one
    - [ ] DEV - delete multiple
    - [ ] deletion should be linked to JWT auth so that someone cant delete others quote by changing the request
      - The input of the delete function should be the JWT token and the quote to delete.
## More Semantics
*Use of multiple services to do a task*
- when deleting a quote, check if the user exists or not
  - the goal is not the make the application work only, but to also make the api robust to any kind of security fault