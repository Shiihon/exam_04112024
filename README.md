## 3.semester Exam

**http responses :**

_Get all_

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:51:43 GMT
Content-Type: application/json
Content-Length: 767

[
{
"id": 1,
"name": "Mountain safari",
"price": 500.95,
"category": "FOREST",
"longitude": 86.925,
"latitude": 27.9881,
"start_time": [
9,
30
],
"end_time": [
15,
0
]
},
{
"id": 2,
"name": "Sunrise at Mount Fuji",
"price": 350.75,
"category": "FOREST",
"longitude": 138.7274,
"latitude": 35.3606,
"start_time": [
4,
30
],
"end_time": [
9,
0
]
},
{
"id": 3,
"name": "Kilimanjaro Wildlife Trek",
"price": 450.5,
"category": "FOREST",
"longitude": 37.3556,
"latitude": -3.0674,
"start_time": [
7,
0
],
"end_time": [
14,
0
]
},
{
"id": 4,
"name": "Patagonian Peaks Expedition",
"price": 775.25,
"category": "FOREST",
"longitude": -73.158,
"latitude": -49.273,
"start_time": [
8,
0
],
"end_time": [
19,
0
]
},
{
"id": 5,
"name": "Denali Base Camp Adventure",
"price": 620.99,
"category": "SNOW",
"longitude": -151.0074,
"latitude": 63.0695,
"start_time": [
6,
15
],
"end_time": [
18,
30
]
}
]
Response file saved.
> 2024-11-04T125143.200.json

<hr>

_Get by id_


HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:53:53 GMT
Content-Type: application/json
Content-Length: 144

{
"id": 1,
"name": "Mountain safari",
"price": 500.95,
"category": "FOREST",
"longitude": 86.925,
"latitude": 27.9881,
"start_time": [
9,
30
],
"end_time": [
15,
0
]
}
Response file saved.
> 2024-11-04T125353.200.json

<hr>

_Create trip_

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 11:54:32 GMT
Content-Type: application/json
Content-Length: 137

{
"id": 6,
"name": "TestTrip",
"price": 212.0,
"category": "SNOW",
"longitude": 12.34567,
"latitude": 54.32109,
"start_time": [
10,
0
],
"end_time": [
12,
0
]
}
Response file saved.
> 2024-11-04T125432.201.json

<hr>

_Update name_

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:55:01 GMT
Content-Type: application/json
Content-Length: 137

{
"id": 2,
"name": "NewName",
"price": 350.75,
"category": "FOREST",
"longitude": 138.7274,
"latitude": 35.3606,
"start_time": [
4,
30
],
"end_time": [
9,
0
]
}
Response file saved.
> 2024-11-04T125501.200.json

<hr>

_Delete by id_


HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:56:46 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 149ms (149 ms); Content length: 0 bytes (0 B)

<hr>

## 3.3.5
In RESTful API design, the PUT method is used to update an existing resource or create a resource at a specific URI. 
In the context of adding a guide to a trip, using PUT implies that the relationship between the guide and,
the trip is being established in a way that identifies the trip resource explicitly.

