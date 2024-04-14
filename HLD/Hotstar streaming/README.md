# Live Streaming Workflow:

## Prerequiste: Go through the video content

## Content Acquisition and Processing:

### Live Feed Acquisition
Hotstar receives a live video feed from the BCCI. This feed typically arrives via satellite links or dedicated fiber connections to ensure high quality and reliability.


### Transcoding
The live feed is processed on servers (like Amazon EC2 instances) running software like ffmpeg. This process involves:

- Demultiplexing: Separating audio and video streams.
    
- Decoding: Converting compressed audio and video streams into raw frames.
    
- Filtering: Adjusting video for different bitrates, resolutions, and possibly adding subtitles or additional audio tracks for different languages.

- Encoding: Recompressing the audio and video streams into multiple formats/bitrates for adaptive streaming.
    
- Multiplexing: Re-combining the encoded audio and video streams into a container format like MPEG-TS.
    
- Segmentation for HLS: The continuous video stream is segmented into short, typically 10-second chunks (.ts files). Alongside, HLS manifest files (master playlist and media playlists) are generated to instruct the player on how to adaptively stream the content.

## Content Delivery:
- Uploading to CDN: The manifest files and video segments are uploaded to a CDN, which replicates this content across its global network of edge servers.

- Unicast Delivery: When a client requests to watch the stream, the CDN uses a unicast approach to deliver the video segments. The player dynamically requests segments based on the current network conditions, supported by the media manifest files.

## Viewer Metrics:

### Concurrent Viewers:
Real-Time Monitoring: Hotstar can monitor the number of active connections to the CDN to gauge concurrent viewership. This metric reflects the number of users watching the stream at any given moment.


### Total Views:
Connection Counting: By tallying the total number of connections made to the CDN for the stream, Hotstar can estimate the total views. This includes repeat views by the same users.

## Implementation Considerations:

### Scalability
Using CDN and HLS allows Hotstar to scale the service to handle very high numbers of simultaneous viewers by reducing the load on origin servers and minimizing latency.


- Quality of Service: Adaptive bitrate streaming (HLS) ensures that viewers receive the best possible video quality according to their internet speed and viewing device capabilities.

- Analytics and Monitoring: Advanced analytics tools are integrated to monitor viewer engagement, quality of experience, and service performance in real-time.


## The below is an overview of how live streaming works

![video_streaming_overview](<images/Video streaming overview.png>)