### **1. Category (Represents a Product Category)**
- Defines a broad **category** for products (e.g., 1 = "MEN",2 = "WOMEN").
- Each **Category** can have multiple **CategoryTypes** (subcategories).
- Each **Product** belongs to a **single Category**.

📌 **Key Fields:**
- `id` (UUID) → Unique identifier
- `name` (String) → Category name
- `code` (String) → Category code
- `description` (String) → Description of the category
- `categoryTypes` (List<CategoryType>) → Subcategories

**_Categories example:_**  
1. Men
2. Women
3. Kids

**_Example of categoryTypes under MEN category:_**
```json
{
  "id": 1,
  "name": "Men",
  "code": "MEN",
  "path": "/men",
  "description": "Men's Clothing",
  "types": [
    {
      "id": 1,
      "name": "Jeans",
      "description": "Men's jeans"
    },
    {
      "id": 2,
      "name": "Shirts",
      "description": "Men's shirts"
    },
    {
      "id": 14,
      "name": "Hoodie's",
      "description": "Men's Hoodie"
    }
  ]
}
```

---

### **2. CategoryType (Represents a Subcategory of a Category)**
- Defines a **subtype** within a Category (e.g., "Shirts" under "MEN").
- Each **CategoryType** is linked to one **Category**.
- Each **Product** belongs to a specific **CategoryType**.

📌 **Key Fields:**
- `id` (UUID) → Unique identifier
- `name` (String) → Name of the category type
- `code` (String) → Code of the category type
- `description` (String) → Description of the category type
- `category` (Category) → Reference to the main category

---

### **3. Product (Represents an Item for Sale)**
- Stores details about **each product** listed in the store.
- A **Product** belongs to a **Category** and a **CategoryType**.
- A **Product** can have multiple **variants** (e.g., different colors, sizes).
- A **Product** can have multiple **resources** (e.g., images, videos).

📌 **Key Fields:**
- `id` (UUID) → Unique identifier
- `name` (String) → Product name
- `description` (String) → Product description
- `price` (BigDecimal) → Product price
- `brand` (String) → Product brand (eg: Nike)
- `isNewArrival` (Boolean) → Whether it is a new product
- `rating` (Float) → Average customer rating
- `category` (Category) → Category reference
- `categoryType` (CategoryType) → Subcategory reference
- `variants` (List<ProductVariant>) → Different variations (e.g., color, size)
- `resources` (List<ProductResource>) → Images, videos, etc.

---

### **4. ProductVariant (Represents a Variation of a Product)**
- Defines different **variants** of a product (e.g., different sizes or colors).
- Each **ProductVariant** belongs to a **single Product**.

📌 **Key Fields:**
- `id` (UUID) → Unique identifier
- `color` (String) → Color of the variant (eg: "Black","Blue")
- `size` (String) → Size of the variant (eg: S, XL, M)
- `stockQuantity` (Integer) → Available stock
- `product` (Product) → Reference to the main product

---

### **5. ProductResource (Represents Media Files for a Product)**
- Stores **images, videos, or other media** for a product.
- Each **ProductResource** belongs to a **single Product**.

📌 **Key Fields:**
- `id` (UUID) → Unique identifier
- `name` (String) → Name of the resource
- `url` (String) → URL of the image/video
- `type` (String) → Type of media (e.g., "image", "video")
- `isPrimary` (Boolean) → Whether it is the main image
- `product` (Product) → Reference to the main product

---

### **Summary of What Each Schema Represents:**
| Schema            | Represents | Key Relationships |
|-------------------|-----------|------------------|
| **Category**      | Product category (e.g., Electronics, Clothing) | Has many `CategoryType`, Has many `Product` |
| **CategoryType**  | Subcategory (e.g., Laptops, Shoes) | Belongs to `Category`, Has many `Product` |
| **Product**       | A product available for purchase | Belongs to `Category`, Belongs to `CategoryType`, Has many `ProductVariant`, Has many `ProductResource` |
| **ProductVariant** | Different versions of a product (e.g., color, size) | Belongs to `Product` |
| **ProductResource** | Images, videos, or media for a product | Belongs to `Product` |

