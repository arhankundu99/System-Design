# Apache Spark Overview

Reference: https://spark.apache.org/docs/latest/rdd-programming-guide.html

## Intro
- Distributed computing framework
- Supports batch, streaming and machine learning operations
- Commonly used for big data etl (Extract, transform and load) and machine learning pipelines.
- We write spark applications typically in scala, python, java and R and run them on clusters via YARN (Yet another resource negotiator), Kubernetes etc.

## Batch and stream jobs
We have heard the words "batch" and "streaming" in the intro section. Lets look into them.

### Batch
Batch processing handles large volumes of data collected over time, and processes them in one go.
```python
# Pseudo-code
df = spark.read.json("s3://logs/watch-history/2025-05-06/")
agg_df = df.groupBy("user_id").agg(sum("watch_time"))
agg_df.write.parquet("s3://analytics/daily-watch-summary/")
```
The above code
- Aggregates the total number of hours each user watched the previous day.
- Stores the results in a data warehouse (like Hive or Redshift).

### Streaming
Spark Structured Streaming processes streams as micro-batches of data (Not event by event).

#### Micro-batches and offset tracking
- A micro-batch processes a range of new events. For example,  `.trigger(processingTime="5 seconds")` schedules a batch every five seconds. If processing takes longer, the next batch starts when the previous one finishes, not exactly every five seconds.

- Spark uses the record offsets that Kafka provides. Each offset identifies a record’s position within a particular topic partition. Spark selects a separate offset range for each partition

- Spark processes those ranges and writes the results. Parsing, filtering, and other transformations execute against the events in the microbatch, potentially in parallel.

- Spark saves its progress in its own checkpoint directory. The directory can be in durable storage such as an Azure Storage account. It contains offset logs describing what to process and completion logs recording which batches finished.

- Spark does not commit consumer offsets back to Kafka. It uses Kafka’s record offsets to read data, but uses its own checkpoint and not Kafka’s saved consumer group offsets to track recovery progress.

- After a restart, Spark uses the same checkpoint to recover.

```python
df = spark.readStream.format("kafka")\
     .option("subscribe", "transactions").load()

parsed_df = df.selectExpr("CAST(value AS STRING)")\
              .select(from_json("value", schema).alias("data"))\
              .select("data.*")

fraud_alerts = parsed_df.groupBy("card_id")\
    .applyInPandas(detect_fraud, schema=fraud_schema)

fraud_alerts.writeStream\
    .format("console")\
    .start()
```
The above code shows an example to detect fradulent transactions in real time.

### Example of spark streaming and batch job in production
- Spark streaming job processes events from event hub in micro-batches.
- It writes the results in parquet files in a storage account.
- The streaming job then commits the offsets after its processing in its configured storage and reads the next micro batch of events using those offsets stored.
- A pipeline is configured in ADF (Azure data factory). That pipeline selects the parquet files in a time window (May be 30 mins), merges the parquet files in a single temporary parquet file and starts the batch job with the merged parquet file.
- The batch job then processes the parquet file and ingests into db in bulk.
- The pipeline again continues the same process for next time window.

### Parquet files
Parquet is a file format for storing structured data efficiently for analytics. Its key feature is column-oriented storage.

#### Example: online store purchases


| orderId | product | quantity | price |
|---|---|---|---|
| O1 | Laptop | 1 | 900.00 |
| O2 | Mouse | 2 | 25.00 |
| O3 | Laptop | 1 | 900.00 |

**CSV stores the records row by row:**

```csv
orderId,product,quantity,price
O1,Laptop,1,900.00
O2,Mouse,2,25.00
O3,Laptop,1,900.00
```

**Parquet groups values by column within chunks of rows. Conceptually:**

```text
orderId:  [O1, O2, O3]
product:  [Laptop, Mouse, Laptop]
quantity: [1, 2, 1]
price:    [900.00, 25.00, 900.00]
```

**Why is this useful?**

- To calculate total revenue, a query can read only `quantity` and `price`, without loading `orderId` or `product`.
- Repeated values, such as `Laptop` and `900.00`, can be encoded and compressed efficiently.

## A spark job = A computation that spark runs
- Batch jobs can run once or on a schedule (For eg., via ADF).
- Streaming jobs run continuously as they need to process data in real time.

## Simple analogy
- Spark = Distributed computing platform
- Jobs = logic that we want run on spark
- Cron (or other tools) help us to schedule batch jobs.

## How spark works internally
- We write spark code
- We submit the spark code to <b>YARN (Yet another resource negotiator) or any other resource manager like kubernetes</b>
    ```
    spark-submit \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 8g \ 
    --executor-cores 4 \
    --num-executors 10 \
    your_script.py
    ```
- In the above command, we are requesting the driver memory to be 4 gb, memory of each executor to be 8 gb, number of cores for each executor to be 4 and total number of executors to be 10.

-  Submit the application using  spark-submit .
The command selects YARN and cluster deployment mode.
2. YARN starts the driver.
3. YARN’s ResourceManager allocates resources for executors.
4. Executors start and register with the driver. The driver coordinates its executors directly.
5. The driver runs the application and builds plans.
It executes the main program. Spark transformations such as filter() and select()  describe computations rather than immediately executing them. An action such as count(), collect(), or a write triggers execution.
6. The driver’s schedulers send tasks to executors. Executors process data partitions in parallel.
7. Executors report completion to the driver.
8. Resources are released when the application ends.


Example of spark transformations
```python
from pyspark.sql import SparkSession, functions as F

spark = SparkSession.builder.appName("CachingExample").getOrCreate()

# Describe five orders with amounts: 0, 100, 200, 300, 400.
orders = spark.range(5).select(
    F.col("id").alias("order_id"),
    (F.col("id") * 100).alias("amount")
)

# Transformations: describe the computation, but do not execute it.
expensive_orders = (
    orders
    .filter(F.col("amount") >= 200)
    .select("order_id", "amount")
)

# Mark this result for caching; no computation yet.
expensive_orders.cache()

# First action: compute the result and populate its cache.
print(expensive_orders.count())  # 3

# Another action: reuse the cached rows to calculate total revenue.
total = expensive_orders.agg(
    F.sum("amount").alias("total")
).first()

print(total["total"])  # 900

# Release the cached data when it is no longer needed.
expensive_orders.unpersist()

spark.stop()
```
