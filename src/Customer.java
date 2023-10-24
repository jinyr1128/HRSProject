class Customer {
    private String name;// 고객 이름
    private String phoneNumber;// 전화번호
    private double money; // 고객이 가진 돈

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
    }// 해당 가격을 지불할 수 있는지 확인하는 메소드
}
