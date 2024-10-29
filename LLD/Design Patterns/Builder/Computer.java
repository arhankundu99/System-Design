public class Computer {
    private String CPU;
    private String RAM;

    // Optional fields
    private final int storage;
    private final boolean hasGraphicsCard;


    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;

        this.storage = builder.storage;
        this.hasGraphicsCard = builder.hasGraphicsCard;
    }

    public String getCPU() {
        return this.CPU;
    }

    public String getRAM() {
        return this.RAM;
    }

    public int getStorage() {
        return this.storage;
    }

    public boolean getHasGraphicsCard() {
        return this.hasGraphicsCard;
    }

    public static class Builder {
        private String CPU;
        private String RAM;

        // Optional fields
        private int storage;
        private boolean hasGraphicsCard;

        public Builder(String CPU, String RAM) {
            this.CPU = CPU;
            this.RAM = RAM;
        }

        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public Builder setHasGraphicsCard(boolean hasGraphicsCard) {
            this.hasGraphicsCard = hasGraphicsCard;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}