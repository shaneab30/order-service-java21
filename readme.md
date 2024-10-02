ORDER SERVICE

clean install spring-boot:run

API SAVE ORDER - POST
http://localhost:8083/order

REQUEST
{
  "customerId": 5,
  "orderDate": "2024-08-31T10:00:00",
  "status": "Pending",
  "totalPrice": 20.00,
  "productOrders": [
    {
      "productId": 18,
      "quantity": 50,
      "price": 15.00
    }, {
      "productId": 17,
      "quantity": 30,
      "price": 5.00
    },{
      "productId": 16,
      "quantity": 20,
      "price": 5.00
    }
  ]
}

RESPONSE
Order created successfully

API UPDATE ORDER - PUT
http://localhost:8083/order/{orderid}

REQUEST
{
  "id": 31,
  "customerId": 5,
  "orderDate": "2024-08-31T10:00:00",
  "status": "Pending",
  "totalPrice": 20.00,
  "productOrders": [
    {
      "productId": 18,
      "quantity": 50,
      "price": 15.00
    }, {
      "productId": 17,
      "quantity": 30,
      "price": 5.00
    },{
      "productId": 16,
      "quantity": 20,
      "price": 5.00
    }
  ]
}

RESPONSE
Order updated successfully

API DELETE ORDER - DELETE
http://localhost:8083/order/{orderid}

RESPONSE
Order deleted successfully


API GET ORDER BY ID - GET
http://localhost:8083/order/{orderid}

RESPONSE
{
  "id": 31,
  "customer": {
    "id": 5,
    "firstname": "aasdc",
    "lastname": "tesasdt",
    "email": "testasdasda@gmail.com",
    "address": "test",
    "telephone": "081298004285",
    "birthday": "2023-08-31"
  },
  "orderDate": "2024-08-31T10:00:00",
  "status": "Pending",
  "totalPrice": 20.00,
  "shippingAddress": "test",
  "productOrders": [
    {
      "id": 43,
      "product": {
        "id": 17,
        "name": "makarizo shampo",
        "brand": "makarizo",
        "stock": 12,
        "category": "shampo",
        "price": 12500.00,
        "status": "Active",
        "sku": "MKZ"
      },
      "quantity": 30,
      "price": 5.00
    },
    {
      "id": 44,
      "product": {
        "id": 16,
        "name": "zxczxcz",
        "brand": "dove",
        "stock": 12,
        "category": "shampo",
        "price": 12500.00,
        "status": "asd",
        "sku": "QWE1290"
      },
      "quantity": 20,
      "price": 5.00
    },
    {
      "id": 34,
      "product": {
        "id": 18,
        "name": "makarizo conditioner",
        "brand": "makarizo",
        "stock": 12,
        "category": "conditioner",
        "price": 12500.00,
        "status": "Active",
        "sku": "MKZ1"
      },
      "quantity": 50,
      "price": 15.00
    }
  ]
}

API GET ORDER BY ALL - GET
http://localhost:8083/order

[
  {
    "id": 11,
    "customer": {
      "id": 5,
      "firstname": "aasdc",
      "lastname": "tesasdt",
      "email": "testasdasda@gmail.com",
      "address": "test",
      "telephone": "081298004285",
      "birthday": "2023-08-31"
    },
    "orderDate": "2023-08-31T10:00:00",
    "status": "Pending",
    "totalPrice": 12.00,
    "shippingAddress": "test",
    "productOrders": [
      {
        "id": 13,
        "product": {
          "id": 4,
          "name": "zxczxcz",
          "brand": "dove",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "asd",
          "sku": "QWE-zx"
        },
        "quantity": 2,
        "price": 6.00
      },
      {
        "id": 14,
        "product": {
          "id": 17,
          "name": "makarizo shampo",
          "brand": "makarizo",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ"
        },
        "quantity": 1,
        "price": 6.00
      }
    ]
  },
  {
    "id": 12,
    "customer": {
      "id": 5,
      "firstname": "aasdc",
      "lastname": "tesasdt",
      "email": "testasdasda@gmail.com",
      "address": "test",
      "telephone": "081298004285",
      "birthday": "2023-08-31"
    },
    "orderDate": "2023-08-31T10:00:00",
    "status": "Pending",
    "totalPrice": 12.00,
    "shippingAddress": "test",
    "productOrders": [
      {
        "id": 15,
        "product": {
          "id": 4,
          "name": "zxczxcz",
          "brand": "dove",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "asd",
          "sku": "QWE-zx"
        },
        "quantity": 2,
        "price": 6.00
      },
      {
        "id": 16,
        "product": {
          "id": 17,
          "name": "makarizo shampo",
          "brand": "makarizo",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ"
        },
        "quantity": 1,
        "price": 6.00
      }
    ]
  },
  {
    "id": 25,
    "customer": {
      "id": 3,
      "firstname": "tlkjba",
      "lastname": "zxc",
      "email": "tejbmjasda@gmail.com",
      "address": "test",
      "telephone": "0812984724",
      "birthday": "2023-08-31"
    },
    "orderDate": "2023-08-31T10:00:00",
    "status": "Pending",
    "totalPrice": 12.00,
    "shippingAddress": "test",
    "productOrders": [
      {
        "id": 21,
        "product": {
          "id": 6,
          "name": "zxczxcz",
          "brand": "dove",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "asd",
          "sku": "QWE-zpo"
        },
        "quantity": 2,
        "price": 6.00
      },
      {
        "id": 22,
        "product": {
          "id": 17,
          "name": "makarizo shampo",
          "brand": "makarizo",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ"
        },
        "quantity": 1,
        "price": 6.00
      }
    ]
  },
  {
    "id": 27,
    "customer": {
      "id": 3,
      "firstname": "tlkjba",
      "lastname": "zxc",
      "email": "tejbmjasda@gmail.com",
      "address": "test",
      "telephone": "0812984724",
      "birthday": "2023-08-31"
    },
    "orderDate": "2023-08-31T10:00:00",
    "status": "Done",
    "totalPrice": 12.00,
    "shippingAddress": "test",
    "productOrders": [
      {
        "id": 25,
        "product": {
          "id": 18,
          "name": "makarizo conditioner",
          "brand": "makarizo",
          "stock": 12,
          "category": "conditioner",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ1"
        },
        "quantity": 2,
        "price": 6.00
      },
      {
        "id": 26,
        "product": {
          "id": 17,
          "name": "makarizo shampo",
          "brand": "makarizo",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ"
        },
        "quantity": 1,
        "price": 6.00
      }
    ]
  },
  {
    "id": 28,
    "customer": {
      "id": 3,
      "firstname": "tlkjba",
      "lastname": "zxc",
      "email": "tejbmjasda@gmail.com",
      "address": "test",
      "telephone": "0812984724",
      "birthday": "2023-08-31"
    },
    "orderDate": "2023-08-31T10:00:00",
    "status": "Done",
    "totalPrice": 12.00,
    "shippingAddress": "test",
    "productOrders": [
      {
        "id": 27,
        "product": {
          "id": 18,
          "name": "makarizo conditioner",
          "brand": "makarizo",
          "stock": 12,
          "category": "conditioner",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ1"
        },
        "quantity": 2,
        "price": 6.00
      },
      {
        "id": 28,
        "product": {
          "id": 17,
          "name": "makarizo shampo",
          "brand": "makarizo",
          "stock": 12,
          "category": "shampo",
          "price": 12500.00,
          "status": "Active",
          "sku": "MKZ"
        },
        "quantity": 1,
        "price": 6.00
      }
    ]
  }
]

