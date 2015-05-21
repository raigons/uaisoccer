var frisby = require('frisby');

var team = '   &&*#$%@!)(Caçapava do    Sul  $#@!&* '

frisby.create('It should not have any existing team')
  .get('http://localhost:8080/api/teams')
  .expectStatus(204)
  .toss()

frisby.create('It should create a new team')
  .post('http://localhost:8080/api/teams',
  { name: team, id: 1 }, {json: true})
  .expectJSON({"name": team, "key": "cacapava-do-sul", "enabled": true})
  .expectStatus(201)
  .toss()

frisby.create('It should not create duplicated teams')
  .post('http://localhost:8080/api/teams',
  { name: team, id: 1 }, {json: true})
  .expectStatus(409)
  .toss()

frisby.create('It should not create duplicated teams')
  .get('http://localhost:8080/api/teams')
  .expectJSON([{"name": team, "key": "cacapava-do-sul", "enabled": true}])
  .expectStatus(200)
  .toss()

frisby.create('It should retrieve the team with ID 1')
  .get('http://localhost:8080/api/teams/1')
  .expectStatus(200)
  .expectHeaderContains('content-type', 'application/json')
  .expectJSON({"name": team, "enabled": true, "id": 1})
  .toss()

frisby.create('It should accept a delete request on teams endpoint')
  .delete('http://localhost:8080/api/teams')
  .expectStatus(405)
  .toss()

frisby.create('It should accept a put request on teams endpoint')
  .put('http://localhost:8080/api/teams')
  .expectStatus(405)
  .toss()