---

### **Relationships Between Schemas**

#### **1️⃣ Category ↔ CategoryType**
- **One-to-Many**: A **Category** can have multiple **CategoryTypes**.
- **Many-to-One**: Each **CategoryType** belongs to a **single Category**.

📌 **Annotations:**
```java
// In Category Entity
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
private List<CategoryType> categoryTypes;
```
```java
// In CategoryType Entity
@ManyToOne
@JoinColumn(name = "category_id", nullable = false)
private Category category;
```
🔹 **Why?**
- `@OneToMany`: Defines the relationship where **one category** has **many category types**.
- `mappedBy = "category"`: Means `CategoryType` owns the relationship.
- `cascade = CascadeType.ALL`: Ensures all operations (persist, remove, etc.) apply to child entities.
- `@ManyToOne`: Specifies that each `CategoryType` belongs to a single `Category`.
- `@JoinColumn(name = "category_id")`: Specifies the foreign key in the `CategoryType` table.

---

#### **2️⃣ Category ↔ Product**
- **One-to-Many**: A **Category** can have multiple **Products**.
- **Many-to-One**: Each **Product** belongs to **one Category**.

📌 **Annotations:**
```java
// In Category Entity
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Product> products;
```
```java
// In Product Entity
@ManyToOne
@JoinColumn(name = "category_id", nullable = false)
private Category category;
```
🔹 **Why?**
- A `Product` must belong to **one Category**, ensuring clear classification.
- **One-to-Many** ensures we can retrieve all products belonging to a category.

---

#### **3️⃣ CategoryType ↔ Product**
- **One-to-Many**: A **CategoryType** can have multiple **Products**.
- **Many-to-One**: Each **Product** belongs to **one CategoryType**.

📌 **Annotations:**
```java
// In CategoryType Entity
@OneToMany(mappedBy = "categoryType", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Product> products;
```
```java
// In Product Entity
@ManyToOne
@JoinColumn(name = "category_type_id", nullable = false)
private CategoryType categoryType;
```
🔹 **Why?**
- This ensures **Products** are categorized at a more detailed level (e.g., a "Men's Shorts" in "MENS").

---

#### **4️⃣ Product ↔ ProductVariant**
- **One-to-Many**: A **Product** can have multiple **ProductVariants**.
- **Many-to-One**: Each **ProductVariant** belongs to **one Product**.

📌 **Annotations:**
```java
// In Product Entity
@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ProductVariant> productVariants;
```
```java
// In ProductVariant Entity
@ManyToOne
@JoinColumn(name = "product_id", nullable = false)
private Product product;
```
🔹 **Why?**
- Allows a **single product** to have multiple versions (e.g., different sizes/colors).

---

#### **5️⃣ Product ↔ ProductResource**
- **One-to-Many**: A **Product** can have multiple **ProductResources** (e.g., images, videos).
- **Many-to-One**: Each **ProductResource** belongs to **one Product**.

📌 **Annotations:**
```java
// In Product Entity
@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ProductResource> resources;
```
```java
// In ProductResource Entity
@ManyToOne
@JoinColumn(name = "product_id", nullable = false)
private Product product;
```
🔹 **Why?**
- Helps store **multiple images/videos** for a product.

---

#### **Summary of Relationships**
| Parent Entity   | Child Entity       | Relationship Type |
|----------------|-------------------|------------------|
| **Category**   | CategoryType       | One-to-Many     |
| **CategoryType** | Product         | One-to-Many     |
| **Category**   | Product            | One-to-Many     |
| **Product**    | ProductVariant      | One-to-Many     |
| **Product**    | ProductResource     | One-to-Many     |

---

#### **Why Are These Annotations Necessary?**
1. **Ensures Referential Integrity**
    - `@JoinColumn` maintains foreign key constraints in the database.

2. **Automatic Data Handling**
    - `cascade = CascadeType.ALL` ensures related entities are created/updated/deleted together.

3. **Prevents Orphan Data**
    - `orphanRemoval = true` removes child records when the parent is deleted.

---