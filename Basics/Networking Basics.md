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
  
### Status Code 

<table>
</table>
Status Code 

Meaning 

200 

OK 

302 

Redirection to new URL 

401 

Unauthorized 

403 

Forbidden 

404 

Not found 

500 

Internal server error 

  

Response Headers 

Response Header 

Description 

Server 

The name and version of the server 

Date 

The current date 

Content-type 

The content type of the data 

Content-length 

The length of the data that follows 

  

Response Data 

The response data for the request is sent by the server. 

  

  

 

  

 

  

DNS: DNS stands for Domain naming system. The domain names such as mdn.com or espn.com is converted into Internet protocol address so that the request can be sent to the computer with that address. Using DNS, we dont have to remember the IP address to connect to other computer. 

            

           Authoritative DNS Server: This server contains the map with domain                name as key and ip address as value.  
           

           Recursive DNS Server or DNS Resolver: The DNS Resolver is a server designed to receive queries from client machines through applications such as web browsers. Typically the resolver is then responsible for making additional requests to root server, TLD server, Authoritative server (at the last) in order to satisfy the client’s DNS query. 

  

 

A user types ‘example.com’ into a web browser and the query travels into the Internet and is received by a DNS recursive resolver. 

The resolver then queries a DNS root nameserver (.). 

The root server then responds to the resolver with the address of a Top Level Domain (TLD) DNS server (such as .com or .net), which stores the information for its domains. When searching for example.com, our request is pointed toward the .com TLD. 

The resolver then makes a request to the .com TLD. 

The TLD server then responds with the IP address(es) of the domain’s nameserver (Authoritative DNS Server), example.com. 

Lastly, the recursive resolver sends a query to one of the domain’s nameserver. 

The IP address for example.com is then returned to the resolver from the nameserver. 

The DNS resolver then responds to the web browser with the IP address of the domain requested initially. 

  

TCP and UDP:  

  
