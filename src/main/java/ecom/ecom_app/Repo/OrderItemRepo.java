package ecom.ecom_app.Repo;

import ecom.ecom_app.Entity.Order;
import ecom.ecom_app.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
