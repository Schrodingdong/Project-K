# Neo4j Database


---
to run the neo4j instance, execute the following docker command :
````shell
docker run -d --publish=7474:7474 --publish=7687:7687 --name=neo4j neo4j
````

# TODOs

---
- [x] Following logic
  - [x] Follow users
  - [x] can't follow yourself
  - [x] Unfollow users
  - [x] Get users followers
  - [x] Get users following
- [ ] clean the exceptions in the service