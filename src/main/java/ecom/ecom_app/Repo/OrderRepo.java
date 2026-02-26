package ecom.ecom_app.Repo;

import ecom.ecom_app.Entity.Order;
import ecom.ecom_app.Entity.OrderItem;
import ecom.ecom_app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
      //jpql query-- entity names used
    @Query("SELECT o FROM Order o WHERE o.id =: id")
    Order findOrderById(@Param("id") Long id);

    //native query
    @Query(value = """
           Select u.* from users u INNER JOIN order_details o ON u.id = o.user_id
           where o.total_amount > :price
           """, nativeQuery = true)
    List<User> findUsersWithHighOrders(@Param("price") double price);

//    @Query(value = """
//            Select u.* from users u LEFT JOIN order_item o ON u.id = o.user_id
//            where o.quantity > :quantity //order_item table doesnot have userid, no join to here
//            """, nativeQuery = true)
//    List<User> findUsersWithHighQuantity(@Param("quantity") double quantity);

  //orders  with high quantity --
    @Query(value = """
           Select i.* from order_details i LEFT JOIN order_item o ON  i.order_id= o.order_id
          where o.quantity > :quantity 
          """, nativeQuery = true)
   List<Order> findOrdersWithHighQuantity(@Param("quantity") double quantity);



}
