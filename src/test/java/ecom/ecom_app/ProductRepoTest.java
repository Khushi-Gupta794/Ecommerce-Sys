//package ecom.ecom_app;
//
//import ecom.ecom_app.Entity.Product;
//import ecom.ecom_app.Repo.ProductRepo;
//import ecom.ecom_app.Service.EcomService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Testcontainers
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//public class ProductRepoTest {
//    @Container
//     static PostgreSQLContainer<?> postgres =  new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    @DynamicPropertySource
//    static void configure(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    @Autowired
//     private ProductRepo productRepo;
//    @Test
//    void saveProductTest(){
//        Product product = new Product();
//        product.setName("Laptop");
//        product.setPrice(50000);
//        Product saved = productRepo.save(product);
//
//        Assertions.assertNotNull(saved.getId());
//
//
//    }
//
//}
