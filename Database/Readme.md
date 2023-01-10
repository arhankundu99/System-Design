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

Although Elasticsearch is a NoSQL database, it is <b>NOT RECOMMENDED TO USE AS A PRIMARY DATABASE</b>. Some operations like inserting values are more expensive to perform than other databases.

## SQL Databases
SQL stands for <b>Structured Query Language</b>. SQL Databases are called <b>Relational Database Management System (RDBMS)</b>. 
<br></br>
In Relational Database management systems, data is stored in the form of tables. Each row is uniquely identified by a primary key and each row can also have a foreign key (which is a primary key in another table). <b>Through the foreign key, a relation is formed between two tables, Hence it is called relational databases</b>.

![RDBMS Example Image](https://github.com/arhankundu99/System-Design/blob/main/Database/images/rdbms%20image%201.png)

## NoSQL Databases
NoSQL is know as <b>"No SQL" (which means it cannot support sql)</b> and sometimes called as <b>"Not Only SQL"</b> (When the database can support SQL queries)

There are four kinds of NoSQL databases:
<ul>
  <li>
    Document (Mongo, Firestore)
  </li>
  <li>
    Graph (Neo4j)
  </li>
  <li>
    Key-value (Redis)
  </li>
  <li>
    Wide-column (Cassandra, HBase)
  </li>
</ul>

## How does SQL databases work?

SQL Databases follow <b>ACID</b> properties. ACID stands for
<ul>
  <li>
    <b>Atomicity</b>: <b>Either all the transactions in the commit call will be successful or no transaction will be successful</b>. A very common example is sending some money to a friend. Here 2 transactions take place. One is debiting from your account and the other is crediting into your friend account. Both the transactions should either be successful or not successful. It should not happen that one transaction is successful and the other is not successful. If a person A is transferring X amount to person B, then the following steps will take place.
    <ol>
      <li>
        <b>Transaction 1:</b> Debit X from A
      </li>
      <li>
        <b>Transaction 2:</b> Credit X to B
      </li>
      <li>
        <b>Commit the above transactions</b> (After commit call, the changes take place in the database)
      </li>
    </ol>
  </li>
  
  <li>
    <b>Consistency</b>: Consistency ensures that the data is consistent before and after the transactions. For example, Person A has balance Z in his account and he transfers X amount to Person B. Then after the transaction and if the transaction is successful, the Person A's balance should be Z - X and person B's balance should increase by X. The data in the database should be consistent.
  </li>
  
  <li>
    <b>Isolation</b>: Isolation ensures that if a transaction is occuring on some rows, then the rows are locked by the database and other transactions which would occur on these rows will have to wait for the rows to unlock. This ensures that no invalid transaction takes place. For eg., Let's say a person has 500 rs in his account. And he did 2 withdrawal requests for 300rs and 500rs. Because of the isolation property, when the transation for 300rs is taking place, then the row will be locked, that means the transaction of 500rs will not happen at that time. And when the transaction of 300rs finishes, the amount left is 200rs and this transaction fails. Without isolation, the two transactions may occur concurrently and the row would have inconsistent values then.
  </li>
  
  <li>
    <b>Durability</b>: Durability ensures that all the data in the database is written in disk. So that in case the database gets destroyed or restarted, the data can be recovered from the disk.
  </li>
</ul>

SQL Databases are <b>VERTICALLY SCALABLE</b>. That means there is just one server and to increase the number of requests it can handle, we can add more cpu, ram, ssd in it.

![Vertical Scaling](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Vertical%20Scaling.png)

#### Advantages of vertical scaling
<ul>
  <li>
    Easy to implement.
  </li>
  <li>
    Consumes less power.
  </li>
</ul>

#### Downsides of vertical scaling
<ul>
  <li>
    There is a limit to the amount we can upgrade.
  </li>
  <li>
    Single point of failure because there is just one server
  </li>
  <li>
    Limited Scaling
  </li>
</ul>

#### Can SQL Databases be horizontally scalable (ie become distributed database)?
Distributed database is basically a collection of interconnected databases which are spread across various locations.

![Distributed Database](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Distributed%20Database.jpg)

With distributed database, people can access the database nearer to them which would result in faster access to the data. 

Let's say we have several SQL databases set up across the world. The following things would happen:
<ul>
  <li>
    An update query comes to the nearest database. Then to provide <b>isolation</b>, the database would have to lock its own row(s) and the row(s) of all the connected databases. And let's say another same update query comes in a database in some other location. Then that database would try to lock it's own row and the rows of all the connected databases. This may lead to <b>Data Race</b>.
  </li>
  <li>
    Let's say the update is successful, then the database would have to update the rows of all the other databases. And In during that time, the data in other databases would remain <b>inconsistent</b>.
  </li>
</ul>

So its possible to make SQL Databases distributed, but some of the ACID properties would be lost.
