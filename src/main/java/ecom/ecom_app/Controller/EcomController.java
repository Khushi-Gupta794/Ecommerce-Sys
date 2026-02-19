package ecom.ecom_app.Controller;

import ecom.ecom_app.Entity.Order;
import ecom.ecom_app.Entity.OrderItem;
import ecom.ecom_app.Entity.Product;
import ecom.ecom_app.Entity.User;
import ecom.ecom_app.Repo.OrderRepo;
import ecom.ecom_app.Repo.ProductRepo;
import ecom.ecom_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EcomController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    // ----------- Queries ------------

    @QueryMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // ----------- Mutations ------------

    @MutationMapping
    public User createUser(@Argument String name,
                           @Argument String email) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        return userRepo.save(user);
    }

    @MutationMapping
    public Product createProduct(@Argument String name,
                                 @Argument double price,
                                 @Argument int stock) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        return productRepo.save(product);
    }

    //query for all orders-
    @QueryMapping
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    @MutationMapping
    public Order createOrder(@Argument Long userId,
                             @Argument Long productId,
                             @Argument int quantity){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

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

        double total = product.getPrice() * quantity;
        order.setTotalAmount(total);
        order.setOrderItems(List.of(item));

        return orderRepo.save(order);
    }
}