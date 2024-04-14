# HTTP Basics

## Protocol 
A protocol is a set of rules of communication between two computers. Communications between two devices require that the devices agree on the format of data that is being exchanged. 

## HTTP 
HTTP stands for <b>Hypertext transfer protocol</b> which is basically a set of rules for communication between web browser (client) and server. 
HTTP is based on request/response cycle.  

## Requests 
A request from client to a server includes the following: 
<ul>
  <li>Request Method</li>
  <li>Request Header</li>
  <li>Request Data</li>
</ul>


### Request Methods 
There are various methods for a request:  

<ul>
  <li> GET (To get specified document) </li>
  <li> POST (To post data in server) </li>
  <li> PUT (To modify a complete resource in server) </li>
  <li> PATCH (To modify some parts of resource in server) </li>
  <li> HEAD (To get only the header information from the document) </li>
</ul>
  
  
### Request Header: 
<table>
  <tr>
    <td><b>Request Header</b></td>
    <td><b>Description</b></td>
  </tr>
  <tr>
    <td>Accept </td>
    <td>The format of the response the client can accept (Json, xml, html, image etc)</td>
  </tr>
  <tr>
    <td>Authorization</td>
    <td>Used if the user wants to authenticate with the server </td>
  </tr>
  <tr>
    <td>User-agent</td>
    <td>The information about the client (Name and version of client software)</td>
  </tr>
  <tr>
    <td>Host</td>
    <td>The host and port number of the server to which the request is being sent.</td>
  </tr>
</table>

### Request Data: 
The data to be sent with request methods like POST, PATCH, PUT requests. In case of request methods like GET, HEAD etc, the data is empty. 

## Responses: 

The responses from the server includes the following: 
<ul>
  <li>Status Code</li> 
  <li>Response header</li> 
  <li>Response data</li> 
</ul>
  
  
## Status Code 


<table>
  <tr>
    <td>
      Status Code 
    </td>
    <td>
      Meaning
    </td>
  </tr>
   
  <tr>
    <td>
      200
    </td>
    <td>
      OK
    </td>
  </tr>
    
  <tr>
    <td>
      302
    </td>
    <td>
      Redirection to New URL
    </td>
  </tr>
    
  <tr>
    <td>
      401 
    </td>
    <td>
      Unauthorized
    </td>
  </tr>
    
  <tr>
    <td>
      404 
    </td>
    <td>
      Forbidden
    </td>
  </tr>
    
  <tr>
    <td>
      500
    </td>
    <td>
      Server Error
    </td>
  </tr>
    
</table>


## Response Headers 


<table>
  
  <tr>
    <td>
      Response Header 
    </td>
    <td>
      Description
    </td>
  </tr>
  
  <tr>
    <td>
      Server
    </td>
    <td>
      The name and version of the server 
    </td>
  </tr>
  
  <tr>
    <td>
      Date 
    </td>
    <td>
      The current date
    </td>
  </tr>
  
  <tr>
    <td>
      Content-type 
    </td>
    <td>
      The content type of the data 
    </td>
  </tr>
  
  <tr>
    <td>
      Content-length 
    </td>
    <td>
      The length of the data that follows 
    </td>
  </tr>
  
</table>
  
## Response Data 
The response data for the request is sent by the server. 
