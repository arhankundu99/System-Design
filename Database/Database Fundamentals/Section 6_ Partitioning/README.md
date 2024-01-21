# Partitioning

Partitioning breaks the table into multiple tables and when we have multiple tables, we would have index structures for each table. So this makes CRUD operations efficient, but we would have an overhead of calculating which query should go in which table(s) based on the partition key.

Also during an update, a row can move from one partition to another partition.

![table_partition](images/table_partition.png)

## Vertical vs Horizontal Partitioning

![vertical_vs_horizontal_partition](images/vertical_vs_horizontal_partition.png)

![partitioning_types](images/partitioning_types.png)

NOTE: READ ABOUT CONSISTENT HASHING.

## Partitioning vs Sharding

![partioning_vs_sharding](images/partitioning_vs_sharding.png)