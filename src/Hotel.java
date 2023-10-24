import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public UUID reserveRoom(String roomKey, Customer customer, String dateStr) {
        // 방의 키를 사용하여 rooms 맵에서 해당 방의 정보를 가져오면되유...
        Room room = rooms.get(roomKey);
        // "yyyy-MM-dd" 형식의 문자열 날짜를 파싱하기 위한 포맷터를 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // dateStr을 LocalDate 형식으로 파싱(그냥 쉽게 문자로 된 날짜를 날짜 형태로 바꾸기)
        LocalDate inputDate = LocalDate.parse(dateStr, formatter);
        // 현재 날짜를 가져오는 식???
        LocalDate currentDate = LocalDate.now();

        // 입력한 날짜가 현재 날짜 이전인 경우 예약 거절
        if (inputDate.isBefore(currentDate)) {
            System.out.println("당신은 타임머신타려고요? 지난 날짜는 예약 안되요~.");
            return null;//거절~
        }

        // 방이 있고 고객이 돈을 지불할 수 있는 경우
        if (room != null && customer.canAfford(room.getPrice())) {
            // 새로운 예약 ID를 생성
            UUID id = UUID.randomUUID();

            // 새로운 예약을 생성하고 reservations 리스트에 추가
            reservations.add(new Reservation(id, room, customer, dateStr));

            assets += room.getPrice();      // 총 자산 업데이트
            return id;      // 예약 ID를 반환

        } else {
            // 조건을 만족하지 못하는 경우 예약을 거절하고 null을 반환
            return null;
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

    // 총 보유 자산 출력
    public void getAssets() {
        System.out.println(assets);
    }
}