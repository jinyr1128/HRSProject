import java.util.UUID;

class Reservation {
    private UUID id;            // 예약 ID
    private Room room;          // 예약된 방 정보
    private Customer customer;  // 예약한 고객 정보
    private String date;        // 예약 날짜

    public Reservation(UUID id, Room room, Customer customer, String date) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.date = date;
    }

    @Override
    public String toString() {
        return "\n예약 ID: " + this.id +
                "\n고객 이름: " + this.getCustomerName() +
                "\n전화번호: " + this.getPhoneNumber() +
                "\n방 타입: " + this.room.getKey() +
                "\n날짜: " + this.getDate();
    }

    public UUID getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    public String getDate() {
        return date;
    }


}