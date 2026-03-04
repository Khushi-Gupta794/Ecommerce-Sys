//package ecom.ecom_app.FeignClient;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class InventoryFallback implements InventoryClient{
//    @Override
//    public boolean checkStock(Long productId, int quantity){
//        System.out.println("inventory service is down");
//        return false;
//    }
//
//}
