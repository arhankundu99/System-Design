# NOTE
The contents of this readme file are taken from the book: Database Internals by Alex Petrov.

# DBMS Architecture
Database management systems use a client server model, where database system instances (nodes) take the role of servers and application instances take the role of clients.

![DBMS Architecture](images/DBMS%20Architecture.png)

# Memory versus Disk based DBMS
<table>
<tr>
    <td><b>Memory</b></td>
    <td><b>Disk</b></td>
</tr>
<tr>
    <td>Stores data in memory (RAM) and uses the disk for recovery and logging.</td>
    <td>Stores data in disk and uses memory for caching.</td>
<tr>
<tr>
    <td>RAM Prices are very high compared to disks.</td>
    <td>Low price than RAM.</td>
<tr>
<tr>
    <td>Normally, the database snapshots are periodically saved in disk as well as logs. In case of failure, the last database snapshot and the logs from the last database snapshot timestamp are used for recovery.</td>

<tr>
</table>

# Page in DBMS
Data in disk is stored and retrieved in units called disk blocks or pages.

# Column vs Row oriented Database
In tabular database, Tables can be partitioned either horizontally (storing
values belonging to the same row together), or vertically (storing values
belonging to the same column together).

Examples of row-oriented DBMS are MySQL, PostgreSQL etc., and examples of column-oriented DBMS are MonetDB and C-Store.

## Row-Oriented Data Layout
Here the record is stored in rows.

![Row Oriented table](images/row%20oriented%20database.png)

Note that Data from the database is accessed block-wise (in pages).
So for eg., the page can contain

```ID;name;Birth Date;Phone Number;10;John;01 Aug 1981;+1111222333;20;Sam....```

This layout is great if we want to access complete records. But if we want to do some analytics (For eg., we want to fetch all the birth date), then we would we reading so many fields that we wont require (Remember that the data is read in pages).

## Column-Oriented Data Layout
Here the record is stored in columns.
![Column Oriented table](images/column%20oriented%20database.png)

The values in the same column are stored contiguosly in disk. This would be great if we want to do analytics based on some columns.
In the image, we can see that each value is mapped to the ID, which we can avoid by using the position of the value (offset).

## READ ABOUT WIDE COLUMN DATABASES

## Data and index files

Database systems store data records, consisting of multiple fields, in tables, where each table is usually represented as a <b>separate file</b>.

Each record in the table can be looked up using a search key. To locate a record, database systems use <b>indexes</b>, which are auxiliary data structures that allow it to efficiently locate data records <b>without scanning an entire table to search for a record.</b>

A database system usually seperates <b>data files</b> and <b>index files</b>. Data files store the records and index files store the metadata of records and are used to locate a record efficiently in data files.

Most modern storage systems do not delete data from pages explicitly. Instead, they use deletion markers (also called
tombstones), which contain deletion metadata, such as a key and a timestamp. Space occupied by the records shadowed by their updates or deletion markers is reclaimed during garbage collection, which reads the pages, writes the live (i.e., nonshadowed) records to the new place, and discards the shadowed ones.

### Index Files
Index files are organized as specialised structures that <b>map keys to locations in data files (where the record is identified by the key).</b>

## Pages in DBMS (Revisit)
As we discussed before, the page is the smallest memory unit (or memory block) that the database system reads. For eg., In MySQL, the default page size is 16 KB. That means the database system would get data from disk in multiples of 16 KB. We can also change the page size.

<b><i>NOTE: Each data file is split into multiple pages and the pages contain the records.</i></b>.

Anytime there is a change in the page of data file, the entire page would be written in disk because page is the smallest unit of memory of database system (For eg., lets say there are 16 records in a page and we modified a record in the page. Even though we modified only one record, the entire page which consists of 16 records would be written in disk.). 

## How does writes happen in the same page?
Let's says two write requests concurrently in the same page come in MySQL.
<ol>
    <li>
        <b>The requests are for the same record:</b> In this case, the record is locked to make sure the data is not in inconsistent state after the requests are processed. And then the page is written in the database. 
    </li>
    <li>
        <b>The requests are for different record in same page:</b> In this case, both the modifications are done to the page in memory and then the page is written to the disk. 
    </li>
</ol>



