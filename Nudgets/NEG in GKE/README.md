# Network endpoint group in Google Kubernetes engine

- This is crucial to check before deploying your applications. As this enables the load balancers to serve traffic to your "scaled pods".

- When your application experiences increased demand, GKE can automatically scale the number of pods to maintain performance and manage the load effectively.

- As new pods are created during scaling, NEGs are responsible for automatically registering these new pods as endpoints. This registration includes capturing the IP addresses of the new pods.

- Google Cloud Load Balancers use the information from NEGs to distribute incoming traffic across all available pods.