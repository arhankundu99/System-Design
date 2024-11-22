package states;

import services.VendingMachineService;

public class MoneyInsertionState implements VendingMachineState {
    private final VendingMachineService vendingMachineService;
    public MoneyInsertionState(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    public void selectProduct(String product) {
        System.out.println("Product is already selected. Please insert money");
    }

    public void insertMoney(double amount) {
        vendingMachineService.addAmount(amount);

        String selectedProduct = vendingMachineService.getSelectedProduct();
        double selectedProductPrice = vendingMachineService.getProductPrice(selectedProduct);

        if (vendingMachineService.getAmount() < selectedProductPrice) {
            double balance = selectedProductPrice - vendingMachineService.getAmount();
            System.out.println("Remaining balance to be paid: " + balance);
        } else {
            double change = vendingMachineService.getAmount() - selectedProductPrice;

            if (change == 0) {
                vendingMachineService.setState(vendingMachineService.getProductDispenseState());
            } else {
                vendingMachineService.setState(vendingMachineService.getChangeCollectionState());
                vendingMachineService.setChange(change);
            }
        }
    }

    public void collectChange(double amount) {
        System.out.println("Please enter sufficient money first.");
    }

    public void dispenseProduct() {
        System.out.println("Please enter sufficient money first.");
    }
}
