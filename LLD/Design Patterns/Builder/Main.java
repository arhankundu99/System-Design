public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer.Builder("Intel I7", "32GB").setStorage(256).build();

        System.out.println(computer.getStorage());
    }
}