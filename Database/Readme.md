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

### Difference between Document, Graph, key-value and column databases
To be written.

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

#### Replication (Horizontal scaling technique)
Replication is a <b>horizontal scaling technique</b> which is basically making exact copies of the database and storing them in different database servers.

![Replication](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Replication.png)

<b>Advantages</b>
<ul>
  <li>
    Increases Availibility (Since there are many database servers containing the same data)
  </li>
  <li>
    Increases read performance
  </li>
</ul>

<b>Disadvantages</b>
<ul>
  <li>
    Complexity increases when we have write operations since the write has to be copied in all the database servers.
  </li>
</ul>

<b>When to use</b>
So if our data is primarily read focussed, then we can use this database. 

### Normalization in SQL Databases
To be written

#### Can we design SQL Databases to be replicable and become distributed database?
Distributed database is basically a collection of interconnected databases which are spread across various locations.

![Distributed Database](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Distributed%20Database.jpg)

With <b>distributed database with replication</b>, people can access the database nearer to them which would result in faster access to the data. 

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

### Sharding (Horizontal scaling technique)
Database sharding is basically <b>splitting our database into multiple chunks which are called shards</b> and storing them in several database servers. 
<br></br>
<b>NOTE that sql databases like MySQL, Oracle, PostgreSQL do not support automatic sharding and we have to write manual logic for sharding</b>

![Database Sharding](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Database%20sharding.png)

<b>Advantages of database sharding</b>

<ul>
  <li>
    <b>Improve Response Time</b>: Data retrieval takes longer on a single database. The database management system needs to search through many rows to get the data. So if we split our data into smaller chunks (shards), then the data retrieval would be much faster (Given that the system knows which shard to look into for the given transaction)
  </li>
  <li>
    <b>No single point of failure</b>: If we shard the database, then the shards are distributed on different database servers. So if one of the shard fails, the other shards would still work and data would be restored from an alternate shard.
  </li>
  <li>
    <b>Scale</b>: We can scale our database with sharding. We can add more shards when the data increases. 
  </li>
</ul>

<b>Disadvantages of database sharding</b>
<ul>
  <li>
    We have to write a service which knows the database shard to route the transaction.
  </li>
</ul>

#### Sharding techniques
<ul>
  <li>
    <b>Range Based Sharding:</b> In this technique, we assign a <b>pre-defined range</b> to each shard. 
    <img src = "https://github.com/arhankundu99/System-Design/blob/main/Database/images/Range%20Based%20Sharding%201.png"></img>
    <br></br>
    So when a query hits the load balancer for database, it will check the shard id and then it will redirect to that shard. Notice that a query can require data from multiple shards as well. So selecting the <b>Shard Key</b> becomes very important for performance. We have to select shard keys in such a way that for our queries, we should look into minimum required shards.
  </li>
  <li>
  <b>Hashed Sharding:</b> In this technique, the record is taken as an input and then a hash value is generated. Based on that hash value, the record is allocated a shard.
  A simple hash function would be <b>Hash Value = ID % number of shards </b>. 
  
  <b>Advantages of hashed sharding </b>
  <ul>
    <li>
      In Hashed Sharding, no look up table for allocating the shard (as in range based sharding) needs to be maintained (Shard key does not need to be maintained)
    </li>
  </ul>
  
  <b>Disadvantages of hashed sharding </b>
  <ul>
    <li>
      In Hashed Sharding, query operations would be much complex because the records are much more distributed.
    </li>
    <li>
      Resharding can be expensive. For the above hash function, if we increase the number of shards, then we would have to rebalance all the shards
    </li>
  </ul>
 
  </li>
  
  <li>
  <b>Entity / relationship based sharding </b>: In this technique, all the related data is kept in single shard which can reduce the need for looking into multiple shards for a transaction and increase performance.
  </li>
  
  <li>
  <b>Geography based Sharding</b>: In this technique, all the data belonging to the same location is kept in the same shard.
  </li>
</ul>

### Scaling SQL Databases Architechture Example

#### Design 1

![Scaling SQL Database Design 1](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Scaling%20SQL%20Databases%201.png)

<b>NOTE: Proxy is a service which is commonly used for load balancing, caching and security.</b>

<ul>
  <li>
    <b>Configuration Service</b> maintains a look up table of the shards and their locations.
  </li>
  <li>
    <b>Cluster Proxy</b> decides which shard to hit for the corresponding query.
  </li>
</ul>

#### Improving Design 1
![Design 1 Improvement](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Scaling%20SQL%20Databases%20Design%201%20Impovement.png)

In this design, we have another proxy called <b>Shard Proxy</b> which is present between the <b>Load Balancer</b> and the <b>shard</b>. 
<br></br>
Shard proxy can be used for the following reasons:
<ul>
  <li>
    When a shard becomes large enough, then the shard proxy can be used to break the shard into smaller shards
  </li>
  <li>
    It can be used to cache some frequent queries, monitor database health, terminate queries if the request is taking long and publish metrics about the data.
  </li>
