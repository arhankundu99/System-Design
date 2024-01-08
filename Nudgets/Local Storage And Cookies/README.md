# References: 
1. https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies
2. https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage
3. https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy

# Basics
An url is a combination of protocol, host and port.

(For eg., https://www.google.com: Here protocol is https, host is www.google.com, port is 443). 

Note: if port is not mentioned, then by default for https, port is 443 and for http, port is 80.

And in the same example, domain is google.com. Domain is basically a part (subset) of host. The "com" in google.com is called Top Level Domain (TLD) and "google" in google.com is Second Level Domain (SLD). And "www" is the Sub Domain

Domain is basically the combination of TLD + SLD.

Then what is the difference between host and domain? As I said, domain is a part of host. Host is more specific. For eg., in www.google.com, google.com is the domain and www.google.com is the host.

Another example: In https://mail.google.com/dir1/resource1, https is the protocol, mail.google.com is the host, google.com is the domain, mail is the subdomain, port is 443 by default, and dir1/resource1 is the path.

Two URLs are said to be of same origin, if their protocol, host and port are SAME. (Note: Their paths may be different)



# Local storage and cookies
Both local storage and cookies are used to store data by the browser.

1. When browser stores data in local storage or cookies, browser creates files locally in our pc which has this data. (The location of the file depends upon the browser and the os that we are using).
2. The data in the cookies are sent through <b>every http request</b>, but in case of local storage, we can decide, which data to send in which request.
3. Local storage (~5 MB) has more storage limit than cookies (~4 KB). The storage in cookies is very less because the data in cookies are sent in every http request. And the choice of size limits is somewhat arbitrary.
4. Local storage has no lifetime, But cookies has lifetime (If not set, they have a default lifetime), after which the browser will clear the cookies.
5. Local storage data and cookies data are accessible by javascript code, But in cookies, we can set 2 flags:
    <ul>
    <li><b>Secure (Which only sends the cookie data through HTTPS protocol. Its never sent on HTTP protocol, except on localhost)</b></li>
    <li><b>HttpOnly (If this flag is set, then the cookie data is not accessible via js code)</b></li>
    </ul>
6. In private browsing, Cookies and Local Storage data both get cleared after we close the browser.
7. Data stored in local storage is strictly bound to the protocol and domain that set it. This means that data stored by subdomain.example.com will not be accessible to another.example.com. Local storage does not provide a native mechanism for sharing data across subdomains.
8. Cookies can be configured in different ways. By default, a cookie set by subdomain.example.com is not accessible to another.example.com. However, cookies can be set to be accessible across subdomains of a base domain. This is done by setting the Domain attribute of the cookie to .example.com (note the leading dot). In this case, the cookie will be accessible to all subdomains under example.com, like subdomain.example.com, another.example.com, etc.

The last point is important because using cookies, we can prevent XSS attacks. 

