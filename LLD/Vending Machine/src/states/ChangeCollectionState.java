package states;

import services.VendingMachineService;

public class ChangeCollectionState implements VendingMachineState {
    private final VendingMachineService vendingMachineService;
    public ChangeCollectionState(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }
    public void selectProduct(String product) {
        System.out.println("Please collect your change first.");
    }
    public void insertMoney(double amount) {
        System.out.println("Please collect your change first");
    }
    public void collectChange(double amount) {
        System.out.println("Thank you for collecting your change.");
        vendingMachineService.setChange(0);
        vendingMachineService.setAmount(0);
        this.vendingMachineService.setState(vendingMachineService.getProductDispenseState());
    }
    public void dispenseProduct() {
        System.out.println("Please collect your change first");
    }
}
