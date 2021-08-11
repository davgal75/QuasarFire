## QuasarOperation

### Elaborado por David Galeano

Este proyecto se encuentra desarrollado bajo JAVA 8 con SpringBoot 2.5.3 y maven-2.

El proyecto se encuentra bajo la siguiente **IP pública** de un cluster ECS en AWS:
**http://3.143.223.207:8080/**

La ruta para probar con swagger es:
**http://3.143.223.207:8080/swagger-ui.html#**

## Servicios

Se desarrollo 3 servicios en los cuales se enviará la información correspondiente a cada satélite con respecto a un objeto distante. Estos responderán con un mensaje y posición aproximada del mismo.  

### 1. /topsecret/
Servicio **POST** el cual intentara determinar la posición de la nave enemiga por medio de la información suministrada.

#### Funcionamiento
- Se podrá recibir información de 1 o más satélites, y se recopilara información únicamente de los satélites en operación.
- Se pueden configurar N cantidad de satélites y se puede recibir de igual modo N cantidad de información de satélites.
- En caso de que no se ingrese información de al menos 3 satélites, no se podrá calcular la posición de la nave y se arrojara error.
- Si el mensaje de todos los satélites no tiene la misma longitud, se retornara error.

#### Endpoint
**http://3.143.223.207:8080/topsecret**

#### Request
```
{
    "satellites" : [
        {
            "name" : "name",
            "distance" : 0,
            "message" : ["message", ...]
        }
    ], ...
}
```

#### Response
```
{
    "message": "message",
    "position": {
        "x": 0,
        "y": 0
    }
}
```

### 2. /topsecret_split/{satelite_name}
Servicio **POST** el cual recibirá información y actualizara la misma por satélite.

#### Funcionamiento
- Se podrá recibir información de 1 satélite.
- Si el satélite no existe, se omitirá la información enviada.

#### Endpoint
**http://3.143.223.207:8080/topsecret_split{satelite_name}**

#### Request
```
{
    "distance": 0,
    "message":["mensaje" ...]
}
```

#### Response
```
Codigo de status OK (200)
```

### 3. /topsecret_split
Servicio **GET** el cual intentara determinar la posición de la nave enemiga por medio de la información suministrada con anterioridad en los satélites, por medio del servicio anteriormente mencionado.

#### Funcionamiento
- En caso de que no se ingrese información de al menos 3 satélites con anterioridad, no se podrá calcular la posición de la nave y se arrojara error.
- Si el mensaje de todos los satélites no tiene la misma longitud, se retornara error.

#### Endpoint
**http://3.143.223.207:8080/topsecret_split**

#### Request
```
N/A
```

#### Response
```
{
    "message": "message",
    "position": {
        "x": 0,
        "y": 0
    }
}
```
