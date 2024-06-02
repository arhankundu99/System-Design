## Protos vs JSON
As we all know, JSON is used everywhere in RPC calls. JSON is flexible, easy to use and human readable. As a result, JSON is also fault prone (For eg., the json can contain bad keys) and also takes up more space (More on this later!)

To efficiently transmit information between 2 servers, we need a well defined contract and also something that would take up less space.

### Protos
Protocol Buffers require a `.proto` file which defines the structure of the data, including specific data types for each field.

For eg.,
```
message User {
  int32 ID = 1;
  string Name = 2;
  string Email = 3;
  bool IsActive = 4;
}
```
The above is a user structure defined in a proto file. Notice the `tags` for each field. While transmitting the payload, instead of sending the `keys and values` of the `User` object, we send the `tag and values`. The receiver receives this payload, `deserialises` it and because it knows the payload contract, it would know the corresponding key from the tag.

### How to use protos
- We define the structure of the payload in a `proto` file, and compile it using `proto compiler` to generate code in any language like `go`, `javascript` etc.

- Generate the code using the below command.
```
protoc --python_out=. person.proto
```

- The generated code has methods like getters, serialise (into binary string), deserialise etc.

Generated python class
```python
class Person(_message.Message):
    DESCRIPTOR = ...

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.name = ""
        self.id = 0
        self.email = ""
        self.is_active = False

    def SerializeToString(self):
        # Serializes the message to a binary string.
        ...

    def ParseFromString(self, data):
        # Parses a binary string to fill the message fields.
        ...
```


### Pros and cons
While protobuffs make transmitting data in less size and fault tolerant, it also makes it difficult to use. Because we would have to recompile for a single change in proto file and it is not human readable.

GRPC makes use of protobuffs.
