import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class HotelReservationSystem {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();// 호텔 객체 생성
        Scanner scanner = new Scanner(System.in);// 입력을 위한 Scanner 객체 생성
        String[] roomKeyArr = {"small1", "small2", "big1", "big2", "veryBig1", "veryBig2"};

        // 사용자에게 선택할 수 있는 메뉴 표시
        while (true) {
            printMenu();

            int choiceMenu = scanner.nextInt();
            scanner.nextLine();

            switch (choiceMenu) {
                case 1:// 예약하기
                    String name = inputInfo("이름을 입력하세요", scanner);
                    //아래 메소드에서 설명
                    String phoneNumber = "";
                    int money = 0;
                    try {
                        phoneNumber = inputPhoneInfo(scanner);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    // 금액대신 문자를 입력하는 경우를 방지.
                    try {
                        money = inputMoneyInfo("소지 금액을 입력하세요: ", scanner);
                    } catch (InputMismatchException e) {
                        System.out.println("올바른 숫자를 입력해주세요");
                        scanner.nextLine();
                        break;
                    }

                    System.out.print("예약 날짜를 입력하세요 (예, 2023-10-27): ");
                    String date = scanner.nextLine();

                    System.out.println("해당 날짜에 예약 가능한 방은 다음과 같습니다.");
                    int i = 1;
                    for (String key : roomKeyArr) {
                        if (Hotel.checkRooms(key, date)) {                        // checkRoom() 메서드로 해당 날짜에 예약된 방 확인
                            System.out.println(i++ + ". " + key);               // 예약 가능한 방만 출력
                        }
                    }

                    if (i == 1) {                                                 // i가 증가되지 않았을 경우 예약 가능한 방이 존재하지 않음
                        System.out.println("\n해당 날짜에 예약 가능한 방이 존재하지 않습니다.");
                        System.out.print("다른 날짜를 입력하세요 (예, 2023-10-27): ");
                        date = scanner.nextLine();                              // 날짜를 다시 입력받음
                    }

                    System.out.print("\n방 키를 입력하세요 : ");
                    String roomKey = scanner.nextLine();

                    Customer customer = new Customer(name, phoneNumber, money);
                    // 예약 시도 및 결과 출력
                    date = date.strip();
                    System.out.println("date = " + date);
                    UUID reservationId = hotel.reserveRoom(roomKey, customer, date);
                    if (reservationId != null) {
                        System.out.println("예약 성공! 예약 ID는 다음과 같습니다: " + reservationId);
                    } else {
                        System.out.println("예약 실패!");
                    }
                    break;

                case 2:// 예약 취소하기
                    System.out.println("\n----------------------------------\n");
                    String id = inputInfo("취소할 예약 ID를 입력하세요: ", scanner);

                    boolean canceled = hotel.cancelReservation(UUID.fromString(id));
                    if (canceled) {
                        System.out.println("예약이 성공적으로 취소되었습니다!");
                    } else {
                        System.out.println("예약 취소에 실패했습니다!");
                    }
                    break;// 예약 ID를 받아와 예약 취소 로직 실행

                case 3: // 특정 고객의 예약 정보 출력
                    System.out.println("\n----------------------------------\n");
                    System.out.print("조회할 예약 ID를 입력하세요: ");
                    UUID rev_id = UUID.fromString(scanner.nextLine());

                    Reservation customerRev = hotel.getCustomerReservations(rev_id);        // 조회된 고객 Reservation 객체 반환
                    if (customerRev == null) {                                               // 반환된 객체가 없을 경우 잘못된 입력
                        System.out.println("잘못된 입력입니다.");
                    } else {                                                                // 있을 경우 출력
                        System.out.println("고객 이름: " + customerRev.getCustomerName());
                        System.out.println("방 타입: " + customerRev.getRoom().getType());
                        System.out.println("날짜: " + customerRev.getDate());
                        System.out.println("\n----------------------------------\n");
                    }
                    break;
                case 4:
                    System.out.println("안녕히 가세요!");
                    scanner.close();
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
        System.out.print("총 보유 자산 : ");
        hotel.getAssets();
        System.out.println("모든 예약:");
        if (hotel.getAllReservations().isEmpty()) {
            System.out.println("현재 예약이 없습니다.");
        }
        hotel.getAllReservations().forEach(reservation -> {
            System.out.println("예약 ID: " + reservation.getId());
            System.out.println("고객 이름: " + reservation.getCustomerName());
            System.out.println("전화번호: " + reservation.getPhoneNumber());
            System.out.println("방 타입: " + reservation.getRoom().getType());
            System.out.println("날짜: " + reservation.getDate());
            System.out.println("---------");
        });//toSting 으로 만들기
    }

    private static String inputInfo(String Info, Scanner sc) {
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
        } else {
            return checkPhoneNum;
        }
    }

    private static int inputMoneyInfo(String Info, Scanner sc) {
        System.out.println(Info);
        return sc.nextInt();
    }

    private static void printMenu() {
        System.out.println("호텔 예약 시스템에 오신 것을 환영합니다!");
        System.out.println("1. 예약하기");
        System.out.println("2. 예약 취소하기");
        System.out.println("3. 나의 예약 보기");
        System.out.println("4. 종료");
        System.out.println("0. 모든 예약 보기 (관리자)");
        System.out.print("선택을 입력하세요: ");

    }
}
