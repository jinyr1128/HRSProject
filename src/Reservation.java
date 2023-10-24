import java.util.UUID;

class Reservation {
    private UUID id;
    private Room room;
    private Customer customer;
    private String date;

    public Reservation(UUID id, Room room, Customer customer, String date) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.date = date;
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