Database Pages — A deep dive

Databases often use fixed-size pages to store data. Tables, collections, rows, columns, indexes, sequences, documents and more eventually end up as bytes in a page. This way the storage engine can be separated from the database frontend responsible for data format and API. Moreover, this makes it easier to read, write or cache data when everything is a page.

Here is an example of SQL Server page layout.

SQL Server page layout (https://learn.microsoft.com/en-us/sql/relational-databases/pages-and-extents-architecture-guide?view=sql-server-ver16).


A Pool of Pages

Databases read and write in pages. When you read a row from a table, the database finds the page where the row lives and identifies the file and offset where the page is located on disk. The database then asks the OS to read from the file on the particular offset for the length of the page. The OS checks its filesystem cache and if the required data isn’t there, the OS issues the read and pulls the page in memory for the database to consume.

The database allocates a pool of memory, often called shared or buffer pool. Pages read from disk are placed in the buffer pool. Once a page is in the buffer pool, not only we get access to the requested row but also other rows in the page too depending on how wide the rows are. This makes reads efficient especially those resulting from index range scans. The smaller the rows, the more rows fit in a single page, the more bang for our buck a single I/O gives us.

The same goes for writes, when a user updates a row, the database finds the page where the row lives, pull the page in the buffer pool and update the row in memory and make a journal entry of the change (often called WAL) persisted to disk. The page can remain in memory so it may receive more writes before it is finally flushed back to disk, minimizing the number of I/Os. Deletes and inserts work the same but implementation may vary.

Page Content

What you store in pages is up to you. Row-store databases write rows and all their attributes one after the other packed in the page so that OLTP workloads are better especially write workload.

Column-store databases write the rows in pages column by column such OLAP workloads that run a summary fewer fields are more efficient. A single page read will be packed with values from one column, making aggregate functions like SUM much more effective. I made a video comparing row vs column based storage engines,

Document based databases compress documents and store them in page just like row stores and graph based databases persist the connectivity in pages such that page read is efficient for traversing graphs, this also can be tuned for depth vs breadth vs search.

Whether you are storing rows, columns, documents or graphs, the goal is to pack your items in the page such that a page read is effective. The page should give you as much useful information as possible to help with client side workload. If you find yourself reading many pages to do tiny little work consider rethinking your data modeling. This is a whole different article, data modeling, underrated.

Small vs Large Pages

Small pages are faster to read and write especially if the page size is closer to the media block size, however the overhead cost of the page header metadata compare to useful data can get high. On the other hand, larger sizes can minimize metadata overhead and page splits but at a cost of higher cold read and write.

Of course this gets very complicated the closer you get to the disk/SSD. Great minds in storage industry are working on technologies like Zoned and key value store namespaces in NVMe to optimize read/writes between host and media. I’m not even going to attempt to explain it here because frankly speaking I’m still dipping my toes in these waters.

Postgres Default page size is 8KB, MySQL InnoDB is 16KB, MongoDB WiredTiger is 32KB, SQL Server is 8KB and Oracle is also 8KB. Database defaults work for most cases but it is important to know these default and be prepared to configure it for your use case.


How page are stored on Disk

There are many ways we can store and retrieve pages to and from disk. One way is to make a file per table or collection as an array of fixed-size pages. Page 0 followed by page 1 followed by page 2. To read something from disk we need to information, the file name, offset and the length, with this design we have all three!

To read page x, we know the file name from the table, to get the offset it is X * Page_Size, and the length in bytes are the page size.


Example reading table test, assume a page size of 8KB, to read pages 2 through 10, we read the file where table test live, with an offset 16484 (2*8192) for 65536 bytes ((10–2)*8192).

But that is just one way, the beauty of databases is every database implementation is differnet.

Postgres Page Layout

Postgres page layout (https://www.postgresql.org/docs/current/storage-page-layout.html)

Page header — 24 bytes

The page must have metadata to describe what is in the page including the free space available. This is a 24 bytes fixed header.

ItemIds — 4 byte each

This is an array of item pointers (not the items or tuples themselves). Each itemId is a 4 byte offset:length pointer which points to the offset in the page of where the item is and how large is it.

It is the fact that this pointer exist allows the HOT optimization (Heap only tuple), when an update happens to a row in postgres, a new tuple is generated, if the tuple happened to fit in the same page as the old tuple, the HOT optiimization changes the old item id pointer to point to the new tuple. This way indexes and other data structures can still point to the old tuple id. Very powerful.

Although one criticism is the size the item pointers take, at 4 bytes each, if I can store 1000 items, half the page (4KB) is wasted on headers.

I use items, tuples and rows but there a difference, the row is what the user see, the tuple is the physical instance of the row in the page, the item is the tuple. The same row can have 10 tuples, one active tuple and 7 left for older transactions (MVCC reasons) to read and 2 dead tuples that no one needs any more.

Items — variable length

This is where the items themselves live in the page one after the other.

Special — variable length
This section is only applicable to B+Tree index leaf pages where each page links to the previous and forward. Information about page pointers are stored here.


Summary

Data in databases end in pages, whether this is index, sequence, or a table rows. This makes it easier for the database to work with pages regardless what is in the page itself. The page it self has a header and data and is stored on disk in as part of a file. Each database has a different implementation of how the page looks like and how it is physically stored on disk but at the end, the concept is the same.