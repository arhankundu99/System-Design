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


