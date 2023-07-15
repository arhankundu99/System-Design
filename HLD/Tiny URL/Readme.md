# Tiny URL System Design
<b>What is Tiny URL?</b>
Tiny URL is a system which does two things:
<ul>
  <li>Convert a long url to short url. </li>
  <li>When user enters the short url, the user gets redirected to the long url. </li>
</ul>

Some of the examples of this system are:
<ul>
  <li>https://tinyurl.com</li>
  <li>https://bitly.com</li>
</ul>

## Approach

### Step 1 (Functional and non functional requirements)
<ul>
  <li>The first step when solving a system design problem would be to list down the <b>functional</b> and <b>non-functional</b> requirements.
  <br></br>
  <table>
    <tr>
      <th>Functional requirements</th>
      <th>Non Functional requirements</th>
    </tr>
    <tr>
      <td>Functional requirements define the functionality of the system. (For eg., the tiny url system has two functional requirements here: a) Converting the long url to short url. b) Redirecting to the long url when user enters a short url. </td>
      <td>Non functional requirements define the <b>performance attribute</b> of the system. (For eg., the tiny url system should have low latency, high availibility etc). Non functional requirements helps us to give the user a good experience. </td>
    </tr>
    <tr>
      <td>Functional requirements concentrate on product features. </td>
      <td>Non Functional requirements concentrate on product attributes. </td>
  </table>
  
  <li>The functional and non functional requirements are: </li>
  <br></br>
  <table>
    <tr>
      <th>Functional requirements</th>
      <th>Non Functional requirements</th>
    </tr>
    <tr>
      <td>Convert a long url to short url</td>
      <td>Low Latency</td>
    </tr>
    <tr>
      <td>Redirect to the long url when user enters a short url</td>
      <td>High Availaibility (The system should rarely go down)</td>
    </tr>
  </table>
  
  <li>Confirm these requirements with your interviewer. </li>
</ul>

### Step 2 (Capacity Estimation)

<ul>
  <li>What should be the length of our short url? To answer this question, we have to do capacity estimations.</li>  
  <li>Ask your interviewer about the traffic that we are getting per seconds and for how much duration the system should store the short urls.</li>
  <br></br>
  
  ```
  Let's say the number of requests per second that the system is getting is X.
  And the duration for which we have to store the urls is Y years.
  
  So that means our system should be able to store Z = X * 60 * 60 * 24 * 365 * Y unique urls.
  
  So to support this, we have to calculate the number of characters that our short url should have. 
  Ask your interviewer about the characters that we can use for the short url.
  
  Let's say we are allowed to use characters [a-z][A-Z][0-9], a total of 62 characters. And Let's say our short url has a size of L.
  
  So that means 62^L >= Z => L = Math.ceiling(log(Z) / log(62))
  ```
  
</ul>

### Step 3 (Design)

<b>Design Iteration 1</b>
![Tiny URL Design Iteration 1](https://github.com/arhankundu99/System-Design/blob/main/Tiny%20URL/images/Tiny%20URL%20Design%201.png)

Note that there will be multiple instances of the short url service and the database.

<b> Design Iteration 2 </b>
![Tiny URL Design Iteration 2](https://github.com/arhankundu99/System-Design/blob/main/Tiny%20URL/images/Tiny%20URL%20Design%20Iteration%202.png)

<b> Now how do we generate the unique short url? </b>
We have to make sure that the instances of the short url service do not generate the same short url (Collisions should not happen).

One simple solution for this would be to check if the generated short url exists in the database or not. If it does not exist, then the generated url is a unique short url. And if the url exists, then the service has to again generate a short url and continue to do so until it generates an unique url. But this solution is not really efficient.

So what we need is a predictable way to generate a short url knowing that there would be no collisions at all.

One very simple way to implement this is to use ONE of the features of Redis (Which is a cache system) which returns an unique number whenever a request for unique number comes to redis. Redis is basically an in memory store used to cache the data in key, value pairs.

<b>Design Iteration 3</b>
![Tiny URL Design Iteration 3](https://github.com/arhankundu99/System-Design/blob/main/Tiny%20URL/images/Tiny%20URL%20Design%20Iteration%203.png)

Now this design would work fine, but there are some <b>major problems</b>.
<ul>
  <li>
    Firstly, every instance of url service is connected to redis. So redis has huge amount of load.
  </li>
  <li>
    Remember, in any system design, we should not create a <b>single point of failure</b>. Here redis becomes a single point of failure. If redis fails, then our url service will go down.
  </li>
</ul>
  
We can argue that we can keep multiple redis servers to reduce the load.

<b>Design Iteration 4</b>
![Tiny URL Design Iteration 4](https://github.com/arhankundu99/System-Design/blob/main/Tiny%20URL/images/Tine%20URL%20Design%20Iteration%204.png)

So if we keep multiple redis servers, then also we would have a major problem.

One very simple solution is to give a series to a redis and another series to another redis. (For eg., give a series of even numbers to one redix and a series of odd numbers to other redis). But what if we need to add a third redis?
Now it becomes complicated to manage the series.

<b>Design Iteration 5</b>
![Tiny URL Design Iteration 5](https://github.com/arhankundu99/System-Design/blob/main/Tiny%20URL/images/Tine%20URL%20Design%20Iteration%205.png)

Now one of the problems with this design is that let's say a url service got a range 1 - 1000 assigned and the service after serving some requests stopped working. So there is no way to track the unused tokens in the range 1 - 1000. But even if we lose some of the tokens every day, it wont matter much because our system has the capacity to store massive number of urls.

Now which database to use to store the urls?
A SQL Database for a massive number of URLs would start to give some problems. We can possibly shard the SQL database and make it work. But NoSQL Databases are much easier to scale than SQL Database. So we can use a NoSQL Database here.

So this approach is good enough from the functional and non functional requirements' point of view. But this system does not give us any metrics like the location from where most of the requests are coming, what kind of urls are being converted etc., If we have the geographic locations, we can assign more servers in that location.

So for analytics, what we can do is, each time a request comes to the url service, the request comes with a lot of attributes, like the origin header (which says about the platform from which the request is sent), or source IP Address (Which is an unique Intenet Protocol Address for our computer to connect to the internet).

What we can do is, we can use <b>Kafka</b> for analytics. So each time a request comes, we will <b>asynchronously</b> (So that our main functions do not get blocked) send the request to kafka. But sending the request to kafka everytime a request hits the url service is also not very much optimised. 

So what we can do is, we can maintain a queue in the service to store the requests and everytime the queue size becomes equal to a threshold size, we can push all the requests (asynchronously) to <b>Kafka</b>.
