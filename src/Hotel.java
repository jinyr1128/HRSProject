import java.text.DecimalFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Hotel {
    private double assets;                          // 호텔 보유 자산
    private Map<String, Room> rooms;                // 객실 정보 (방키로 방에 접근)키에 따라 호텔의 각 방에 대한 정보를 저장
    private static List<Reservation> reservations;  // 예약 리스트

    public Hotel() {
        this.assets = 10000;                        // 기본 자산 10000 설정
        this.rooms = new LinkedHashMap<>();
        this.reservations = new ArrayList<>();

        // 방 정보 초기화
        rooms.put("Deluxe Twin", new Room("Deluxe Twin", 50));
        rooms.put("Deluxe Double", new Room("Deluxe Double", 50));
        rooms.put("Premier Twin", new Room("Premier Twin", 100));
        rooms.put("Premier Double", new Room("Premier Double", 100));
        rooms.put("Suite Twin", new Room("Suite Twin", 200));
        rooms.put("Suite Double", new Room("Suite Double", 200));
    }

    // 방 예약 메소드
    public UUID reserveRoom(Room room, Customer customer, String dateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");        // "yyyy-MM-dd" 형식의 문자열 날짜를 파싱하기 위한 포맷터를 설정
        LocalDate inputDate = LocalDate.parse(dateStr, formatter);        // dateStr을 LocalDate 형식으로 파싱(그냥 쉽게 문자로 된 날짜를 날짜 형태로 바꾸기)
        LocalDate currentDate = LocalDate.now();        // 현재 날짜를 가져오는 식???

        // 입력한 날짜가 현재 날짜 이전인 경우 예약 거절
        if (inputDate.isBefore(currentDate)) {
            System.out.println("당신은 타임머신타려고요? 지난 날짜는 예약 안돼요~.");
            return null;        //거절~
        }

        // 방이 있고 고객이 돈을 지불할 수 있는 경우
        if (room != null && customer.canAfford(room.getPrice())) {
            // 새로운 예약 ID를 생성
            UUID id = UUID.randomUUID();

            // 새로운 예약을 생성하고 reservations 리스트에 추가
            reservations.add(new Reservation(id, room, customer, dateStr));

            assets += room.getPrice();      // 총 자산 업데이트
            return id;                      // 예약 ID를 반환

        } else {
            // 조건을 만족하지 못하는 경우 예약을 거절하고 null을 반환
            return null;
        }
    }

    public Room findRoom(int roomCount, int roomKey) {        // 객실 번호로 객실 찾아 반환
        int i = 1;
        Room room = null;

        if (roomKey > roomCount || roomKey < 1) {           // 다른 번호 입력시
            return null;
        }

        for (String key : rooms.keySet()) {       // 방 번호로 키를 찾기 위해 map 탐색
            if (i++ == roomKey) {
                room = rooms.get(key);          // 방의 키를 사용하여 rooms 맵에서 해당 방의 정보 반환
                break;
            }
        }
        return room;
    }

    public int getRoomPrice(String roomKey) {
        Room room = rooms.get(roomKey);
        if (room != null) {
            return room.getPrice();
        }
        return -1;
    }

    public boolean cancelReservation(UUID id) { // 주어진 id와 일치하는 예약을 찾아 삭제
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                double p = reservation.getRoom().getPrice();
                assets -= p;                    // 객실 가격을 찾아 총 자산에서 차감
            }
        }
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    // 모든 예약 정보 반환 메소드
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    // 특정 고객의 예약 정보 반환 메소드
    public Reservation getCustomerReservations(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().toString().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    // 해당 날짜에 key 방이 예약 되어 있는지 확인
    public static boolean checkRooms(String key, String date) {
        boolean flag = true;
        for (Reservation r : reservations) {                                          // 전체 예약 목록 순회
            if (r.getRoom().getKey().equals(key) && r.getDate().equals(date)) {       // 해당 날짜(date)에 (key)방이 이미 예약되었는지 확인
                flag = false;                                                       // 예약이 되어 있으므로 예약 불가 (false return)
            }
        }
        return flag;
    }

    // 총 보유 자산 출력
    public void getAssets() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        String separatedAssets = df.format(assets);
        System.out.println(separatedAssets);
    }
}