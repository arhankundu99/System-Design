# Video Stuff
## `Please go through video basics and ffmpeg first!`

## Transcoding flow

![transcoding_flow_1](FFmpeg/Section%203_%20Media%20Concepts/images/transcoding_flow_1.png)

## Live streaming overview of hotstar (My understanding)
![live_streaming_overflow](<images/Live Streaming Overview.png>)

- SDI Feed (High definition, uncompressed feed which has no loss of quality) is sent to a `hardware device` called `AWS Elemental Link`.
- The AWS elemental link sends the video stream to `studios` where some directors monitor the stream. The director gets stream from all camera angles and chooses the best possible camera streams for user experience. 
- Whenever the director feels the need of `inserting ads`, he would insert a `SCTE-104` message which contains information like `CUE OUT` (Move out of the main stream), `duration` (duration of the ad), `identifier` etc. SCTE-104 messages work with `SDI streams`

- `CUE-OUT` in `SCTE-104` message is used to denote moving out of main stream and show ad or any other streams whereas `CUE-IN` message is used to denote moving into the main stream from other streams such as ads.

- This feed is then sent to a `special encoder`, which then modifies the stream into streams like `ts` or `m4s` etc, and also converts the `SCTE-104` messages to `SCTE-35` messages.

```
What is the difference between SCTE-35 and SCTE-104 messages?

The two standards are equivalent and have the same functionality – they just apply to different signal types (SCTE-104 is generally applied to SDI feed and SCTE-35 is generally inserted into transport streams)

Source: https://www.cobaltdigital.com/sites/default/files/downloads/SCTE_Insertion_Methodologies_v1.0.pdf
```

- Now the `special encoder` sends the stream to `AWS Elemental MediaConnect` which sends the stream to multiple destinations. (The stream can be sent in a bucket, or to medialive for transcoding).

- Next the stream is sent to `AWS Elemental MediaLive` for transcoding to streams like `ts`, and producing streams of multiple bitrates for adaptive streaming.

- `AWS Elemental MediaLive` then sends the stream to `AWS Elemental MediaPackage` which may further break the ts stream into smaller ts chunks and prepare `master and media playlists` like `m3u8`.

- This stream is then sent to an ad processing service which then processes the `SCTE 35 markers`, calls an ad server to give relavant ads (The ad params in the request may contain the language of the stream, the device in which the stream would be going, the duration etc).

- The ad server then sends relavant ad and the ad processing service then stiches the ads in the live stream. It would create the ts files for each bitrate and modify the `SCTE-35` message with the ad info. (This ad info may be used by the player to show `clickable links` while the ad plays).

- This stream is then sent to `AWS Elemental MediaStore` which is a bit costly than S3 because it also provides a caching layer. The MediaStore is generally connected with a `CDN`.


## SSAI and CSAI
SSAI (Server side ad insertion) is generally preferred in the live stream because ads are stiched in the video stream.
- An ad blocker would not be able to detect this.
- The ads are already stitched, client would not fetch the ads, so less chance of buffering.
- But this reduces the amount of `personalisation` of ads. 


CSAI (Client side ad insertion) can be preferred in VOD to show personalised ads.
- But this would not work if ad blocker is enabled.
- `Can show personalised ads`


## Hands on with SCTE-35 messages
- Open any live stream and go to the network tab.
- Search for `m3u8` (If the stream is through DASH protocol, then search for `mpd`).
- Scroll down and we would see a SCTE-35 message which is encoded. 
- Dowload the `ts` streams above and below the `SCTE-35` message. (Most probably they are `ad streams`).
- Copy the SCTE message and go to https://myvideotools.com/scte35parser/ and paste the message to see the decoded data

Eg SCTE-35 data:
```
{
  "info_section": {
    "table_id": "0xfc",
    "section_syntax_indicator": false,
    "private": false,
    "sap_type": "0x3",
    "sap_details": "No Sap Type",
    "section_length": 56,
    "protocol_version": 0,
    "encrypted_packet": false,
    "encryption_algorithm": 0,
    "pts_adjustment_ticks": 207000,
    "pts_adjustment": 2.3,
    "cw_index": "0x0",
    "tier": "0xfff",
    "splice_command_length": 5,
    "splice_command_type": 6,
    "descriptor_loop_length": 34,
    "crc": "0xc067da6b"
  },
  "command": {
    "command_length": 5,
    "command_type": 6,
    "name": "Time Signal",
    "time_specified_flag": true,
    "pts_time": 18699.48,
    "pts_time_ticks": 1682953200
  },
  "descriptors": [
    {
      "tag": 2,
      "descriptor_length": 32,
      "name": "Segmentation Descriptor",
      "identifier": "CUEI",
      "components": [],
      "segmentation_event_id": "0x400005e",
      "segmentation_event_cancel_indicator": false,
      "program_segmentation_flag": true,
      "segmentation_duration_flag": true,
      "delivery_not_restricted_flag": false,
      "web_delivery_allowed_flag": false,
      "no_regional_blackout_flag": false,
      "archive_allowed_flag": false,
      "device_restrictions": "Restrict Group 0",
      "segmentation_duration": 10,
      "segmentation_duration_ticks": 900000,
      "segmentation_message": "Chapter Start",
      "segmentation_upid_type": 14,
      "segmentation_upid_type_name": "ADS Info",
      "segmentation_upid_length": 12,
      "segmentation_upid": "MPH000007481",
      "segmentation_type_id": 32,
      "segment_num": 1,
      "segments_expected": 1
    }
  ]
}
```

In the `descriptors` section, we would see the ad related meta data and some id like `segmentation_upid` which may be used to fetch ad data (like links, image, description) from an ad server.

Also using the link, whenever an user clicks on the ad, the client can make a request to the ad link `along with user details such as location, timestamp, device details, media id etc` for better ads tracking.
