# Service API

Prefix: `/api/v1`

## Team API

JSON:

        {
           id: 15,
           key: "atletico-mineiro",
           name: "Atlético Mineiro"
        }

Resource: `/teams`

REST Methods:

        GET /teams/{id}
        GET /teams
        GET /teams?name={cruz}&match_mode=substring
        POST /teams
        PUT /teams/:id
        DELETE /teams/:id
	
## Championship API

        {
            id: 10,
            name: "Campeonato Mineiro",
            teams: [
            	{ id: 15, name: “Cruzeiro”, _href=“http://.../api/v1/teams/15” },
                { id: 16, name: “Atlético Mineiro”, _href=“http://.../api/v1/teams/16” },
                ...
            ]
        }

Resource: `/championships`

REST methods:

        GET /championships/:id
        GET /championships
        POST /championships
        PUT /championships/:id
        DELETE /championships/:id
