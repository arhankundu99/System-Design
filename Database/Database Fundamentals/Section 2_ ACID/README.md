# Section 2: ACID

## What is a Transaction?
A transaction is basically collection of queries. 
For eg., If we want to deposit some amount in an account, We will have a transaction which has the following queries:
1. SELECT (The from and to accounts)
2. UPDATE (The from and to accounts)

### Transaction lifetime
1. BEGIN: Begin the transaction and execute the queries in memory. (There are also databases which would write in disk while queries are getting executed)
2. COMMIT: After the execution of queries is complete, commit all the changes in the database. (If the data is already in disk, then the database may just a set a flag indicating that the commit has been successful)
3. ROLLBACK: (Anything goes wrong in the commit phase or while executing the query, rollback would have to handle it)

### Example
Transfer 1000 dollars from account 1 to account 2.
```
BEGIN TRANSACTION

SELECT balance of account1
balance >= 1000
    UPDATE balance of account1
    UPDATE balance of account2

COMMIT TRANSACTION
```

## Atomicity
Atomicity simply means that all the queries in the transaction must either succeed completely or if any one of the transaction fails, the the entire transaction should fail. All the prior successful queries would have to rollback.

(The name of the concept is derived from the word "atom" because atom cannot be split. Well, now using nuclear fission, we can split, but in 1970s when this concept was introduced, then there was no concept of atom splitting)


Case 1: Before commit, if one of the queries fail, then the database would have to flush everything which is in memory.

Case 2: During commit, if the commit fails, then the database would have to rollback the existing commit changes which were in disk.


(See there are some databases which would write in disk while the queries are getting executed. In this case, when we commit, the database system may have a commit flag which it would set to 1. And in this case, the commits are faster, but rollbacks are a pain if they are needed to be done before committing)

Lets say we have 2 accounts: Account1 and Account2 with balance 1000 and 500. And we want to deposit 100 from account1 to account2.

```
BEGIN TRANSACTION
    SELECT BALANCE from Account1
    BALANCE >= 100
        UPDATE BALANCE from Account1
        ... database crashed here
```

Now when our database restarts, there can be 2 cases:
1. The database persists in disk before commiting. In this case, it has to check, whether the commit was successful or not, and if the commit was a failure, then it would have to rollback.
2. The database persists in memory before commiting. In this case, the memory has already been flushed, so there are no problems.

## Isolation

### 1. Read phenomenon
#### (a). Dirty reads
Reading something which other transaction has written but not committed yet. So there is a chance that this change that we just have read, could be rolled back also (Because it has not been committed yet). 
NOTE: If there are no uncommitted changes to be read, then the transaction reads the committed changes.

![Dirty Reads Example](./images/dirty_reads.png)

#### (b). Non repeatable reads
Non repeatable reads are the reads in a transaction where a row is fetched twice and the values in the row are different because some other transaction changed this row which this transaction was going on.
```
Example:
First Read: We read the balance, which is 1000.
Meanwhile, a separate transaction (Transaction 2) is executed, which deducts 200 from the same account.
Second Read: We read the balance again, and now it is 800.
```

![Non repeated reads example](./images/non_repeatable_reads.png)

#### (c). Phantom reads
Phantom reads are the reads which occurs when a transaction re-executes a query returning a set of rows and finds that the set of rows has changed because another transaction has committed which involved adding or deleting some rows.
<b>NOTE: This is different from non repeatable reads because in non-repeatable reads, values in same row changes, but in phantom reads, new rows are added or deleted</b>.

```
Example:
Transaction 1 reads 10 rows which have balance over 1000
Transaction 2 adds a new row wich balance 2000
Now when transaction 1 reads the rows which have balance over 1000, it finds that there are 11 rows (1 row committed by the transaction 2)
```
![](./images/phantom_reads.png)

#### (d). Lost Updates
Lost updates happen when changes made by a transaction are overwritten by the changes made by other transactions.

```
Example:
Transaction 1 reads balance of 1000 and adds 100, to update it to 1100
Transaction 2 reads balance of 1000 and adds 200, to update it to 1200
Transaction 1 commits
Transaction 2 commits (Overwrites the update made by transaction 1)

And now balance is 1200. It should have been 1300.
But in this case, if dirty read is enabled, then this would not happen.
```

![Lost Updates](./images/lost_updates.png)

### 2. Isolation Levels
#### (a) Read Uncommitted
Any change from outside is visible to an inflight transaction (Committed or not).

Version Selection: When a transaction reads a row, the database engine selects the latest version of that row based on timestamps or transaction identifiers. This version can be either committed or uncommitted. So, it can read data in an intermediate state if another transaction is modifying the same row concurrently.

Version Cleanup: After a transaction is committed, the DBMS may remove older versions of the row that are no longer needed. This cleanup helps manage storage and ensures that older, committed versions do not clutter the database.

High Concurrency, Data Inconsistency: Read Uncommitted isolation indeed allows for high concurrency since transactions do not block each other. However, it also allows for data inconsistency, as transactions may read uncommitted changes made by others, potentially leading to unexpected or incorrect results.

There are no locks which may lead to potential data corruption.

#### (b) Read Committed
Each query in a transaction only sees the committed changes made by other transactions.

Here locks are used so that no two transactions can modify the same data concurrently. Since locks are used, this becomes an overhead which would result in less performance that Read Uncommitted isolation level.

#### (c) Repeatable reads
The transaction will make sure that when a query reads a row, that row will remain unchanged until the transaction is running.

Consistent View of Data:

When a transaction starts under the Repeatable Reads isolation level, it gets a consistent view of the database. This means that any data read during the transaction will appear the same throughout the transaction's duration, regardless of changes made by other transactions.
Locking Mechanism:

