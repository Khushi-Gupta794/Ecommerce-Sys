//package ecom.ecom_app;
//
//import ecom.ecom_app.Entity.Order;
//import ecom.ecom_app.Entity.OrderItem;
//import ecom.ecom_app.Entity.User;
//import ecom.ecom_app.Repo.OrderRepo;
//import ecom.ecom_app.Repo.UserRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static graphql.Assert.assertFalse;
//import static graphql.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class OrderRepoTest {
//
//    @Autowired
//    private OrderRepo orderRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Test
//    void testFindOrderById() {
//
//        Order order = new Order();
//        order.setTotalAmount(1000.0);
//        orderRepo.save(order);
//
//        Order result = orderRepo.findOrderById(order.getId());
//
//        assertNotNull(result);
//        assertEquals(1000.0, result.getTotalAmount());
//    }
//
//    @Test
//    void testFindUsersWithHighOrders() {
//
//        User user = new User();
//        user.setName("Demo");
//        userRepo.save(user);
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setTotalAmount(2000.0);
//        orderRepo.save(order);
//
//        List<User> result =
//                orderRepo.findUsersWithHighOrders(1000.0);
//
//        assertFalse(result.isEmpty());
//        assertEquals("Demo", result.get(0).getName());
//    }
//
//
//}
