### Instructions without security
#### url: localhost:8080

#####  To add new record, method=POST
  ###### url: localhost:8080/person
  Content-Type   application/json
  
  Request body content format
  ```
  {
    "persons":[
      {
        "first_name":"Name1",
        "last_name":"L_name1",
        "age":"age1",
        "favourite_colour":"Colour1"
      }
    ]
  }
  ```
  #####  To read all records, method=GET
  ###### url: localhost:8080/person
  Content-Type   application/json
  NA
  
  #####  To update record, method=PUT
  ###### url: localhost:8080/person/{full_name}
  Where full_name is first_name+last_name of the existing record (Without space in between).
  
  Content-Type   application/json
  
  Request body updated data content format 
  ```
  {
    "persons":[
      {
        "first_name":"Name1",
        "last_name":"L_name1",
        "age":"age1",
        "favourite_colour":"Colour1"
      }
    ]
  }
  ```
  
  #####  To remove record, method=DELETE
  
  ###### 1) url: localhost:8080/person
  
  Content-Type   application/json
  
  Request body containing data to be removed content format 
  ```
  {
    "persons":[
      {
        "first_name":"Name1",
        "last_name":"L_name1",
        "age":"age1",
        "favourite_colour":"Colour1"
      }
    ]
  }
  ```
  ###### 2) url: localhost:8080/person/{full_name}
  Where full_name is first_name+last_name of the existing record (Without space in between).
  
  Content-Type   application/json
  NA
  
  
  
