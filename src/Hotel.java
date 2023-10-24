import java.util.*;

class Hotel {
    private double assets;// 호텔의 보유 자산(환불이나 뭐 이런저런 일을 위해서 만들어둔것입니다 별 필요없어 보이긴해요)
    private Map<String, Room> rooms;// 방들의 정보 (방키로 방에 접근)키에 따라 호텔의 각 방에 대한 정보를 저장
    private List<Reservation> reservations;// 예약 리스트

    public Hotel() {
        this.assets = 10000;// 기본 자산 10000 설정
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
    // 방 예약 메소드
    public UUID reserveRoom(String roomKey, Customer customer, String date) {
        // [구현] 중복 예약 처리 필요 (날짜별로 한 객실당 한 고객만 예약)
        // [구현] 객실 요금 처리 필요
        Room room = rooms.get(roomKey);// 방이 존재하고, 고객이 해당 방의 가격을 지불할 수 있는지 확인
        if (room != null && customer.canAfford(room.getPrice())) {
            UUID id = UUID.randomUUID();
            reservations.add(new Reservation(id, room, customer, date));
            assets += room.getPrice();
            return id;// 예약 ID 반환
        } else {
            return null;// 예약 불가능

        }
    }

    public boolean cancelReservation(UUID id) { // 주어진 id와 일치하는 예약을 찾아 삭제
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    // 모든 예약 정보 반환 메소드
    public List<Reservation> getAllReservations() {
        return reservations;
    }
    // 특정 고객의 예약 정보 반환 메소드
    public List<Reservation> getCustomerReservations(String name) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    // 총 보유 자산 출력
    public void getAssets() {
        System.out.println(assets);
    }
}