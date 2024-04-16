# Questions
1. How does multicast work with routing?
2. Suppose device A is connected to the network and device A now sends a packet to the internet. The wifi router maintains a NAT entry like this 
```
deviceA_ip:deviceA_port:wifi_assigned_port:WAN_ip
```
But now device A disconnects and device B now connects and gets assigned the same port which was assigned to device A. Now if the router gets response which was intended for device A, what does wifi router do to ensure that the packet does not sent to the wrong device B. 

I have read in many places that routers maintain the tcp sequence numbers for session identification. So the router gets a packet which was for device A, but now the NAT mapping suggests for device B, the router would look into the tcp sequence number of the received packet, and see that there is difference between this sequence and the sequence of device B and drop the packet.

But what does it do for UDP. Also router is level 3 device. For it to get the sequence number, it has to operate in level 4. So how does it work?

3. What is anycast and how does it work?

4. How does RTP stream work in the `ingestion` side? Also it is based on UDP, so what are the error correction mechanisms which are implemented in this protocol?
