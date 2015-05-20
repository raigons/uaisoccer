var frisby = require('frisby');

frisby.create('Creates a new team')
  .post('http://localhost:8080/api/teams',
  { name: "new team", id: 1 }, {json: true})
  .expectJSON({"name": "new team", "enabled": true})
  .expectStatus(201)
  .toss()

frisby.create('Retrieves a team with ID 1')
  .get('http://localhost:8080/api/teams/1')
  .expectStatus(200)
  .expectHeaderContains('content-type', 'application/json')
  .expectJSON({"name": "new team", "enabled": true, "id": 1})
  .toss()

frisby.create('Makes a invalid request on teams endpoint')
  .get('http://localhost:8080/api/teams')
  .expectStatus(405)
  .toss()
