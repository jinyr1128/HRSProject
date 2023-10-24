import java.util.*;

class Hotel {
    private double assets;// 호텔의 보유 자산(환불이나 뭐 이런저런 일을 위해서 만들어둔것입니다 별 필요없어 보이긴해요)
    private Map<String, Room> rooms;// 방들의 정보 (방키로 방에 접근)키에 따라 호텔의 각 방에 대한 정보를 저장
    private static List<Reservation> reservations;// 예약 리스트

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
        Room room = rooms.get(roomKey);// 방이 존재하고, 고객이 해당 방의 가격을 지불할 수 있는지 확인

        if (room != null && customer.canAfford(room.getPrice())) {
            UUID id = UUID.randomUUID();
            reservations.add(new Reservation(id, room, customer, date));
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
    public Reservation getCustomerReservations(UUID id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    public static boolean checkRooms(String key, String date){
        // 해당 날짜에 key 방이 예약 되어 있는지 확인

        boolean flag = true;
        for(Reservation r : reservations){                                          // 전체 예약 목록 순회
            if(r.getRoom().getKey().equals(key) && r.getDate().equals(date)){       // 해당 날짜(date)에 (key)방이 이미 예약되었는지 확인
                flag = false;                                                       // 예약이 되어 있으므로 예약 불가 (false return)
            }
        }
        return flag;
    }
}