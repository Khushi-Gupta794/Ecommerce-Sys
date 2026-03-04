package ecom.ecom_app.FeignClient;

import ecom.ecom_app.DTO.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")//we have feign level fallback also
public interface InventoryClient {

    @GetMapping("/inventory/check")
    boolean checkStock(@RequestParam Long productId,@RequestParam int quantity);

   @PostMapping("inventory/create")
    String createInventory(@RequestBody InventoryRequest inventoryRequest);

}
