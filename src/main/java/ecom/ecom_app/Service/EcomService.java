package ecom.ecom_app.Service;
import ecom.ecom_app.AOP.CustomAnnotationAop;
import ecom.ecom_app.Entity.*;
import ecom.ecom_app.ProductNotFoundExp;
import ecom.ecom_app.Repo.OrderRepo;
import ecom.ecom_app.Repo.ProductRepo;
import ecom.ecom_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EcomService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------- Queries --------

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    // -------- Mutations --------
    @CustomAnnotationAop("User registration")
    @Transactional
    public User registerUser(String name, String email, String password) {

        if(userRepo.findByEmail(email).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public User validateLogin(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Product createProduct(String name, double price, int stock) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        return productRepo.save(product);
    }

    @CustomAnnotationAop("Creating new order")
    @Transactional
    public Order createOrder(Long productId, int quantity, String email){

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("email not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundExp("Product not found"));

        if(product.getStock() < quantity){
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

        order.setTotalAmount(product.getPrice() * quantity);
        order.setOrderItems(List.of(item));

        return orderRepo.save(order);
    }
}
