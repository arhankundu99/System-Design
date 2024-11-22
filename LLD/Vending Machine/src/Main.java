import services.VendingMachineService;

public class Main {
    public static void main(String[] args) {
        VendingMachineService vendingMachineService = new VendingMachineService();
        vendingMachineService.addProduct("Coke", 2, 1);

        vendingMachineService.selectProduct("Coke");
        vendingMachineService.insertMoney(2);
        vendingMachineService.dispenseProduct();

        vendingMachineService.selectProduct("Coke");
    }
}
