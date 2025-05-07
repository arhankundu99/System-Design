# Azure Batch Jobs
Reference: https://learn.microsoft.com/en-us/azure/batch/batch-technical-overview
## Overview
- Can be used when we have "embrassingly" parallel tasks to execute.

- Code example is given below.
```python
from azure.batch import BatchServiceClient
from azure.batch.batch_auth import SharedKeyCredentials
from azure.batch.models import (
    PoolAddParameter,
    VirtualMachineConfiguration,
    ImageReference,
    JobAddParameter,
    TaskAddParameter,
    ResourceFile,
    OutputFile,
    OutputFileUploadOptions,
    OutputFileUploadCondition,
    TaskConstraints
)
import azure.storage.blob as azureblob
import datetime

# --- Configuration (replace with our actual values) ---
BATCH_ACCOUNT_NAME = 'yourbatchaccountname'
BATCH_ACCOUNT_KEY = 'yourbatchaccountkey'
BATCH_ACCOUNT_URL = 'https://yourbatchaccountname.region.batch.azure.com'
STORAGE_ACCOUNT_NAME = 'yourstorageaccountname'
STORAGE_ACCOUNT_KEY = 'yourstorageaccountkey'
STORAGE_CONTAINER_NAME_INPUT = 'input-data'
STORAGE_CONTAINER_NAME_OUTPUT = 'output-data'
POOL_ID = 'my-processing-pool'
JOB_ID = 'my-parallel-job'
VM_SIZE = 'standard_d2s_v3'
NODE_COUNT = 2 # Number of VMs in the pool
APPLICATION_EXECUTABLE_NAME = 'my_processor.exe' # Or our script e.g., 'process_data.py'

# --- Helper function to generate SAS URL for blobs (simplified) ---
# In a real application, we would use the Azure Storage SDK to generate proper SAS tokens
def get_blob_sas_url(storage_account_name, storage_account_key, container_name, blob_name):
    sas_token_placeholder = "YOUR_SAS_TOKEN" # Replace with a real SAS token generation
    return f"https://{storage_account_name}.blob.core.windows.net/{container_name}/{blob_name}?{sas_token_placeholder}"

# --- Initialize Batch Service Client ---
credentials = SharedKeyCredentials(BATCH_ACCOUNT_NAME, BATCH_ACCOUNT_KEY)
batch_client = BatchServiceClient(credentials, BATCH_ACCOUNT_URL)
# We might also initialize a blob_service_client here for managing blobs directly

# --- 1. Create a Compute Pool (if it doesn't exist) ---
try:
    print(f"Creating pool '{POOL_ID}'...")
    pool = PoolAddParameter(
        id=POOL_ID,
        vm_size=VM_SIZE,
        virtual_machine_configuration=VirtualMachineConfiguration(
            image_reference=ImageReference(
                publisher='microsoftwindowsserver',
                offer='windowsserver',
                sku='2019-datacenter', # Or a suitable Linux SKU
                version='latest'
            ),
            node_agent_sku_id='batch.node.windows amd64' # Or the appropriate Linux SKU
        ),
        target_dedicated_nodes=NODE_COUNT,
        # We can define a start_task here to install software or configure nodes
    )
    batch_client.pool.add(pool)
    print(f"Pool '{POOL_ID}' created or already exists.")
except Exception as e:
    if "PoolExists" in str(e):
        print(f"Pool '{POOL_ID}' already exists.")
    else:
        print(f"Error creating pool: {e}")
        exit()

# --- 2. Create a Job ---
print(f"Creating job '{JOB_ID}'...")
job = JobAddParameter(
    id=JOB_ID,
    pool_info={'pool_id': POOL_ID}
)
try:
    batch_client.job.add(job)
    print(f"Job '{JOB_ID}' created.")
except Exception as e:
    if "JobExists" in str(e):
        print(f"Job '{JOB_ID}' already exists.")
    else:
        print(f"Error creating job: {e}")
        # We might want to continue if the job exists to add tasks

# --- 3. Define Tasks (one for each input file) ---
# Assume these input files exist in our input storage container
input_files = ['input1.txt', 'input2.txt', 'input3.txt']
tasks = []

for i, input_file_name in enumerate(input_files):
    task_id = f"task_process_{i}"
    output_file_name = f"output_{input_file_name}"

    # Generate SAS URLs for input and output (simplified)
    input_file_sas = get_blob_sas_url(STORAGE_ACCOUNT_NAME, STORAGE_ACCOUNT_KEY, STORAGE_CONTAINER_NAME_INPUT, input_file_name)
    output_container_sas = get_blob_sas_url(STORAGE_ACCOUNT_NAME, STORAGE_ACCOUNT_KEY, STORAGE_CONTAINER_NAME_OUTPUT, "") # SAS for the container

    # Define input resource file for the task
    input_resource_file = ResourceFile(
        http_url=input_file_sas,
        file_path=input_file_name # Path where the file will be downloaded on the compute node
    )

    # Define output files to be uploaded from the node to Azure Storage
    output_file_upload_options = OutputFileUploadOptions(
        upload_condition=OutputFileUploadCondition.task_success # Upload only if task succeeds
    )
    output_file = OutputFile(
        file_pattern=output_file_name, # Relative path on the node where the output file is created
        destination=azure.batch.models.OutputFileBlobContainerDestination(
            container_url=output_container_sas
        ),
        upload_options=output_file_upload_options
    )

    # Command for the task to execute.
    # This assumes the application executable and input file are available in the task's working directory.
    # The application should write its output to output_file_name in the working directory.
    # We can also run scripts in addition to running executables
    command = f"{APPLICATION_EXECUTABLE_NAME} --input {input_file_name} --output {output_file_name}"

    task = TaskAddParameter(
        id=task_id,
        command_line=command,
        resource_files=[input_resource_file],
        output_files=[output_file],
        constraints=TaskConstraints(max_wall_clock_time=datetime.timedelta(hours=1)) # Task timeout
    )
    tasks.append(task)

# --- 4. Add Tasks to the Job ---
if tasks:
    print(f"Adding {len(tasks)} tasks to job '{JOB_ID}'...")
    # For large numbers of tasks, we should add them in chunks
    batch_client.task.add_collection(job_id=JOB_ID, value=tasks)
    print("Tasks added.")

print("\nAzure Batch job submission outlined. We can monitor progress in Azure Portal or via SDK.")

# Note: We would typically add logic here to wait for tasks/job completion,
# handle potential errors, and clean up resources (pool, job) when done.
```
- Here, we define the pool of nodes, create a job and then add a list of tasks to be executed.
- These tasks are then executed in the nodes.

```
NOTE: Azure Batch does not let us embed and execute core processing logic inline like Spark. We must package your logic in an external executable or script (e.g., .exe, .py, .sh, etc.) and run it via tasks.

For eg., In spark if we have a code block like below

rdd = sc.textFile("data.txt").map(lambda line: line.upper()).filter(...)
rdd.saveAsTextFile("output")


Then for azure batch, we must have a script file like below which we would then execute as a task

# process_data.py
with open("input.txt") as f, open("output.txt", "w") as out:
    for line in f:
        out.write(line.upper())

```

## Why This Difference?
- Azure Batch is designed as a generic HPC (High performance computing) job scheduler, not specifically for data processing.
- Spark is a data processing engine, where writing and executing distributed data logic inline is central.
