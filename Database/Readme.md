# Databases
In a system design, How good our design is and how it can scale, depends very much on the choice of the database.
Now databases generally do not impact our <b>functional requirements</b>. So to satisfy our functional requirements, we can use any database that we want. Normally the <b>non functional requirements</b> are the ones that are impacted by our choice of database.

Based on the choice of the database, it would impact how well our design can follow the non functional requirements.

<b>
  Normally the choice of database depends on the following three factors:
  <ul>
    <li>
      Whether that data that we have is structured or not.
    </li>
    <li>
      The query patterns.
    </li>
    <li>
      The amount of scale the database needs to handle.
    </li>
  </ul>
</b>

## Database vs Data Store vs Data Warehouse
<table>
<tr>
  <th>
    Database
  </th>
  <th>
    Data Store
  </th>
  <th>
    Data Warehouse
  </th>
</tr>
<tr>
  <td>
    Databases are the storage systems which can be <b>queried</b>. Database comes under Data store.
  </td>
  <td>
    Data store is any system which can be used as storage and may or may not be queriable. For eg., Amazon S3, which is a <b>blob storage</b> is a data store and not database because it stores files (for eg., images and videos) but we can't query those files. Databases fall under Data stores.
  </td>
  <td>
    Data warehouses contain large amount of <b>historical and current data</b> which is mainly used for <b>analytics</b>.
  </td>
</tr>
</table>

## Caching Solutions
So whichever system that we are designing, we would definitely have to use some caching solution there.
<br></br>
There are a lot of use cases for caching:
<ul>
  <li>
    Let's say we are querying a database and we do not want to query the database for the same kind of response. So there we can store the response in a caching solution and whenever we get a request, then we can look in the cache and get the response.
  </li>
  <li>
    Or lets say we are using a service and it takes a lot of time to get the response from the service, In that case also we can use the caching solutions
  </li>
</ul>

Generally how caching works is we have the data stored in <b>key, value</b> pairs

Some of the mostly used caching solutions are:
<ul>
  <li>
    Redis (Stands for Remote Dictionary Server)
  </li>
  <li>
    Memcached
  </li>
  <li>
    etcd
  </li>
  <li>
    Hazelcast
  </li>
</ul>

<b>
  For caching, The above caching solution stores the data in key, value pairs in-memory (in RAM) and does not write to disk.
</b>

Refer these links:
<ul>
<li>
https://aws.amazon.com/redis
</li>
<li>
  https://developer.redis.com/explore/what-is-redis/
</li>
<li>
  https://redis.com/blog/redis-cache-vs-redis-primary-database-in-90-seconds
</li>
</ul>

<b>NOTE that redis can also be used as a primary database which is NoSQL.</b>

## File Storage Solutions
If we need to store images or videos or any kinds of file, then we use these solutions. 
<br></br>
For eg., the product images on Amazon, or the videos on Netflix. 
For these kinds of storge, we use something called as <b>Blob Storage</b>.

Some of the most commonly used blob storage solution is <b>Amazon S3</b>.
And with any blob storage solutions, we have to use <b>CDN</b> which stands for <b>Content Delivery Network</b>.

<b>What is a CDN?</b>
CDN stands for Content Delivery Network. CDN is basically a group of servers distributed across the globe which helps in faster delivery of content to the user. 

![CDN](https://github.com/arhankundu99/System-Design/blob/main/Database/images/CDN.png)

How does CDN delivers content faster? CDN Basically caches content from the origin server and whenever a request comes, a nearby server sends the appropiate responses. CDN also compresses images or videos so that the content can be sent faster.

Refer the following links:
<ul>
  <li>
    https://www.cloudflare.com/learning/cdn/what-is-a-cdn/
  </li>
  <li>
    https://www.cloudflare.com/learning/cdn/caching-static-and-dynamic-content/
  </li>
</ul>

## Search Solutions
These solutions are used to provide <b>text searching capabilities with fuzzy search</b>. Common use case would the search bar of google, amazon, netflix etc.
For eg., if a seller uploads a product on amazon, the system should provide the searching capability for that product so that the users can search for it. 
Or if an user types a crew name, or a genre, then the movies with the given crew name or the genre should be searched and displayed.

If we want to build search engines, Elastic Search can be our first choice.

Common solutions are:
<ul>
  <li>
    Elastic Search
  </li>
  <li>
    Solr
  </li>
</ul>
Both of these solutions are built on <b>Apache Lucene</b>.

These are basically NoSQL Databases which store data in json document. These solutions also provide <b>fuzzy search</b> feature. For eg., If an user types a wrong spelled word, the system should show the results of the corrected word.