To maintain this consistent view, the database system typically uses locks. When a transaction reads data (like a row or a set of rows), it places a lock on that data.
These locks can be shared locks or exclusive locks. Shared locks allow other transactions to read the locked data but prevent them from writing (modifying) it. Exclusive locks prevent other transactions from both reading and writing the locked data.

Duration of Locks:

Unlike lower isolation levels (like Read Committed), where locks are held for a shorter duration (often just for the duration of the read operation), in Repeatable Reads, locks are usually held until the transaction is completed (either committed or rolled back).
This longer duration of locks ensures that if a transaction reads a row at the beginning, it will read the same data at the end of the transaction, even if other transactions are trying to modify this data.

Handling Write Operations:

If a transaction under Repeatable Reads isolation level tries to write data, it will acquire an exclusive lock on the data. This prevents other transactions from reading or writing the data until the first transaction is completed.

Drawbacks:

The main drawback of the Repeatable Reads isolation level is the potential for lock contention and deadlocks. Because locks are held for the duration of the transaction, other transactions might be blocked for a significant time, leading to reduced concurrency and potential deadlocks.
Another issue is the possibility of phantom reads. While Repeatable Reads prevent other transactions from modifying data that has been read, it doesn’t prevent the insertion of new rows that meet the criteria of a query. Therefore, a transaction could read a set of rows matching a condition at the beginning and then find additional rows matching the same condition at the end of the transaction, due to insertions by other transactions.

in Repeatable Reads, locks are acquired on-the-fly as data is accessed, and these locks are held until the transaction is completed. This approach ensures data consistency but can lead to higher lock contention and decreased concurrency, especially in systems with long-running transactions.

```NOTE: This is an example internal working for the repeatable reads. Many other databases use MVCC (Multi version concurrency control which maintains their own versions (snapshots) before their transactions instead of locking the rows as we proceed).```

```
Also merge conflicts resolution can be implemented in repeatable reads. Suppose there are 4 rows in a table

---------
id |  t
---------
1  |  a
2  |  a
3  |  b
4  |  b
---------

And we begin 2 transactions concurrently (Here let's consider snapshot is being considered for repeatable reads)

T1
begin transaction isolation level repeatable read;
update table1 set t = 'b' where t = 'a';
select * from table1;
---------
id |  t
---------
1  |  b
2  |  b
3  |  b
4  |  b
---------

Now we do a similar transaction T2 (Note we did not commit the previous transaction yet)
T2
begin transaction isolation level repeatable read;
update table1 set t = 'a' where t = 'b';
select * from table1;
---------
id |  t
---------
1  |  a
2  |  a
3  |  a
4  |  a
---------

Now if we commit the transactions T1 and T2, and we do a select * from table1;

We get
---------
id |  t
---------
1  |  b
2  |  b
3  |  a
4  |  a
---------

Note that here the changes from the 2 transactions were merged and not overwritten.

```

#### (d) Snapshot
Each query in a transaction only sees the changes that have been committed before the transaction starts. It's like a snapshot version of the database. We don't get phantom reads here.

#### (e) Serializable
This is the highest level of isolation. Each transaction runs as if they are serialised one after the other even though they are running concurrently.

```
If we take the above example in repeatable reads section with transaction T1 and T2 (T2 running after T1), the table would look like this
---------
id |  t
---------
1  |  a
2  |  a
3  |  a
4  |  a
---------


```

## Isolation levels vs Read Phenomena
![Isolation levels vs read phenomenon](./images/isolation_levels_vs_read_phenomenon.png)

```NOTE: Each database implements the isolation levels differently. Which means that the result we get for an isolation level in a database may not necessarily match with the result in other database with the same isolation level```


## Consistency
Consistency in ACID is about correctness and integrity of the data within a <b>single database</b>. (For eg., in account update, both sender and receiver balance should be updated otherwise there would be inconsistency in the data). This is different from Consistency in CAP where it refers to same data across a <b>distributed system</b>.

## Durability
Durability is basically persisting the data in a non volatile storage system. Even if the database crashes, when the database is up, all the data which was previously committed should show up.

A lot of databases play around with it. One example is that a database writes to memory and ocassinally writes the snapshot to the disk (Because writing everytime to the disk is slow).

### Durability techniques
#### WAL - Write ahead log
In this technique, before committing to the database, logs are written in the log file and once the changes are written to the database, then a checkpoint is made in the log file which signifies that this commit or transaction was completed.

Now if any error happens during the commit phase, then the logs are replayed from the last checkpoint on the latest committed database to restore.

#### Asynchronous snapshot
Here as we write, we keep everything in memory and periodically we persist in the disk. If any crash occurs, then last snapshot of the database and the logs from that time are used to restore. 

Redis uses both WAL and Asynchronous snapshot.

### OS Cache
When the database tells os to write the changes to the disk, OS writes all the changes to its own cache called OS cache and tells the database that it has written successfully. (OS does this because it wants to write to disk in batches since writing to disk is slow). Now if the system crashes during this period, We will still face data loss even though we got commit as success, because OS never wrote to disk from its cache.

To resolve this, database uses ```Fsync OS command```, which forces writes to always go to disk.

Fsync can be expensive and slows down commits.

## ACID Quiz
![Q1](./images/ACIDQ1.png)
![Q2](./images/ACIDQ2.png)
![Q3](./images/ACIDQ3.png)
![Q4](./images/ACIDQ4.png)
![Q5](./images/ACIDQ5.png)
![Q6](./images/ACIDQ6.png)
![Q7](./images/ACIDQ7.png)
![Q8](./images/ACIDQ8.png)
![Q9](./images/ACIDQ9.png)