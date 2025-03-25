## **1. POST Requests**

### **1.  _Products_**

http://localhost:8080/api/products

```json
{
  "id": "886d47c0-5e13-4e08-bd8b-a679114f0b3a",
  "name": "Classic Cotton T-Shirt",
  "description": "A comfortable and stylish cotton t-shirt for men.",
  "price": 599.99,
  "brand": "FashionWear",
  "rating": 4.5,
  "createdAt": "2025-03-24T22:48:44.4927984",
  "updatedAt": "2025-03-24T22:48:44.4927984",
  "productVariants": [
    {
      "id": "5345d24b-4217-404e-bdb4-3b3b5af50ea4",
      "color": "Black",
      "size": "M",
      "stockQuantity": 50
    },
    {
      "id": "ad109bb1-a5f1-4ab1-a93f-3538086352f4",
      "color": "White",
      "size": "L",
      "stockQuantity": 40
    }
  ],
  "resources": [
    {
      "id": "4cff60e9-e912-46bd-9c16-a6088ad50797",
      "name": "T-Shirt Front View",
      "url": "https://example.com/images/tshirt-front.jpg",
      "isPrimary": true,
      "type": "IMAGE"
    },
    {
      "id": "56401d38-576d-4d51-bcae-3ee7f770e067",
      "name": "T-Shirt Back View",
      "url": "https://example.com/images/tshirt-back.jpg",
      "isPrimary": false,
      "type": "IMAGE"
    }
  ],
  "newArrival": false
}
```

Before making this `POST` request to add a new product, the following values must already exist in your database:

### **1. Categories Table (`categories`)**

- The **"Men"** category must exist with **ID**:  
  **`12881de6-6b64-44a9-93a2-bdbe69f1ae51`**

### **2. Category Types Table (`category_types`)**

- The **"T-shirts"** category type must exist under "Men" with **ID**:  
  **`8b14bb08-ef02-4834-9e12-8a10079c72ff`**

### **3. Resources Table (`resources`)** *(if referenced beforehand)*

- The URLs used in `productResources` should be accessible and, if stored in a database, should be valid.

### **4. Product Variants Table (`product_variants`)** *(if foreign key constraints exist)*

- If there are strict relationships, ensure that `color`, `size`, and `stockQuantity` fields match the expected values
  in your schema.

---

### **Database Pre-check Query (if applicable in SQL)**

To confirm that the necessary category and category type exist, run:

```sql
SELECT * FROM categories WHERE id = '12881de6-6b64-44a9-93a2-bdbe69f1ae51';
SELECT * FROM category_types WHERE id = '8b14bb08-ef02-4834-9e12-8a10079c72ff';
```

If both queries return results, your request should work correctly.

---

### **Summary**

✅ **Must exist before request**:

1. **Category ("Men")** with `ID = 12881de6-6b64-44a9-93a2-bdbe69f1ae51`
2. **Category Type ("T-shirts")** with `ID = 8b14bb08-ef02-4834-9e12-8a10079c72ff`
3. (Optional) **Existing Resources & Product Variants**, depending on DB constraints.

If these values exist, your API request should succeed!

Here are multiple valid `POST` request bodies to add different products under various categories and category types.

---

### **1️⃣ Men's T-Shirt**

```json
{
  "name": "Casual Cotton T-Shirt",
  "description": "Premium quality cotton T-shirt with a soft finish.",
  "price": 499.99,
  "brand": "Nike",
  "isNewArrival": true,
  "rating": 4.5,
  "categoryId": "12881de6-6b64-44a9-93a2-bdbe69f1ae51",
  "categoryTypeId": "8b14bb08-ef02-4834-9e12-8a10079c72ff",
  "productResources": [
    {
      "name": "Front View",
      "type": "image",
      "url": "https://example.com/images/tshirt-front.jpg",
      "isPrimary": true
    },
    {
      "name": "Back View",
      "type": "image",
      "url": "https://example.com/images/tshirt-back.jpg",
      "isPrimary": false
    }
  ],
  "variants": [
    {
      "color": "Black",
      "size": "M",
      "stockQuantity": 50
    },
    {
      "color": "White",
      "size": "L",
      "stockQuantity": 30
    }
  ]
}
```

---

### **2️⃣ Women's Dress**

