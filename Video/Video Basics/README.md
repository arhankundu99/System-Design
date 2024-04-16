# Video Basics

let's first clarify some basics.

## Resolution
A resolution of 1920 * 1080 pixels mean that there are 1920 pixels horizontally and 1080 pixels vertically. This resolution is also known as FHD (Full high definition). 

Common Aspect ratio of FHD (1920 / 1080), 4k (3840 / 2160), HD (1280 / 720) is 16 / 9.

## FPS
The number of images (frames) displayed per second.

## How much data does a pixel require?
This depends on the color that the display supports. This display might support 8 bit color, 16 bit color, 24 bit, 32 bit color. (Notice these are in multiples of 8 because it would be easier to store in memory.)

Now for eg., in 16 bit color, depending on the situation (Note that this behaviour is dynamic), the pixel might have 5 bits allocated for red, 6 bits for green, and 5 bits again for blue. 

The more bits a pixel can support, the more color ranges can be supported. For eg., 24 bit color would have more spectrum of color than 16 bit color.

## Bitrates
Now a bitrate is amount of bits required per second to play a video.
Let's say we are streaming a video in FHD (1920 * 1080 p), and we have 16 bit color display, and the FPS is 30.

16 bit color display means, each pixel requires 16 bits of data. So the bitrate needed to view the video seamlessly is 1920 * 1080 * 16 * 30 = 995328000 bits = 949.22 Mbps = 118.65 MBps

The high bitrate calculated above is based on a theoretical calculation, assuming no compression or encoding. In reality, video content, especially for streaming, is compressed to make it more efficient for transmission over the internet. Video compression reduces the amount of data required to represent a video while attempting to maintain acceptable quality.

The actual bitrate used for streaming FHD content is significantly lower than the raw, uncompressed bitrate because of compression. The bitrate required for high-quality streaming (10-20 Mbps Generally) takes into account the compression efficiency, which can vary depending on the codec and settings used.

Common video codecs like H.264, H.265 (HEVC), and VP9 are highly efficient at compressing video content while preserving quality. These codecs employ various techniques like temporal and spatial compression, quantization, and more to reduce the data size. They achieve high compression ratios, allowing FHD video to be streamed smoothly at much lower bitrates without a significant loss in quality.

So, while the theoretical, uncompressed bitrate for FHD video may be very high, the actual streaming bitrate is much lower due to video compression. The 10-20 Mbps range for high-quality streaming takes this compression into account and is suitable for most viewers with standard internet connections.

## `Playout`
Playout refers to the series of processing steps where the raw feed is transformed into a final stream format that is suitable for consumption by clients. This includes transcoding, adding graphics, and other necessary adjustments to ensure the stream is compatible and optimized for different devices and network conditions.

