# Dropbox
## Functional requirements
- User should be able to upload a file.
- User should be able to download a file.
- Share files.

## Non functional requirements
- Available
- Scalable
- Low latency

## Entities
```
User

user_id | username | email
```
```
file_metadata

id | file_name | file_size | uploaded_by | file_type | status
```
- File: raw chunks

## Interface or API endpoints
- POST `/dropbox/v1/files`
```
Request body
{
    file,
    file_metadata
}
```
- GET `/dropbox/v1/files/:file_id`
- POST `/dropbox/v1/files/share`
```
Request body
{
    "users": users
}
```

## Data flow
### User uploads the file to our service and then our service uploads the file to S3
![HLD_1](images/HLD_1.png)
In the above approach, we are uploading the same file twice (To our service and then to S3)
