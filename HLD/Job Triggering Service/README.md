# Job Triggering Service

## Problem statement
You have a job training service which takes job details, model name, dataset (Already uploaded / predefined) and it triggers the jobs. You need to create a job triggering service which users would use to trigger a job.

## Functional requirements
1. User can request to trigger a job
2. User can view all the jobs that the user has triggered
3. User can view a particular job details (status like submitted, cancelled, failure, successful)
4. User can cancel a job
5. There are projects which user is assigned to and user can create jobs in the assigned projects only

## Non functional requirements
1. Low latency
2. Scalable


## Entities
1. `permissions: {userId, projectId}`
2. `jobs: {id, name, model, datasetMetadata, userId, projectId, createdAt, updatedAt, status (SUBMITTED, STARTED, SUCCESSFUL, CANCELLED, FAILED), comments}`


## API Endpoints

1. POST `jts/api/v1/jobs`
```
Request headers: access token
Request body: { name, model, datasetMetadata, projectId }
Response: { id, name, status: SUBMITTED / FAILED }
```

2. GET `jts/api/v1/jobs`
```
Request headers: access token
Response: { [id, name, status] }
```

3. GET `jts/api/v1/jobs/:jobId`
```
Request headers: access token
Response: { id, name, status, model, datasetMetadata, projectId, createdAt, updatedAt, comments }
```

4. PATCH `jts/api/v1/jobs/:jobId`
```
Request headers: access token
Request body: { action: CANCEL }
Response: { id, name, status: CANCELLING }
```


## Design

<img width="1460" height="988" alt="image" src="https://github.com/user-attachments/assets/9da851eb-579b-4bc1-a22a-606bc6971357" />

<img width="1332" height="850" alt="image" src="https://github.com/user-attachments/assets/1381823e-f4b5-4459-ab7b-efda77d94593" />

<img width="1708" height="802" alt="image" src="https://github.com/user-attachments/assets/23642269-cb4b-4bf0-ae78-daae5979773c" />


