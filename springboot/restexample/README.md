# Superhero Service

## requirement:
- maven
## Authentication credentials:
- username: `rohith`
- password: `123456`

## Usage:
- open the project in Spring Tool Suite (or)
- run this command in the terminal `mvn spring-boot:run` to run the spring boot application with embedded tomcat server
- The application will run in `localhost` at port `8080`
- Use the credentials to access the endpoints
- To stop the server run `mvn -stop` or press `control + c` to gracefully exit

All the responses will have the json data or the description of what happended

### List all Superheroes:
**Definition**
- '*_GET /superheroes_*'

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

### Adding a new Superhero:
**Definition**
- '*_POST /superheroes_*'

**Arguments**
- `"id":integer` a unique identifier for the superhero
- `"name":string` name for the superhero
- `"power":string` power of the superhero 

**Example data**
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```

**Response**
- `200 OK` on success

```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```

### Updating a new Superhero:
**Definition**
- '*_PUT /superheroes_*'

**Arguments**
- `"id":integer` a unique identifier for the superhero
- `"name":string` name for the superhero
- `"power":string` power of the superhero 

**Example data**
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```
 
**Response**
- `200 OK` on success

```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```
 
### Lookup a specific Superhero details:
**Definition**
- '*_GET /superheroes/{id}_*'

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
 
### Delete a specific Superhero:
**Definition**
- '*_DELETE /superheroes/{id}_*'

**Response**
- `200 OK` on success
```json
 {
        "id": 6,
        "name": "birdman",
        "power": "fly"
 }
 ```

 
 





