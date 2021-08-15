## QuasarOperation

### Elaborado por David Galeano

Este proyecto se encuentra desarrollado bajo JAVA 8 con SpringBoot 2.5.3 y maven-2.

El proyecto se encuentra bajo la siguiente **IP pública** de un cluster ECS en AWS:
**http://3.143.223.207:8080/**

La ruta para probar con swagger es:
**http://3.143.223.207:8080/swagger-ui.html#**

### Posiciones de los satelites

Los satelites inicialmente se encuentran en las siguientes posiciones:

```
Kenobi [-500, -200]
Skywalker [100,-100]
Sato [500,100]
```
*Tener en cuenta que estas posiciones pueden ser modificadas en el archivo Satelite.json en el directorio raiz.*

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
    "position": {
        "x": 0,
        "y": 0
    },
    "message": "message"
}
```

#### Request ejemplo

*Basado en las posiciones iniciales indicadas en este apartado*

```
{
   "satellites":[
      {
         "name":"kenobi",
         "distance":921.95,
         "message":[
            "este",
            "",
            "",
            "mensaje",
            ""
         ]
      },
      {
         "name":"skywalker",
         "distance":424.26,
         "message":[
            "",
            "es",
            "",
            "",
            "secreto"
         ]
      },
      {
         "name":"sato",
         "distance":509.9,
         "message":[
            "este",
            "",
            "un",
            "",
            ""
         ]
      }
   ]
}
```

#### Response ejemplo (esperado)
```
{
    "position": {
        "x": 400.0,
        "y": -400.0
    },
    "message": "este es un mensaje secreto"
}
```

### 2. /topsecret_split/{satelite_name}
Servicio **POST** el cual recibirá información y actualizara la misma por satélite.

#### Funcionamiento
- Se podrá recibir información de 1 satélite.
- Si el satélite no existe, se omitirá la información enviada.

#### Endpoint
**http://3.143.223.207:8080/topsecret_split/{satelite_name}**

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

#### Request ejemplo(s)

*Basado en las posiciones iniciales indicadas en este apartado*


*Peticion con Kenobi*

http://3.143.223.207:8080/topsecret_split/kenobi
```
{
    "distance":921.95,
    "message":[
       "este",
       "",
       "",
       "mensaje",
       ""
    ]
}
```

*Peticion con Skywalker*

http://3.143.223.207:8080/topsecret_split/skywalker
```
{
    "distance":424.26,
    "message":[
       "",
       "es",
       "",
       "",
       "secreto"
    ]
}
```

*Peticion con Sato*

http://3.143.223.207:8080/topsecret_split/sato
```
{
    "name":"sato",
    "distance":509.9,
    "message":[
       "este",
       "",
       "un",
       "",
       ""
    ]
}
```

#### Response ejemplo (esperado)
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

#### Request ejemplo(s)

*Basado en las posiciones iniciales indicadas en este apartado*

```
N/A
```

#### Response ejemplo (esperado)
```
{
    "position": {
        "x": 400.0,
        "y": -400.0
    },
    "message": "este es un mensaje secreto"
}
```
