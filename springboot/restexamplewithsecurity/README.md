# Superhero Service

## Usage:
All the responses will have the json data or the description of what happended

### List all Superheroes
**Definition**
`GET /superheroes`
**Response**
- `200 OK` on success
```json
[
    {
        "id": 1,
        "name": "superman",
        "power": "strength"
    },
    {
        "id": 2,
        "name": "batman",
        "power": "ninja"
    }
]
```
 
### Deleting all Superheroes
**Definition**
`DELETE /superheroes`
**Response**
- `200 OK` on success
```json
[
    {
        "id": 1,
        "name": "superman",
        "power": "strength"
    },
    {
        "id": 2,
        "name": "batman",
        "power": "ninja"
    }
]
```

### Adding a new Superhero
**Definition**
`POST /superheroes`
**Response**
- `200 OK` on success
**Arguments**
- `"id":integer` a unique identifier for the superhero
- `"name":string` name for the superhero
- `"power":string` power of the superhero 
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```

### Updating a new Superhero
**Definition**
`PUT /superheroes`
**Response**
- `200 OK` on success
**Arguments**
- `"id":integer` a unique identifier for the superhero
- `"name":string` name for the superhero
- `"power":string` power of the superhero 
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```
 
### Lookup a specific Superhero details
**Definition**
`GET /superheroes/<id>`
**Response**
- `404 Not Found` if the superhero does not exist
- `200 OK` on success
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```
 
 ### Delete a specific Superhero
**Definition**
`DELETE /superheroes/<id>`
**Response**
- `200 OK` on success
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```

 
 





