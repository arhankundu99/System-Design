# External mergesort

## Problem statement
Suppose we have a list of numbers which are bigger than our RAM. How would we sort this list.

Refer: [External MergeSort](https://web.archive.org/web/20150208064321/http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX1.htm)

## Example
```
Suppose memory is 12 bytes (it can take 3 integers at max)
And list = [17, 3, 29, 56, 24, 18, 4, 9, 10, 6, 45, 36, 11, 43]


Create a file tl_1 with first 3 integers: [17, 3, 29]
Sort tl_1: [3, 17, 29]

Create a file tl_2 with next 3 integers: [56, 24, 18]
Sort tl_2: [18, 24, 56]

Create a file tl_3 with next 3 integers: [4, 9, 10] (Sorted)

Create a file tl_4 with next 3 integers: [6, 36, 45]

Create a file tl_5 with next integers: [11, 43]

Now merge files tl_1 and tl_2 using 2 pointers into tl_1_2 = [3, 17, 18, 24, 29, 56]

Merge files tl_3 and tl_4 into tl_3_4 = [4, 6, 9, 10, 36, 45]
and tl_5 = [11, 43]

Now merge files tl_1_2 and tl_3_4 into tl_1_2_3_4 = [3, 4, 6, 9, 10, 17, 18, 24, 29, 36, 45, 56]

Now merge tl_1_2_3_4 and tl_5 = [3, 4, 6, 9, 10, 11, 17, 18, 24, 29, 36, 43, 45, 56]
```

Some of the applications include removing duplicates from a file.