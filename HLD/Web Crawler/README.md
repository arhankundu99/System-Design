# Web Crawler

## Requirements
Design a web crawler which crawls the webpages and stores the content. The content can be used for search engine indexing, LLM training etc

## Non functional requirements
- Politeness: Our crawler should not flood a domain with requests that may be considered as DDOS.
- Available
- Scalable
- Fault tolerant

## Uses
- Search engine indexing
- Web archive
- Data mining (Getting data related to a specific topic)

## QPS and capacity estimation
```
- 1 Billion pages to be crawled every month
- QPS: 1 B / (30 * 86400) = 385 QPS
- Peak QPS: 2 * QPS = 800 QPS
- Assuming each page size is 500 KB
- Per month data to be stored = 500 * 1000 GB = 500 TB
- Data is to be stored for 2 years = 12 PB
```

## Entities
Url and content.

## Interface
No public facing apis here. Input is a bunch of seed urls and output is the text which is stored.

## Data flow
- Take seed urls from URL frontier which is a queue.
- Get ips from dns 
- Fetch html content
- Parse them and check if the content was not previously seen. If it was seen, then discard.
- Save the content.
- Get the links from the content.
- Add non crawled links in the url frontier.
- Repeat.
