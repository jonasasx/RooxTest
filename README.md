# RooxTest
Example of RESTfull application with Spring Boot

## JavaDoc
https://jonasasx.github.io/RooxTest/javadoc/


## Api Reference

Authorization:
`Authorization: Bearer customerId`

**Get Customer**
----
  Returns json data about a single customer.

* **URL**

  /customer/:cid

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `cid=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"id":1,"fio":"Ionas","balance":22.00,"status":"ACTIVE","login":"ionas"}`
 
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{"timestamp":1488427413659,"status":403,"error":"Forbidden","message":"Access is denied","path":"/customer/2"}`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"timestamp":1488427456238,"status":401,"error":"Unauthorized","message":"An Authentication object was not found in the SecurityContext","path":"/customer/2"}`


















**Get Partner's Mapping**
----
  Returns json data about a single Partner's Mapping.

* **URL**

  /customer/:cid/partnerMapping/:pid

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `cid=[integer]`
   `pid=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"id":2,"partnerId":"fb7129","accountId":"0123917673012","fio":"Ionas","avatarUrl":"https://example.com/image.jpg"}`
 
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{"timestamp":1488427413659,"status":403,"error":"Forbidden","message":"Access is denied","path":"/customer/2"}`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"timestamp":1488427456238,"status":401,"error":"Unauthorized","message":"An Authentication object was not found in the SecurityContext","path":"/customer/2"}`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{"message":"Resource not found"}`












**Create Partner's Mapping**
----
  Create Partner's Mapping.

* **URL**

  /customer/:cid/partnerMapping

* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `cid=[integer]`

* **Data JSON**

   `partnerId=[string]`
   `accountId=[accountId]`
   `accountId=[fio]`
   `accountId=[avatarUrl]`

* **Success Response:**

  * **Code:** 201 <br />
    **Header:** `Location: [created entity url]`
 
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{"timestamp":1488427413659,"status":403,"error":"Forbidden","message":"Access is denied","path":"/customer/2"}`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"timestamp":1488427456238,"status":401,"error":"Unauthorized","message":"An Authentication object was not found in the SecurityContext","path":"/customer/2"}`
























**Update Partner's Mapping**
----
  Update Partner's Mapping.

* **URL**

  /customer/:cid/partnerMapping/:pid

* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
 
   `cid=[integer]`
   `pid=[integer]`

* **Data JSON**

   `partnerId=[string]`
   `accountId=[accountId]`
   `accountId=[fio]`
   `accountId=[avatarUrl]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"id":2,"partnerId":"fb7129","accountId":"0123917673012","fio":"Ionas","avatarUrl":"https://example.com/image.jpg"}`
 
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{"timestamp":1488427413659,"status":403,"error":"Forbidden","message":"Access is denied","path":"/customer/2"}`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"timestamp":1488427456238,"status":401,"error":"Unauthorized","message":"An Authentication object was not found in the SecurityContext","path":"/customer/2"}`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{"message":"Resource not found"}`









**Delete Partner's Mapping**
----
  Delete Partner's Mapping.

* **URL**

  /customer/:cid/partnerMapping/:pid

* **Method:**

  `DELETE`
  
*  **URL Params**

   **Required:**
 
   `cid=[integer]`
   `pid=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 204 <br />
 
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{"timestamp":1488427413659,"status":403,"error":"Forbidden","message":"Access is denied","path":"/customer/2"}`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"timestamp":1488427456238,"status":401,"error":"Unauthorized","message":"An Authentication object was not found in the SecurityContext","path":"/customer/2"}`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{"message":"Resource not found"}`
