package states;

public interface VendingMachineState {
    void selectProduct(String product);
    void insertMoney(double amount);
    void collectChange(double amount);
    void dispenseProduct();
}
