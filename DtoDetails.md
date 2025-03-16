## **📌 List of DTO Classes**
1. **CategoryDto**
2. **CategoryTypeDto**
3. **ProductDto**
4. **ProductResourceDto**
5. **ProductVariantDto**

---

### **📌 What Each DTO Represents?**

#### **1️⃣ CategoryDto**
- Represents a **category** in the system.
- Contains details like `id`, `name`, `code`, and `description`.
- Holds a **list of category types** (`List<CategoryTypeDto>`).

```java
public class CategoryDto {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private List<CategoryTypeDto> categoryTypeList;
}
```

---

#### **2️⃣ CategoryTypeDto**
- Represents a **subcategory type** within a category.
- Contains `id`, `name`, `code`, and `description`.

```java
public class CategoryTypeDto {
    private UUID id;
    private String name;
    private String code;
    private String description;
}
```

---

#### **3️⃣ ProductDto**
- Represents a **product** in the system.
- Contains product details like `id`, `name`, `description`, `price`, `brand`, etc.
- Holds references to its **category** and **category type** using `categoryId` and `categoryTypeId`.
- Also contains lists for **variants** and **resources**.

```java
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private boolean isNewArrival;
    private Float rating;
    private UUID categoryId;
    private String categoryName;
    private UUID categoryTypeId;
    private String categoryTypeName;
    private List<ProductVariantDto> variants;
    private List<ProductResourceDto> productResources;
}
```

---

#### **4️⃣ ProductResourceDto**
- Represents **resources (images, videos, etc.)** associated with a product.
- Contains details like `name`, `URL`, `type` (image, video, etc.), and `isPrimary`.

```java
public class ProductResourceDto {
    private UUID id;
    private String name;
    private String url;
    private String type;
    private Boolean isPrimary;
}
```

---

#### **5️⃣ ProductVariantDto**
- Represents **different variants of a product** (like different sizes, colors).
- Contains `color`, `size`, and `stockQuantity`.

```java
public class ProductVariantDto {
    private UUID id;
    private String color;
    private String size;
    private Integer stockQuantity;
}
```

---

### **🔹 Why Use DTOs?**
- **Decouple** database entities from API responses.
- **Enhance security** by exposing only required fields.
- **Optimize data transfer** by including only necessary fields.
- **Prevent direct entity modifications** in API requests.

---

## **📌 Mapping DTO Object to Entity Object in Spring Boot**

Mapping a **DTO (Data Transfer Object)** to an **Entity** is a crucial step when handling API requests. There are multiple approaches to achieve this in Spring Boot:

1️⃣ **Manual Mapping (Using Setter Methods or Builder Pattern)**  
2️⃣ **Using ModelMapper Library**  
3️⃣ **Using MapStruct (Highly Efficient Library)**

---

### **1️⃣ Manual Mapping (Using Setter Methods or Builder Pattern)**
The most straightforward way to map DTOs to entities is **manually setting the fields**.

#### **✅ Example: Mapping `ProductDto` to `Product` Entity**
Let's say we have a `ProductDto`:

```java
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private UUID categoryId;
}
```

And we have a `Product` entity:

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
```

#### **🔹 Mapping DTO to Entity Manually**
Inside the **Service Layer**, we can create a `mapToEntity()` method:

```java
@Service
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setBrand(productDto.getBrand());

        // Fetch category from database
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found with ID " + productDto.getCategoryId()));

        product.setCategory(category); // Set mapped category
        return product;
    }
}
```

✅ **Advantages**:  
✔️ Simple and **easy to understand**  
✔️ Gives **full control over the mapping**

❌ **Disadvantages**:  
❌ Can become **repetitive** and **hard to maintain** for large projects

---

### **2️⃣ Using ModelMapper (Automatic Field Mapping)**
[ModelMapper](https://modelmapper.org/) is a popular **Java library** that automatically maps **DTOs to Entities**.

#### **🔹 Add Dependency (Maven)**
```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.1.0</version>
</dependency>
```

#### **🔹 Configure ModelMapper as a Spring Bean**
```java
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

#### **🔹 Mapping DTO to Entity Using ModelMapper**
```java
@Service
public class ProductService {
    @Autowired
    private ModelMapper modelMapper;

    public Product mapToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }
}
```

✅ **Advantages**:  
✔️ **Reduces boilerplate code**  
✔️ Automatically maps **matching field names**

❌ **Disadvantages**:  
❌ **Not optimal for complex mappings** (e.g., nested objects)  
❌ **Overhead** for small projects

---

### **3️⃣ Using MapStruct (Best for Performance)**
[MapStruct](https://mapstruct.org/) is a **compile-time code generation tool** that automatically creates **mapping methods**.

#### **🔹 Add Dependency (Maven)**
```xml

<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>

    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

#### **🔹 Create a Mapper Interface**
MapStruct **automatically generates** the implementation at compile-time.

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
```

#### **🔹 Use in Service**
```java
@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public Product convertToEntity(ProductDto productDto) {
        return productMapper.toEntity(productDto);
    }
}
```

✅ **Advantages**:  
✔️ **Best performance** (compile-time mapping)  
✔️ **Automatic implementation generation**  
✔️ **Supports complex mappings**

❌ **Disadvantages**:  
❌ Requires **more setup**  
❌ **Harder to debug** if issues arise

---

#### **🛠️ Which Mapping Method Should You Use?**

| Approach | When to Use? | Pros | Cons |
|----------|-------------|------|------|
| **Manual Mapping** | Small projects or when full control is needed | Simple & flexible | Repetitive & error-prone |
| **ModelMapper** | Medium-sized projects with simple mappings | Reduces boilerplate | Slower for deep mappings |
| **MapStruct** | Large applications needing high performance | Fast & efficient | Requires more setup |

If performance is critical, **MapStruct** is the best choice.  
For small projects, **manual mapping** is sufficient.

---

#### **🔹 Summary**
- **DTOs help separate API contracts from database entities**.
- **Mapping DTOs to Entities** can be done manually or via libraries like **ModelMapper** or **MapStruct**.
- **MapStruct** is the most **efficient** solution for large applications.

---
