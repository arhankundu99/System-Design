# Hotel Booking System Design

## Functional and non functional requirements
The first step to solving a system design problem is to note down the functional and non functional requirements.

<table>
  <tr>
    <th>
      Functional requirements
    </th>
    <th>
      Non Functional requirements
    </th>
  </tr>
  
  <tr>
    <td>
      Hotel managers should be able to register their hotels and rooms.
    </td>
    <td>
      Low Latency.
    </td>
  </tr>

  <tr>
    <td>
      Hotel managers should be able to update the rooms, photos, prices etc.
    </td>
    <td>
      High Availibility.
    </td>
  </tr>
  
  <tr>
    <td>
      Users should be able to search for hotels and filter hotels based on price, dates etc.
    </td>
    <td>
      High Consistency because booking and payment is involved.
    </td>
  </tr>
  
   <tr>
    <td>
      Users should be able to book the hotels.
    </td>
    <td>
    </td>
  </tr>
 
   <tr>
    <td>
      Users / Managers should be able to view their bookings.
    </td>
    <td>
    </td>
  </tr>

</table>

## Design
![Hotel Booking HLD](https://raw.githubusercontent.com/arhankundu99/System-Design/main/Hotel%20Booking%20System/images/Hotel%20Booking%20Design%20HLD.png)

![Schemas and APIs](https://raw.githubusercontent.com/arhankundu99/System-Design/main/Hotel%20Booking%20System/images/Hotel%20Booking%20Design%20APIs%20and%20Schemas.png)
