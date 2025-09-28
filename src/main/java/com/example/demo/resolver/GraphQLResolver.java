package com.example.demo.resolver;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Mutation;
import org.springframework.graphql.data.method.annotation.Query;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class GraphQLResolver {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Queries
    @Query
    public Iterable<Category> allCategories() {
        return categoryService.getAllCategories();
    }

    @Query
    public Optional<Category> categoryById(@Argument Long id) {
        return categoryService.getCategoryById(id);
    }

    @Query
    public Iterable<User> allUsers() {
        return userService.getAllUsers();
    }

    @Query
    public Optional<User> userById(@Argument Long id) {
        return userService.getUserById(id);
    }

    @Query
    public Iterable<Product> allProducts() {
        return productService.getAllProducts();
    }

    @Query
    public Iterable<Product> productsByCategoryId(@Argument Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @Query
    public Optional<Product> productById(@Argument Long id) {
        return productService.getProductById(id);
    }

    // Mutations
    @Mutation
    public Category createCategory(@Argument String name) {
        Category category = new Category();
        category.setName(name);
        return categoryService.saveCategory(category);
    }

    @Mutation
    public Category updateCategory(@Argument Long id, @Argument String name) {
        Category category = categoryService.getCategoryById(id).orElseThrow();
        category.setName(name);
        return categoryService.saveCategory(category);
    }

    @Mutation
    public Boolean deleteCategory(@Argument Long id) {
        categoryService.deleteCategory(id);
        return true;
    }

    @Mutation
    public User createUser(@Argument String fullName, @Argument String email) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        return userService.saveUser(user);
    }

    @Mutation
    public User updateUser(@Argument Long id, @Argument String fullName, @Argument String email) {
        User user = userService.getUserById(id).orElseThrow();
        user.setFullName(fullName);
        user.setEmail(email);
        return userService.saveUser(user);
    }

    @Mutation
    public Boolean deleteUser(@Argument Long id) {
        userService.deleteUser(id);
        return true;
    }

    @Mutation
    public Product createProduct(@Argument String title, @Argument String description, @Argument String images, @Argument Integer amount, @Argument Double price, @Argument Long categoryId) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setImages(images);
        product.setAmount(amount);
        product.setPrice(price);
        product.setCategory(categoryService.getCategoryById(categoryId).orElseThrow());
        return productService.saveProduct(product);
    }

    @Mutation
    public Product updateProduct(@Argument Long id, @Argument String title, @Argument String description, @Argument String images, @Argument Integer amount, @Argument Double price, @Argument Long categoryId) {
        Product product = productService.getProductById(id).orElseThrow();
        product.setTitle(title);
        product.setDescription(description);
        product.setImages(images);
        product.setAmount(amount);
        product.setPrice(price);
        product.setCategory(categoryService.getCategoryById(categoryId).orElseThrow());
        return productService.saveProduct(product);
    }

    @Mutation
    public Boolean deleteProduct(@Argument Long id) {
        productService.deleteProduct(id);
        return true;
    }
}