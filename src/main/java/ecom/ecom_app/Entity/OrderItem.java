package ecom.ecom_app.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class OrderItem implements Serializable {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private int quantity;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
private Product product;

 @ManyToOne
 @JoinColumn(name = "order_id")
private Order order;
}
