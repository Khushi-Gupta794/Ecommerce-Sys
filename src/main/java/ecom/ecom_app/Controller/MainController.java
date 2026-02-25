package ecom.ecom_app.Controller;


import ecom.ecom_app.Config.JwtUtil;
import ecom.ecom_app.Entity.*;
import ecom.ecom_app.Service.EcomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private EcomService ecomService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------- Queries --------

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        return ecomService.getAllProducts();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(@Argument Long id) {
        return ecomService.getProductById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return ecomService.getAllUsers();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "orders")
    public List<Order> getAllOrders(){
        return ecomService.getAllOrders();
    }

    // -------- Mutations --------

    @MutationMapping
    public User registerUser(@Argument String name,
                             @Argument String email,
                             @Argument String password){
        return ecomService.registerUser(name, email, password);
    }

    @MutationMapping
    public String loginUser(@Argument String name,
                            @Argument String email,
                            @Argument String password) {

        User user = ecomService.validateLogin(email);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    public Product createProduct(@Argument String name,
                                 @Argument double price,
                                 @Argument int stock) {
        return ecomService.createProduct(name, price, stock);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    @CacheEvict(value = {"orders"}, allEntries = true)
    public Order createOrder(@Argument Long productId,
                             @Argument int quantity){

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ecomService.createOrder(productId, quantity, email);
    }
}

