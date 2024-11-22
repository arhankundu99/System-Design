package services;

import states.*;

public class VendingMachineService {
    String selectedProduct;
    VendingMachineState state;
    ProductSelectionState productSelectionState;
    MoneyInsertionState moneyInsertionState;
    ChangeCollectionState changeCollectionState;
    DispenseProductState dispenseProductState;

    InventoryService inventoryService;
    double amount;
    double change;

    public VendingMachineService() {
        inventoryService = new InventoryService();

        productSelectionState = new ProductSelectionState(this, inventoryService);
        moneyInsertionState = new MoneyInsertionState(this);
        changeCollectionState = new ChangeCollectionState(this);
        dispenseProductState = new DispenseProductState(this);

        state = productSelectionState;
        amount = 0;
        change = 0;
        selectedProduct = null;
    }

    public void selectProduct(String product) {
        this.state.selectProduct(product);
    }

    public void insertMoney(double amount) {
        this.state.insertMoney(amount);
    }

    public void dispenseProduct() {
        this.state.dispenseProduct();
    }

    public void collectChange(double amount) {
        this.state.collectChange(amount);
    }

    public void setSelectedProduct(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public String getSelectedProduct() {
        return selectedProduct;
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public double getProductPrice(String product) {
        return inventoryService.getProductPrice(product);
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public VendingMachineState getProductSelectionState() {
        return productSelectionState;
    }

    public VendingMachineState getMoneyInsertionState() {
        return moneyInsertionState;
    }

    public VendingMachineState getChangeCollectionState() {
        return changeCollectionState;
    }

    public VendingMachineState getProductDispenseState() {
        return dispenseProductState;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChange() {
        return change;
    }

    public void addProduct(String productName, double price, int quantity) {
        inventoryService.addProduct(productName, price, quantity);
    }

    public boolean isProductAvailable(String product) {
        return inventoryService.isProductAvailable(product);
    }

    public void removeProduct(String product) {
        inventoryService.removeProduct(product);
    }

    public void decreaseProductQuantity(String product, int quantity) {
        inventoryService.decreaseProductQuantity(product, quantity);
    }
}
