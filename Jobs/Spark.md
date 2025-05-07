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
Streaming jobs process the data as it arrives in real time.

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

## A spark job = A computation that spark runs
- Batch jobs can run once or on a schedule (via cron, airflow) etc.
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
- YARN creates a container for <b>Driver</b> and multiple containers for <b>Executors</b>.

- The driver gets started. The driver is the brain of the application. It parses our spark code, builds a DAG (Directed acyclic graph) and coordinates execution.

- Driver Requests Resources from YARN

- YARN Allocates Resources:
Once resources are available, YARN will provide details about the containers where the executors will run.

- Driver Receives Resource Details of the executors.

- Driver Divides Tasks and Sends to Executors (Via SparkContext obj, explained below)
- Executors Execute Tasks
- Results are Collected by Driver (SparkContext).
- Tasks are completed and resources are released by YARN.

## SparkContext
- SparkContext is a spark object which is created by the Spark driver process.
- SparkContext is responsible for 
```
1. Connecting to resource manager like YARN to request resources.

2. Launching and scheduling tasks on executor nodes.

3. Coordinating distributed data processing (by managing RDDs, broadcasting variables etc)

4. Once the job is done, SparkContext obj manages stopping and cleanup of the executors
```

## RDD (Resilient distributed dataset)

- RDDs are collection of elements that can be operated on parallel.

### First we create a spark context
```scala
val conf = new SparkConf().setAppName(appName).setMaster(master)
new SparkContext(conf)

// Master is a Spark, Mesos or YARN cluster URL
```

### Then we can create RDDs

```scala
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data)

// distData is a RDD made using sparkContext (sc). Now operations on distData can be executed on parallel

distData.reduce((a, b) => a + b) // This will be executed in parallel.

Normally SparkContext tries to setup the number of partitions according to the cluster, but we can also specify number of partitions

// distData = sc.parallelize(data, 10) where 10 is the number of partitions.

// We can also read files using RDDs
val distFile = sc.textFile("data.txt")
```

## RDD operations
- RDDs support two types of operations: 

```
Transformations: Which create a new dataset from an existing one. Eg: Map

Actions: Which return a value to the driver program after running a computation on the dataset. Eg: Reduce
```

- All transformations in Spark are lazy (they do not compute their results right away. Instead, they just remember the transformations applied to some base dataset (e.g. a file)).

- The transformations are only computed when an action requires a result to be returned to the driver program (Handled by sparkContext in driver program). This enables Spark to run more efficiently.


```scala
val lines = sc.textFile("data.txt")
val lineLengths = lines.map(s => s. length) 
// Spark Driver just creates a DAG till here. (For eg., lineLengths -> lines)


val totalLength = lineLengths.reduce((a, b) => a + b)
// Reduce is an action, now sparkContext object in the driver would divide the tasks of map and reduce among the executors.

// Executors then return the results to the driver.
```

- We can also persist a RDD if it can be used in another actions. 


```scala
val lines = sc.textFile("data.txt")
val lineLengths = lines.map(s => s. length) 

lineLengths.persist() // persist in memory, we have the option to persist in disk also, The results would be persisted in the executor nodes.

val totalLength = lineLengths.reduce((a, b) => a + b)


NOTE: The persist is called before the reduce action, if the persist was called after, then it would NOT be persisted because lineLengths computation would be flushed from memory of the executors once the reduce function is run.
```

