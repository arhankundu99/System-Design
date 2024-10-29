class Singleton {
    private static Singleton instance;
    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("Constructor called even when instance is not null.");
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            // Make it thread safe
            synchronized(Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

    public void invoke() {
        System.out.println("Singleton instance method invoked.");
    }
}