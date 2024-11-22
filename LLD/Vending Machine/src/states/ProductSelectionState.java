package states;

import services.InventoryService;
import services.VendingMachineService;

public class ProductSelectionState implements VendingMachineState {
    VendingMachineService vendingMachineService;
    InventoryService inventoryService;
    public ProductSelectionState(VendingMachineService vendingMachineService, InventoryService inventoryService) {
        this.vendingMachineService = vendingMachineService;
        this.inventoryService = inventoryService;
    }
    public void selectProduct(String product) {
        if (vendingMachineService.isProductAvailable(product)) {
            vendingMachineService.setSelectedProduct(product);
            vendingMachineService.setState(vendingMachineService.getMoneyInsertionState());
        } else {
            System.out.println("Product is not available");
        }
    }
    public void insertMoney(double amount) {
        System.out.println("Please select a product first.");
    }
    public void dispenseProduct() {
        System.out.println("Please select a product first.");
    }
    public void collectChange(double amount) {
        System.out.println("Please select a product first.");
    }
}
