import java.util.*;

class Hotel {
    private double assets;
    private Map<String, Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        this.assets = 10000;  // 초기 자산
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
        // [구현] 중복 예약 처리 필요 (날짜별로 한 객실당 한 고객만 예약)
        // [구현] 객실 요금 처리 필요

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
        // 예약 취소
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public List<Reservation> getAllReservations() {
        // 모든 예약 목록 조회
        return reservations;
    }

    public List<Reservation> getCustomerReservations(String name) {
        // 고객의 예약 내역 조회
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
}