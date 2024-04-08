# Networking Basics

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

  
# DNS
DNS: DNS stands for Domain naming system. The domain names such as mdn.com or espn.com is converted into Internet protocol address so that the request can be sent to the computer with that address. Using DNS, we dont have to remember the IP address to connect to other computer. 

![DNS](images/DNS.png)

Authoritative DNS Server: This server contains the map with domain name as key and ip address as value.  
           
Recursive DNS Server or DNS Resolver: The DNS Resolver is a server designed to receive queries from client machines through applications such as web browsers. Typically the resolver is then responsible for making additional requests to root server, TLD server, Authoritative server (at the last) in order to satisfy the client’s DNS query. 

  
<ul>
  <li>
    A user types ‘example.com’ into a web browser and the query travels into the Internet and is received by a DNS recursive resolver. 
  </li>
  
  <li>
    The resolver then queries a DNS root nameserver (.). 
  </li>
  
  <li>
    The root server then responds to the resolver with the address of a Top Level Domain (TLD) DNS server (such as .com or .net), which stores the information for its domains. When searching for example.com, our request is pointed toward the .com TLD. 
  </li>

  <li>
    The resolver then makes a request to the .com TLD. 
  </li>
  
  <li>
    The TLD server then responds with the IP address(es) of the domain’s nameserver (Authoritative DNS Server), example.com. 
  </li>
  
  <li>
    Lastly, the recursive resolver sends a query to one of the domain’s nameserver. 
  </li>
  
  <li>
    The IP address for example.com is then returned to the resolver from the nameserver. 
  </li>
  
  <li>
    The DNS resolver then responds to the web browser with the IP address of the domain requested initially. 
  </li>
  
</ul>
