# References
1. https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy
2. https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

# SOP (Single Origin Policy)
Single origin policy is a security policy which prevents scripts on one page to access data (For eg., from local storage, cookies etc) in another page of different origin. (Two origins are same if protocol, host and port are same).

Note: Read <a href="../Local Storage And Cookies/README.md">Local Storage Readme</a> for clear understanding of host, port and protocol.

## What happens with embedded js, css and html?

### Javascript
JavaScript embedded from a different origin (domain) cannot access local storage or cookies set by your page's origin due to SOP. This policy ensures that scripts from one site cannot read or write data (like cookies or local storage) belonging to another site.
However, if the JavaScript is from the same origin as your page, it will have full access to local storage and cookies.
Access to Computed Styles of Embedded CSS:

### CSS
CSS, whether embedded or linked from another origin, is generally not restricted by the SOP. This means that the styles defined in external CSS files will be applied to your HTML content.
The client (browser) can compute and display styles from the CSS, regardless of its origin. However, scripts from other origins cannot programmatically access details about these styles applied to your DOM due to the SOP.


### HTML
Embedding HTML from another origin (like through an iframe) creates a separate context for that content. This embedded content is subject to the SOP, meaning it's isolated from the parent page's content.
The parent page cannot directly access the DOM of the embedded content if it's from a different origin. Similarly, the embedded content cannot access the parent page's DOM.
Communication between the parent and the embedded content can only occur through postMessage or similar mechanisms designed to safely bypass the SOP under controlled conditions.

# CORS (Cross origin resource sharing)
CORS is a mechanism which is used to bypass the restrictions of SOP.

CORS works through HTTP headers. A server sends headers that tell the browser to allow web pages from certain origins to access resources on the server.
For instance, the ```Access-Control-Allow-Origin``` header in the server's response can specify which domains are permitted to access the resource.

Flexibility: CORS provides a way for servers to declare which origins are permitted to access its resources. It allows web applications to securely integrate content and services from different origins.

## What happens when a frontend makes a request to backend which does not have CORS configured for the frontend url?
When a frontend application makes a request to a backend that does not have CORS (Cross-Origin Resource Sharing) configuration set up for the frontend's URL, the typical behavior you'll observe is as follows:

Backend Processes the Request: The backend server receives the request, processes it, and sends a response. The lack of CORS configuration on the backend does not prevent the server from processing the request. It's important to understand that CORS is a browser-enforced security feature, not a restriction at the server level.

Browser Checks for CORS Headers: After the backend sends the response, the browser that initiated the request checks for CORS headers (Access-Control-Allow-Origin) in the response.

Browser Denies or Accepts the Response:

If the Access-Control-Allow-Origin header is missing or does not match the origin of the frontend, the browser will block the response. This means that although the backend has processed the request and sent a response, the browser will not allow the frontend application to access this response.
If the CORS headers are correctly set and include the frontend's origin, the browser allows the frontend application to access the response.
