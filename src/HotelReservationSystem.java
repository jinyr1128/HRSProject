import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

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

            reservation:
            switch (choice) {
                case 1:// 예약하기
                    String name = inputInfo("이름을 입력하세요", sc);
                    try {
                        String phoneNum = inputPhoneInfo(sc);
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    // 금액대신 문자를 입력하는 경우를 방지.
                    try {
                        int money = inputMoneyInfo("소지 금액을 입력하세요: ", sc);
                        String roomKey  = inputInfo("방을 선택해주세요", sc);
                        String reserveDate = inputInfo("예약 날짜를 입력하세요 (예, 2023-10-27): ", sc);
                    } catch (InputMismatchException e) {
                        System.out.println("올바른 숫자를 입력해주세요");
                        sc.nextLine();
                        break;
                    }

                    // [구현] 날짜 형식 변경 필요
//                    Customer customer = new Customer(name, phoneNumber, money);
//                    // 예약 시도 및 결과 출력
//                    UUID reservationId = hotel.reserveRoom(roomKey, customer, date);
//                    if (reservationId != null) {
//                        System.out.println("예약 성공! 예약 ID는 다음과 같습니다: " + reservationId);
//                    } else {
//                        System.out.println("예약 실패!");
//                    }
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
                    allReservationList(hotel);
                    break; // 모든 예약 정보 출력
                default:
                    System.out.println("잘못된 선택입니다!");
                    break;
            }
        }
    }




    private static void allReservationList(Hotel hotel) {
        System.out.println("모든 예약:");
        if(hotel.getAllReservations().isEmpty()){
            System.out.println("현재 예약이 없습니다.");
        }
        hotel.getAllReservations().forEach(reservation -> {
            System.out.println("예약 ID: " + reservation.getId());
            System.out.println("고객 이름: " + reservation.getCustomerName());
            System.out.println("전화번호: " + reservation.getPhoneNumber());
            System.out.println("방 타입: " + reservation.getRoom().getType());
            System.out.println("날짜: " + reservation.getDate());
            System.out.println("---------");
        });
    }

    private static String inputInfo(String Info, Scanner sc){
        System.out.println(Info);
        return sc.nextLine();
    }

    /*
    튜터님께서 정규화를 생성자에서 받아오는 것보다 메인에서 메소드를 따로 파서 이것을 활용하는게 좋다고 하셔서 따로 팠습니다.
    return null  break 등 여러 방법을 써봤는데  휴대폰번호 형식을 틀려도 계속 다음 스위치문이 이어져서
    정규화된 000-0000-0000 형식과 sc에서 입력된 휴대폰번호 형식이 다를 경우
    에러를 생성해서 catch문에서 break하는 형식으로 사용했습니다.
     */
    private static String inputPhoneInfo(Scanner sc) throws IllegalArgumentException {
        System.out.println("휴대폰 번호를 입력하세요");

        //정규화 추가    휴대폰 형식이 000-0000-0000이 아니면 입력이 불가능
//        String regexPhoneNum = " ^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        String regexPhoneNum = "^010-[0-9]{4}-[0-9]{4}$";
        String checkPhoneNum = sc.nextLine();
        if (!Pattern.matches(regexPhoneNum, checkPhoneNum)) {
            throw new IllegalArgumentException("휴대폰 번호 형식이 일치하지 않습니다.");
        }else{
            return checkPhoneNum;
        }
    }
    private static int inputMoneyInfo(String Info, Scanner sc){
        System.out.println(Info);
        return sc.nextInt();
    }
}