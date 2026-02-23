package ecom.ecom_app.Controller;

import ecom.ecom_app.Config.JwtUtil;
import ecom.ecom_app.Entity.Order;
import ecom.ecom_app.Entity.OrderItem;
import ecom.ecom_app.Entity.Product;
import ecom.ecom_app.Entity.User;
import ecom.ecom_app.ProductNotFoundExp;
import ecom.ecom_app.Repo.OrderRepo;
import ecom.ecom_app.Repo.ProductRepo;
import ecom.ecom_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller


public class EcomController {
    private static final Logger logger =
            LoggerFactory.getLogger(EcomController.class);

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ----------- Queries ------------

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepo.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product getProductById(@Argument Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        logger.info("fetching all users");
        return userRepo.findAll();
    }

    // ----------- Mutations ------------

    @MutationMapping
    public User registerUser(@Argument String name, @Argument String email, @Argument String password){
        if(userRepo.findByEmail(email).isPresent()){
            throw new RuntimeException("User already exists");
        }
        User user= new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");  //setting default role for user register inorder to verify the token
        return userRepo.save(user);
    }

   // @PreAuthorize("hasRole('USER')")
    @MutationMapping
    public String loginUser(@Argument String name,
                           @Argument String email, @Argument String password) {
       // logger.info("creating the user",name);
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return jwtUtil.generateToken(user.getEmail(), user.getRole());



    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@Argument String name,
                                 @Argument double price,
                                 @Argument int stock) {
        logger.info("creating products {}", name);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        return productRepo.save(product);
    }

    //query for all orders-
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Order createOrder(
                             @Argument Long productId,
                             @Argument int quantity){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        logger.info("creating order with email={} productId={} quantity={}",email, productId,quantity);
        User user = userRepo.findByEmail(email).orElseThrow(() ->
        {
            logger.error("user not found with email={}", email);
            return new RuntimeException("email not found");
        });

        Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundExp("Product not found"));//custom exception

        if(product.getStock() < quantity){
            logger.warn("not enough stocks for productId={}", productId);
            throw new RuntimeException("not enough stock");
        }
        product.setStock(product.getStock() - quantity);

        productRepo.save(product);

        Order order = new Order();
        order.setUser(user);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());
        item.setOrder(order);

        double total = product.getPrice() * quantity;
        order.setTotalAmount(total);
        order.setOrderItems(List.of(item));
        logger.info("order created successfully");

        return orderRepo.save(order);
    }
}