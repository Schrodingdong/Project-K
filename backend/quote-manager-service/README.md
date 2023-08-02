# Quote Manager Service

---
## Todos
- [ ] Use pagination ??
- [ ] CRUD
  - [x] Create
  - [x] Read
  - [x] Update
  - [x] Delete
		- [x] delete one
		- [x] DEV - delete multiple
- [ ] deletion should be linked to JWT auth so that someone cant delete others quote by changing the request
	- The input of the delete function should be the JWT token and the quote to delete.
	- [ ] not only that, but all of the other CRUD operations too !!
      
## More Semantics
*Use of multiple services to do a task*
- when deleting a quote, check if the user exists or not
  - the goal is not the make the application work only, but to also make the api robust to any kind of security fault