```json
{
  "name": "Elegant Evening Dress",
  "description": "A stylish and elegant dress perfect for evening occasions.",
  "price": 1499.99,
  "brand": "Zara",
  "isNewArrival": true,
  "rating": 4.7,
  "categoryId": "e2711512-763f-4393-8bfc-8a48abbafbec",
  "categoryTypeId": "460dfa4f-8b3d-4f21-8d10-1b0af55b6b9c",
  "productResources": [
    {
      "name": "Model Wearing Dress",
      "type": "image",
      "url": "https://example.com/images/dress-model.jpg",
      "isPrimary": true
    }
  ],
  "variants": [
    {
      "color": "Red",
      "size": "S",
      "stockQuantity": 20
    },
    {
      "color": "Blue",
      "size": "M",
      "stockQuantity": 15
    }
  ]
}
```

---

### **3️⃣ Men's Hoodie**

```json
{
  "name": "Winter Fleece Hoodie",
  "description": "Warm and cozy fleece hoodie for cold weather.",
  "price": 1999.99,
  "brand": "Adidas",
  "isNewArrival": false,
  "rating": 4.8,
  "categoryId": "12881de6-6b64-44a9-93a2-bdbe69f1ae51",
  "categoryTypeId": "b701a1b9-20f0-490b-b858-eca82536f6e8",
  "productResources": [
    {
      "name": "Hoodie Front",
      "type": "image",
      "url": "https://example.com/images/hoodie-front.jpg",
      "isPrimary": true
    }
  ],
  "variants": [
    {
      "color": "Gray",
      "size": "L",
      "stockQuantity": 40
    },
    {
      "color": "Black",
      "size": "XL",
      "stockQuantity": 35
    }
  ]
}
```

---

### **4️⃣ Women's Jacket**

```json
{
  "name": "Stylish Leather Jacket",
  "description": "A classic leather jacket for a fashionable winter look.",
  "price": 2999.99,
  "brand": "Gucci",
  "isNewArrival": true,
  "slug": "leather-jacket",
  "rating": 4.9,
  "categoryId": "e2711512-763f-4393-8bfc-8a48abbafbec",
  "categoryTypeId": "323d63c2-a562-4a39-b616-7665d0e286e6",
  "productResources": [
    {
      "name": "Jacket Display",
      "type": "image",
      "url": "https://example.com/images/jacket.jpg",
      "isPrimary": true
    }
  ],
  "variants": [
    {
      "color": "Brown",
      "size": "M",
      "stockQuantity": 25
    }
  ]
}
```

---

### **5️⃣ Men's Jeans**

```json
{
  "name": "Slim Fit Denim Jeans",
  "description": "High-quality slim-fit denim jeans for men.",
  "price": 1799.99,
  "brand": "Levi's",
  "isNewArrival": false,
  "rating": 4.6,
  "categoryId": "12881de6-6b64-44a9-93a2-bdbe69f1ae51",
  "categoryTypeId": "3a63d077-45d4-4b07-b43b-8bd4e55568d2",
  "productResources": [
    {
      "name": "Denim Jeans",
      "type": "image",
      "url": "https://example.com/images/jeans.jpg",
      "isPrimary": true
    }
  ],
  "variants": [
    {
      "color": "Blue",
      "size": "32",
      "stockQuantity": 60
    },
    {
      "color": "Black",
      "size": "34",
      "stockQuantity": 50
    }
  ]
}
```

---

### **Conclusion**

- These request bodies contain **valid category and categoryType IDs** from your database.
- They follow the **expected DTO structure** (`ProductDto`).
- All fields such as `price`, `rating`, `brand`, `variants`, and `productResources` are properly filled.
- Image URLs are placeholders—you should replace them with actual image links.

---

## **2. GET Request**

For a `GET` request to fetch products by category, you should use the `categoryId` key from your database. Here are the valid `categoryId` values:

### **Category IDs for GET Request**
1. **Men** → `"categoryId": "12881de6-6b64-44a9-93a2-bdbe69f1ae51"`
2. **Women** → `"categoryId": "e2711512-763f-4393-8bfc-8a48abbafbec"`

---

### **Example GET Request to Fetch Products by Category**
#### **1️⃣ Fetch all Men's Products**
```
GET /products?categoryId=12881de6-6b64-44a9-93a2-bdbe69f1ae51
```

#### **2️⃣ Fetch all Women's Products**
```
GET /products?categoryId=e2711512-763f-4393-8bfc-8a48abbafbec
```

---

### **If You Want to Filter by Category Type (e.g., T-Shirts or Jackets)**
Each category has multiple **categoryTypeId** values. If you want to get products within a specific **subcategory**, use both `categoryId` and `categoryTypeId`.

#### **Example GET Request to Fetch Only Men's T-Shirts**
```
GET /products?categoryId=12881de6-6b64-44a9-93a2-bdbe69f1ae51&categoryTypeId=8b14bb08-ef02-4834-9e12-8a10079c72ff
```

---
