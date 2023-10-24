import java.util.Scanner;
import java.util.UUID;

public class HotelReservationSystem {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();// 호텔 객체 생성
        Scanner scanner = new Scanner(System.in);// 입력을 위한 Scanner 객체 생성
        // 사용자에게 선택할 수 있는 메뉴 표시
        while (true) {
            System.out.println("호텔 예약 시스템에 오신 것을 환영합니다!");
            System.out.println("1. 예약하기");
            System.out.println("2. 예약 취소하기");
            System.out.println("3. 나의 예약 보기");
            System.out.println("4. 종료");
            System.out.println("0. 모든 예약 보기 (관리자)");
            System.out.print("선택을 입력하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:// 예약하기
                    System.out.print("이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    System.out.print("전화번호를 입력하세요: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("소지 금액을 입력하세요: ");
                    double money = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("방 키를 입력하세요 (small1, small2, big1, big2, veryBig1, veryBig2): ");
                    String roomKey = scanner.nextLine();
                    System.out.print("예약 날짜를 입력하세요 (예, 2023-10-27): ");
                    String date = scanner.nextLine();
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
                    System.out.print("취소할 예약 ID를 입력하세요: ");
                    String id = scanner.nextLine();
                    boolean canceled = hotel.cancelReservation(UUID.fromString(id));
                    if (canceled) {
                        System.out.println("예약이 성공적으로 취소되었습니다!");
                    } else {
                        System.out.println("예약 취소에 실패했습니다!");
                    }
                    break;// 예약 ID를 받아와 예약 취소 로직 실행

                case 3: // 특정 고객의 예약 정보 출력
                    System.out.print("예약을 조회할 이름을 입력하세요: ");
                    String customerName = scanner.nextLine();
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
                    scanner.close();
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
}