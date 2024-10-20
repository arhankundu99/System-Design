# Back of the envelop estimations

## Storage
<table border="1" cellpadding="5" cellspacing="0">
  <thead>
    <tr>
      <th>Power of 2</th>
      <th>Approximation</th>
      <th>Unit</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>2<sup>10</sup></td>
      <td>Thousand</td>
      <td>1 KB (Kilobyte)</td>
    </tr>
    <tr>
      <td>2<sup>20</sup></td>
      <td>Million</td>
      <td>1 MB (Megabyte)</td>
    </tr>
    <tr>
      <td>2<sup>30</sup></td>
      <td>Billion</td>
      <td>1 GB (Gigabyte)</td>
    </tr>
    <tr>
      <td>2<sup>40</sup></td>
      <td>Trillion</td>
      <td>1 TB (Terabyte)</td>
    </tr>
  </tbody>
</table>

## QPS and Storage estimations

- Estimate QPS and Storage for twitter tweet feature.

### Assumptions
```
- Monthly active users: 100 Million 
- 10 % active users daily: 10 Million 
- Users tweet 2 posts per day: 20 Million tweets
- Tweet can contain either text or text and media both.
- Assuming average text size is 100 bytes and average media size is 1 MB.
- Assuming 10% of tweets contain media and text both, remaining consists of only text.
- Assuming we store the data for 10 years.
```

### QPS estimations for tweet feature
```
20 Million tweets / day = 20 * 10^6 / (24 * 60 * 60) = (20 * 10 ^ 4) / 24 * 6 * 6 = 230 QPS
```

### Storage estimations for tweet feature
```
0.9 * 20 * 10^6 * 10^-4 + 0.1 * 20 * 10^6 * 1 = 1800 MB + (2 * 10^6) MB = 2000 GB = 2 TB per day

For 10 years: 2 TB * 365 * 10 = 7300 TB = 7.3 PB
```


