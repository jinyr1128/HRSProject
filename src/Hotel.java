import java.util.*;

class Hotel {
    private double assets;
    private Map<String, Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        this.assets = 10000;
        this.rooms = new HashMap<>();
        this.reservations = new ArrayList<>();

        // 방 정보 초기화
        rooms.put("small1", new Room("small1", 50, "small"));
        rooms.put("small2", new Room("small2", 50, "small"));
        rooms.put("big1", new Room("big1", 100, "big"));
        rooms.put("big2", new Room("big2", 100, "big"));
        rooms.put("veryBig1", new Room("veryBig1", 200, "veryBig"));
        rooms.put("veryBig2", new Room("veryBig2", 200, "veryBig"));
    }

    public UUID reserveRoom(String roomKey, Customer customer, String date) {
        Room room = rooms.get(roomKey);
        if (room != null && customer.canAfford(room.getPrice())) {
            UUID id = UUID.randomUUID();
            reservations.add(new Reservation(id, room, customer, date));
            return id;
        } else {
            return null;
        }
    }

    public boolean cancelReservation(UUID id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public List<Reservation> getCustomerReservations(String name) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
}