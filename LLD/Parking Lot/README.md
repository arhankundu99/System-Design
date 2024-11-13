# Parking Lot
## Requirements
- Application where users can view and choose parking lots (The parking lots should not be full).
- A parking lot manager can add / edit / update their parking lot after verification.
- Parking lots have slots.
- User can book a parking slot at the entrance and pay when they exit the parking lot.
- The parking lot should support per hour pricing model.

## QPS estimations
```
- 100K monthly users
- 50% daily: 50K daily users
- Parking request QPS: 50K / 86400 = 0.6 req / s
```

## Entities
- User
```
user_id | name | email
```

- Parking Lot
```
parking_lot_id | name | description | lat | long | address | status
```

- Slot
```
slot_id | parking_lot_id | floor | name | vehicle_types_supported (Light / Heavy) | status
```

- Booking
```
booking_id | user_id | parking_lot_id | slot_id | status | start_time | end_time
```

- Payments
```
payment_id | booking_id | price
```

## APIs
- View parking lots
```
GET /smart_park/v1/parking_lots?lat={lat}&long={long}&page_size={page_size}&page_number={page_number}

{
    code,
    message,
    data: {
        parking_lots: [
            {
                id,
                name,
                images: [],
            }
        ]
    }
}
```

- View a parking lot
```
GET /smart_park/v1/parking_lots/:parking_lot_id

{
    code,
    message,
    data: {
        id,
        name,
        images,
        address,
        lat,
        long,
        desc,
        slots
    }
}
```

- Book a parking_slot

```
POST /smart_park/v1/orders/create

{
    code,
    message,
    data: {
        orderId,
        slotId,
        status: ACTIVE
    }
}
```

- Complete booking (User leaves the parking lot)

```
Calculate fare
GET /smart_park/v1/orders/:orderId/fare

User does the payment

POST /smart_park/v1/orders/complete
Body: {
    orderId,
    paymentId
}

When user clicks the pay button, the backend would first check if the fare is still valid. (The user can maybe stay there for a longer period and then try to pay)

```

## Classes
```
ParkingLot

id,
name,
desc,
lat,
long,
slots: List<Slot>
```

```
slot

id,
name,
floor,
parking_id,
vehicle_type: VehicleType,
status
```

```
Enum VehicleType

HEAVY_VEHICLE
LIGHT_VEHICLE
```

```
Booking

id
user_id,
parking_lot_id,
slot_id,
status,
start_time,
end_time
```

```
Payments

payment_id,
booking_id,
total_price,
status
```


```
ParkingLotRepository

- addParkingLot(ParkingLot)
- updateParkingLot(ParkingLot)
- deleteParkingLot(id)
- getParkingLot(id): ParkingLot
- getParkingLots(address, pageSize, pageNum): List<Partial<ParkingLot>>
- getAvailableParkingLots(address, pageSize, pageNum): List<Partial<ParkingLot>>
- addSlot(Slot, parkingLotId)
- getAvailableSlots(parkingLotId)
- removeSlot(slotId)
- updateSlot(slotId, Partial<Slot>)
```

```
BookingRepository

- createBooking(Booking)
- updateBooking(Booking)
```

```
PaymentRepository

- createPayment(Payment)
- updatePayment(Payment)
```

```
ParkingLotService 

- addParkingLot(name, lat, long, address, vehicle_type): Creates a ParkingLot object and calls ParkingLotRepository

- Likewise other methods
```

```
ParkingLotController

- Input validation
- Calls the ParkingLotService
- Sends responses
```




