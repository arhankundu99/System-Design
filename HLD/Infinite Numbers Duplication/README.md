# Problem Statement

Design a system which gets a stream of infinite integers and output the duplicate numbers from the stream.

# Solution

<b>Remember: Please do capacity estimations first before moving to the solution.</b>

Read the problem carefully. It says integers. And an integer is 4 bytes in size (Not considering long integers). 

If we maintain a set, while inserting to the set, we would check if the set already contains the integer, if it contains, then we would print the dupicate integer, otherwise we would add the number in the set.

Now as the range of integers is bounded (-2^31 to 2^31 - 1), the set would contain at max 2 ^ 32 integers.

Now 2^32 integers would take 2^34 bytes of memory.

2^10 bytes = 1 KB
2^20 bytes = 1 MB
2^30 bytes = 1 GB

Therefore 2 ^ 34 bytes = 16 GB

So if we have a system which has atleast 16 GB of ram (Well we have to consider the overhead of set also. In this case, we are not considering), then we would be able to solve this problem.