</ul>

#### Sacrificing Consistency for availibility
![Design 1 Improvement (Sacrificing consistency for availibility)](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Scaling%20SQL%20Databases%20Design%201%20Impovement%20(Sacrificing%20Consistency%20for%20availibility).png)

In this design, we introduce <b>slave shards</b> where the data of master shard is replicated. Here, the following things happen:
<ul>
  <li>
    Write / Read operations are supported by the master shard.
  </li>
  <li>
    Only read operations are supported by slave shards.
  </li>
</ul>

This increases the availability since there are multiple shards now for read operations but consistency is lost because it takes time to replicate the data among slave shards. 

## How does NoSQL Databases work?
NoSQL Databases follow <b>BASE</b> model. <b>BASE</b> stands for:
<ul>
  <li>
    <b>Basic Availability</b>: The database favours availibility of data rather than consistency of data.
  </li>
  <li>
    <b>Soft State</b>: The database does not have to be consistent all the time.
  </li>
  <li>
    <b>Eventual Consistency</b>: The database nodes will be eventually consistent (become consistent after some time).
  </li>
</ul>

<b>SQL Databases are more geared towards consistency and NoSQL databases are geared towards scalability and are therefore more fault-tolerant.</b> In other words, when replicas crash, NoSQL databases will still be able to return data, albeit not the most accurate version.

NoSQL Databases are <b>Horizontally scalable</b>. That means to increase scalability, we can just add more database servers.

![Horizontally Scalable](https://github.com/arhankundu99/System-Design/blob/main/Database/images/Horizontal%20Scaling.png)

### CAP Theorem
<b>CAP Theorem</b> states that among <b>Partition tolerance</b>, <b>Availibility</b> and <b>Consistency</b>, A distributed system can have <b>Only two</b> of the three properties.

![CAP Theorem](https://github.com/arhankundu99/System-Design/blob/main/Database/images/CAP%20Theorem.png)

#### Partition tolerance
Lets say we have two nodes A and B in our distributed database system. Any write in A is replicated in node B and any write in B is replicated in node A. Now, if the connection breaks between node A and node B, there are 2 ways for the database to work:
<ul>
  <li>
    <b>Scarifice Consistency: </b> Here the nodes A and B will continue to work but any write in A will not be replicated in B and vice versa since there is no connection. Therefore there would be inconsistency in the data. So this database would become <b>AP</b> (Partition tolerace and Availibility) system.
  </li>
  <li>
    <b>Scarifice Availibility: </b> Here one of the cluster would be turned off. That means either the cluster with contains node A or node B would be made unavailable. In this system there would be consistency but <b>availibility</b> decreases. So this database would become <b>CP</b> (Consistent and Partition tolerance) system.
  </li>
</ul>

<b>AC</b> (Availibility and Consistency) systems do not exist in reality.

## SQL vs NoSQL Databases
Some important differences are captured in the below table:
<table>
  <tr>
    <th>SQL Databases</th>
    <th>NoSQL Database</th>
  </tr>
  <tr>
    <td>
      SQL Databases are more geared towards consistency.
    </td>
    <td>
      NoSQL Databases are more geared towards availibility.
    </td>
  </tr>
  <tr>
    <td>
      Fixed Schema.
    </td>
    <td>
      Flexible Schema.
    </td>
  </tr>
  <tr>
    <td>
      The data in SQL Databases are <b>NORMALIZED</b> to reduce data redundancies (i.e., the data is split into multiple tables). So inserting a record or retrieving a record is slower than NoSQL Databases.
    </td>
    <td>
      Data is <b>NOT NORMALIZED</b> in NoSQL Databases. All the data is stored in a single record and there could be multiple data duplications. As all the data is stored in a single record, <b>Inserting</b> and <b>retrieving</b> is faster because no join operations are required here.
    </td>
  </tr>
  <tr>
    <td>
      As the Data is normalized in SQL Databases, Updates / Deletions are easier because there is no duplication of data.
    </td>
    <td>
      Updates / Deletions are costlier because the data is duplicated in NoSQL Databases.
    </td>
  </tr>
  <tr>
    <td>
      SQL Databases are optimized for filtering as the data is normalised.
    </td>
    <td>
      NoSQL Databases are not optimized for filtering as the data is not normalised.
    </td>
  </tr>
</table>

## References:
<ul>
  <li>
    https://betterprogramming.pub/introduction-to-nosql-databases-7f6ed6e055c5
  </li>
  <li>
    https://betterprogramming.pub/scaling-sql-nosql-databases-1121b24506df
  </li>
  <li>
    Codekarle youtube channel
  </li>
</ul>
