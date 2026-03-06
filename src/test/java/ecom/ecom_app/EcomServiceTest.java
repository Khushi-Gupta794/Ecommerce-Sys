package ecom.ecom_app;

import ecom.ecom_app.DTO.InventoryRequest;
import ecom.ecom_app.Entity.Order;
import ecom.ecom_app.Entity.Product;
import ecom.ecom_app.Entity.User;
import ecom.ecom_app.FeignClient.InventoryClient;
import ecom.ecom_app.Repo.OrderRepo;
import ecom.ecom_app.Repo.ProductRepo;
import ecom.ecom_app.Repo.UserRepo;
import ecom.ecom_app.Service.EcomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) //to enable mock annotation
public class EcomServiceTest {
    @Mock
    ProductRepo productRepo;
    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private InventoryClient inventoryClient;


     @InjectMocks
     EcomService service;

    @Test
    void getAllProductsTest(){
        Product product  = new Product();
        product.setId(1L);
        product.setName("test");
        product.setPrice(100);

        Mockito.when(productRepo.findAll()).thenReturn(List.of(product));
        List <Product> getProduct =service.getAllProducts();

       //assertions
        //test product == matched product
       // Assertions.assertEquals(2 , getProduct.getId());
        Assertions.assertEquals(1, getProduct.size());
        Assertions.assertEquals("test", getProduct.get(0).getName());
       // Mockito.verify(productRepo, Mockito.times(1)).findAll();  //to verify repository calls
    }

    @Test
    void getProductByIdTest(){
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
       Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        Product result = service.getProductById(1L);
       Assertions.assertEquals("Laptop", result.getName());
    }

    @Test
    void getAllUsersTest(){
        User user = new User();
        user.setId(1L);
        user.setName("Khushi");
        Mockito.when(userRepo.findAll()).thenReturn(List.of(user));
        List<User> users = service.getAllUsers();

        Assertions.assertEquals(1 , users.size());
        Assertions.assertEquals("Khushi" , users.get(0).getName());
    }

    @Test
    void getAllOrdersTest(){
       Order order = new Order();
        order.setId(1L);
     Mockito.when(orderRepo.findAll()).thenReturn(List.of(order));
     List<Order> orders = service.getAllOrders();
     Assertions.assertEquals(1 , orders.size());
    }

    @Test
    void createProductTest(){
        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");
        product.setPrice(500);

        Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(product);
        Product result = service.createProduct("Phone",500,10);
        Assertions.assertEquals("Phone", result.getName());
        Mockito.verify(inventoryClient).createInventory(Mockito.any(InventoryRequest.class));
    }

    @Test
    void createOrderTest(){
        User user = new User();
        user.setEmail("user@gmail.com");

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);

        Order order = new Order();
        order.setId(1L);

        Mockito.when(userRepo.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(inventoryClient.checkStock(1L,2)).thenReturn(true);
        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(order);

        Order result = service.createOrder(1L,2,"user@gmail.com");

        Assertions.assertEquals(1L , result.getId());
    }

    //user register login part
    @Test
    void registerUserTest(){
        User user = new User();
        user.setName("Khushi");
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPass");

        Mockito.when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("1234")).thenReturn("encodedPass");
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
       User result = service.registerUser("Khushi","test@gmail.com","1234");
        Assertions.assertEquals("Khushi", result.getName());
    }

    @Test
    void validateLoginTest(){
        User user = new User();
        user.setEmail("test@gmail.com");

        Mockito.when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        User result = service.validateLogin("test@gmail.com");
        Assertions.assertEquals("test@gmail.com", result.getEmail());
    }

}
