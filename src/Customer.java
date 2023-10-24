class Customer {
    private String name;
    private String phoneNumber;
    private double money;

    public Customer(String name, String phoneNumber, double money) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getMoney() {
        return money;
    }

    public boolean canAfford(double price) {
        return money >= price;
    }
}
