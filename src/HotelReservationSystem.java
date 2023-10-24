import java.util.Scanner;
import java.util.UUID;

public class HotelReservationSystem {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();// 호텔 객체 생성
        Scanner sc = new Scanner(System.in);// 입력을 위한 Scanner 객체 생성
        // 사용자에게 선택할 수 있는 메뉴 표시
        while (true) {
            System.out.println("호텔 예약 시스템에 오신 것을 환영합니다!");
            System.out.println("1. 예약하기");
            System.out.println("2. 예약 취소하기");
            System.out.println("3. 나의 예약 보기");
            System.out.println("4. 종료");
            System.out.println("0. 모든 예약 보기 (관리자)");
            System.out.print("선택을 입력하세요: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:// 예약하기
                    String name = inputInfo("이름을 입력하세요", sc);
                    String phoneNum = inputInfo("휴대폰 번호를 입력하세요", sc);
                    // 휴대폰번호는 000-0000-0000 이 양식을 검증하는 과정이 필요하다고 생각해요.

        /*          int money = inputMoneyInfo("소지 금액을 입력하세요: ", sc);
                    코드를 치다가 든 생각인데 소지금액을 입력하라는건 너무 이상한 느낌이 드네요
                    혹시 괜찮으시면 고객에 지갑 변수를 하나 파서
                    지갑에 든 돈과 방 금액을 비교하는 코드를 사용해보는건 어떨까요??
                    우선순위가 낮은 요청사항이긴 합니다.
*/
                    String roomKey  = inputInfo("방을 선택해주세요", sc);
                    String reserveDate = inputInfo("예약 날짜를 입력하세요 (예, 2023-10-27): ", sc);

                    // [구현] 날짜 형식 변경 필요
                    Customer customer = new Customer(name, phoneNumber, money);
                    // 예약 시도 및 결과 출력
                    UUID reservationId = hotel.reserveRoom(roomKey, customer, date);
                    if (reservationId != null) {
                        System.out.println("예약 성공! 예약 ID는 다음과 같습니다: " + reservationId);
                    } else {
                        System.out.println("예약 실패!");
                    }
                    break;

                case 2:// 예약 취소하기
                    String id = inputInfo("취소할 예약 ID를 입력하세요: ", sc);

                    boolean canceled = hotel.cancelReservation(UUID.fromString(id));
                    if (canceled) {
                        System.out.println("예약이 성공적으로 취소되었습니다!");
                    } else {
                        System.out.println("예약 취소에 실패했습니다!");
                    }
                    break;// 예약 ID를 받아와 예약 취소 로직 실행

                case 3: // 특정 고객의 예약 정보 출력
                    String customerName = inputInfo("예약을 조회할 이름을 입력하세요: ", sc);
                    if(hotel.getCustomerReservations(customerName).isEmpty()) {
                        System.out.println("잘못된 입력입니다.");
                    } else {
                        hotel.getCustomerReservations(customerName).forEach(reservation -> {
                            System.out.println("예약 ID: " + reservation.getId());
                            System.out.println("방 타입: " + reservation.getRoom().getType());
                            System.out.println("날짜: " + reservation.getDate());
                            System.out.println("---------");
                        });//고객 이름을 받아와 해당 고객의 예약 정보 출력
                    }
                    break;

                case 4:
                    System.out.println("안녕히 가세요!");
                    sc.close();
                    return;

                case 0: // 관리자를 위한 모든 예약 정보 출력
                    System.out.println("모든 예약:");
                    hotel.getAllReservations().forEach(reservation -> {
                        System.out.println("예약 ID: " + reservation.getId());
                        System.out.println("고객 이름: " + reservation.getCustomerName());
                        System.out.println("전화번호: " + reservation.getPhoneNumber());
                        System.out.println("방 타입: " + reservation.getRoom().getType());
                        System.out.println("날짜: " + reservation.getDate());
                        System.out.println("---------");
                    });
                    break; // 모든 예약 정보 출력
                default:
                    System.out.println("잘못된 선택입니다!");
                    break;
            }
        }
    }
    private static String inputInfo(String Info, Scanner sc){
        System.out.println(Info);
        return sc.nextLine();
    }
//    private static double inputMoneyInfo(int Info, Scanner sc){
//        System.out.println(Info);
//        return sc.nextInt();
//    }    소지금액 입력 메소드지만  바뀐다면 삭제해야하기 때문에 일단 주석처리해뒀습니다.
}