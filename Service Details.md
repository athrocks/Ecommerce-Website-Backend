## **📌 Service Classes in Your Codebase**
Here are the service classes you provided:

### **1️⃣ CategoryService**
✅ **Purpose:**  
Manages `Category` and `CategoryType` entities.

✅ **Key Methods:**
- `getCategoryById(UUID categoryId)`: Fetches a category by ID.
- `createCategory(CategoryDto categoryDto)`: Converts `CategoryDto` to `Category` and saves it.
- `getAllCategory()`: Retrieves all categories.
- `updateCategory(CategoryDto categoryDto, UUID categoryId)`: Updates category and its associated `CategoryType`.
- `deleteCategory(UUID categoryId)`: Deletes a category by ID.

✅ **Key Features:**
- Uses **DTO mapping** to prevent direct entity exposure.
- Handles `CategoryType` updates efficiently.
- Uses `@Transactional` for update operations to maintain data integrity.

---

### **2️⃣ ProductService (Interface)**
✅ **Purpose:**  
Defines methods for managing `Product` entities.

✅ **Key Methods:**
- `getAllProducts(UUID categoryId, UUID typeId)`: Fetches products with optional filters.
- `addProduct(ProductDto product)`: Converts `ProductDto` to `Product` and saves it.

✅ **Key Features:**
- Abstract layer for `ProductServiceImpl`.
- Encourages **loose coupling** and better testability.

---

### **3️⃣ ProductServiceImpl (Implements ProductService)**
✅ **Purpose:**  
Implements `ProductService` with business logic for handling `Product` entities.

✅ **Key Methods:**
- `getAllProducts(UUID categoryId, UUID typeId)`: Retrieves products filtered by category and type.
- `addProduct(ProductDto productDto)`: Converts `ProductDto` to `Product`, maps related entities, and saves.
- **Helper Methods:**
    - `mapToProductEntity(ProductDto productDto)`: Converts `ProductDto` to `Product`.
    - `mapToProductVariant(List<ProductVariantDto>, Product)`: Converts `ProductVariantDto` to `ProductVariant`.
    - `mapToProductResources(List<ProductResourceDto>, Product)`: Converts `ProductResourceDto` to `Resources`.

✅ **Key Features:**
- Uses `CategoryService` to fetch category details.
- Ensures proper mapping of `ProductVariant` and `ProductResource`.
- Follows **Separation of Concerns (SoC)** by keeping mapping logic modular.

---

### **📌 Summary**
| **Service Class** | **Purpose** |
|------------------|------------|
| **CategoryService** | Manages `Category` & `CategoryType` CRUD operations |
| **ProductService (Interface)** | Defines methods for managing `Product` |
| **ProductServiceImpl** | Implements `ProductService`, handles `Product` entity logic |

---

