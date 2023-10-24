import java.util.regex.Pattern;

class Customer {
    private String name;// 고객 이름
    private String phoneNumber;// 전화번호
    private double money; // 고객이 가진 돈

    public Customer(String name, String phoneNumber, double money) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.money = money;

        //정규화 추가    휴대폰 형식이 000-0000-0000이 아니면 입력이 안되는지, 에러가 출력되는지는 아직 확인못함
        String regexPhoneNum = " ^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        phoneNumber = phoneNumber.trim();
        if (!Pattern.matches(regexPhoneNum, phoneNumber)) {
            System.out.println("휴대폰 번호 형식이 일치하지 않습니다.");
            return;
        }else{
            this.phoneNumber = phoneNumber;
        }
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
