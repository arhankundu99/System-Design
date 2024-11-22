package states;

import services.VendingMachineService;

public class DispenseProductState implements VendingMachineState {
    private final VendingMachineService vendingMachineService;
    public DispenseProductState(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }
    public void selectProduct(String product) {
        System.out.println("Please collect your product first.");
    }
    public void insertMoney(double amount) {
        System.out.println("Please collect your product first");
    }
    public void collectChange(double amount) {
        System.out.println("Please collect your product first");
    }
    public void dispenseProduct() {
        System.out.println("Thank you. Please collect your dispensed product.");
        vendingMachineService.decreaseProductQuantity(vendingMachineService.getSelectedProduct(), 1);
        vendingMachineService.setSelectedProduct(null);
        vendingMachineService.setState(vendingMachineService.getProductSelectionState());
    }
}
