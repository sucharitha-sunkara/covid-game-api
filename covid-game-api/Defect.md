Defect1: API is returning 201-success when an empty username is passed as below

Request URL: https://supervillain.herokuapp.com/v1/user
Request Body: {"score":0,"username":"\"\""} 

Defect 2:API returns 503 error instead of 400 bad request for both POST and put

Request URL: https://supervillain.herokuapp.com/v1/user
Request Body: {"username":"TestUser11233"}
Expected -  400 Bad Request  Actual - 503 Service Unavailable