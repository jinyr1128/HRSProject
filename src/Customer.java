import java.util.regex.Pattern;

class Customer {
    private String name;        // 고객 이름
    private String phoneNumber; // 전화번호
    private int money;          // 고객이 가진 돈

    public Customer(String name, String phoneNumber, int money) {
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

    // 해당 가격을 지불할 수 있는지 확인하는 메소드
    public boolean canAfford(int price) {
        return money >= price;
    }
}
