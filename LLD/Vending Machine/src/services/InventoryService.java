package services;


import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    Map<String, Integer> inventoryMap;
    Map<String, Double> priceMap;
    public InventoryService() {
        inventoryMap = new HashMap<>();
        priceMap = new HashMap<>();
    }

    public void addProduct(String product, double price, int quantity) {
        inventoryMap.put(product, inventoryMap.getOrDefault(product, 0) + quantity);
        priceMap.put(product, price);
    }

    public void removeProduct(String product) {
        inventoryMap.remove(product);
        priceMap.remove(product);
    }

    public void decreaseProductQuantity(String product, int quantity) {
        if (!inventoryMap.containsKey(product) || inventoryMap.get(product) < quantity) {
            throw new IllegalArgumentException("The given quantity is more than the quantity in inventory.");
        }
        inventoryMap.put(product, inventoryMap.getOrDefault(product, 0) - quantity);
        if (inventoryMap.get(product) == 0) {
            inventoryMap.remove(product);
            priceMap.remove(product);
        }
    }

    public boolean isProductAvailable(String product) {
        return inventoryMap.containsKey(product);
    }

    public double getProductPrice(String product) {
        return priceMap.getOrDefault(product, 0.0);
    }
}
