import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class HotelReservationSystem {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();                      // 호텔 객체 생성
        Scanner scanner = new Scanner(System.in);       // 입력을 위한 Scanner 객체 생성

        while (true) {
            printMenu();

            int choiceMenu = scanner.nextInt();                 // 메뉴 선택
            scanner.nextLine();

            switch (choiceMenu) {
                case 1 -> bookRoom(scanner, hotel);             // 객실 예약하기
                case 2 -> cancelReservation(scanner, hotel);    // 예약 ID로 예약 취소하기
                case 3 -> findReservation(scanner, hotel);      // 예약 ID로 예약 조회하기
                case 4 -> {
                    System.out.println("안녕히 가세요!");
                    scanner.close();
                    return;
                }
                case 0 -> allReservationList(hotel);            // 모든 예약 정보 출력
                default -> System.out.println("잘못된 선택입니다!");
            }
        }
    }

    private static void printMenu() {       // 사용자가 선택할 수 있는 메뉴 표시
        System.out.println("\n" + "=".repeat(40));  // '====...====' 패턴으로 상단 테두리 추가
        System.out.println("     AAA Hotel에 오신 것을 환영합니다!");
        System.out.println("=".repeat(40));         // '====...====' 패턴으로 하단 테두리 추가
        System.out.println();
        System.out.println("1. 예약하기       - 호텔의 빈 방을 예약할건데 굉장히 환영해요");
        System.out.println();
        System.out.println("2. 예약 취소하기   - 이미 예약한 방을 취소하시려고요...?");
        System.out.println();
        System.out.println("3. 나의 예약 보기  - 예약 정보는 확인하셔야죠?");
        System.out.println();
        System.out.println("4. 종료            - 시스템을 종료합니다.");
        System.out.println();
        System.out.println("0. 모든 예약 보기 (관리자) - 와서 뭐하시려고요...?");
        System.out.println();
        System.out.print("원하는 번호를 선택해주세요: ");
    }

        private static void bookRoom (Scanner sc, Hotel hotel){
            System.out.println("\n----------------------------------\n");

            String name = inputInfo("이름을 입력하세요: ", sc);
            String phoneNumber = inputPhoneInfo(sc);
            int money = inputMoneyInfo(sc);
            sc.nextLine();
            String date = inputDateInfo(sc);
            int roomCount = printRooms(sc, hotel, date);        // 예약 가능한 객실 리스트 출력, 객실 개수 반환
            System.out.print("방을 선택해주세요: ");
            int roomKey = sc.nextInt();
            sc.nextLine();
            Room room = hotel.findRoom(roomCount, roomKey);      // 객실 번호로 객실 찾아 저장
            if(room == null){
                System.out.println("잘못된 입력 입니다.");
            }

            Customer customer = new Customer(name, phoneNumber, money);

            // 예약 시도 및 결과 출력
            UUID reservationId = hotel.reserveRoom(room, customer, date);
            if (reservationId != null) {
                System.out.println("예약 성공! 예약 ID는 다음과 같습니다: " + reservationId);
            } else {
                System.out.println("예약 실패!");
            }
        }

        private static void cancelReservation (Scanner sc, Hotel hotel){
            System.out.println("\n----------------------------------\n");
            String id = inputInfo("취소할 예약 ID를 입력하세요: ", sc);

            boolean canceled = hotel.cancelReservation(UUID.fromString(id));
            if (canceled) {
                System.out.println("예약이 성공적으로 취소되었습니다!");
            } else {
                System.out.println("예약 취소에 실패했습니다!");
            }
        }

        private static void findReservation (Scanner sc, Hotel hotel){
            System.out.println("\n----------------------------------\n");
            System.out.print("조회할 예약 ID를 입력하세요: ");
            UUID rev_id = UUID.fromString(sc.nextLine());

            Reservation customerRev = hotel.getCustomerReservations(rev_id);        // 조회된 고객 Reservation 객체 반환
            if (customerRev == null) {                                               // 반환된 객체가 없을 경우 잘못된 입력
                System.out.println("잘못된 입력입니다.");
            } else {                                                                // 있을 경우 출력
                System.out.println(customerRev.toString());
            }
        }

        private static void allReservationList (Hotel hotel){
            System.out.print("총 보유 자산 : ");
            hotel.getAssets();

            System.out.println("[ 모든 예약 ]");
            if (hotel.getAllReservations().isEmpty()) {
                System.out.println("현재 예약이 없습니다.");
            }
            hotel.getAllReservations().forEach(reservation -> {
                System.out.println(reservation.toString());
            });
        }

        private static String inputInfo (String info, Scanner sc){
            System.out.print(info);
            return sc.nextLine();
        }

        private static String inputPhoneInfo (Scanner sc){      //정규화 추가, 휴대폰 형식이 000-0000-0000이 아니면 입력이 불가능
            System.out.print("휴대폰 번호를 입력하세요: ");
            String regexPhoneNum = "^010-[0-9]{4}-[0-9]{4}$";

            while (true) {
                String checkPhoneNum = sc.nextLine();
                if (Pattern.matches(regexPhoneNum, checkPhoneNum)) {
                    return checkPhoneNum;
                } else {
                    System.out.print("휴대폰 번호 형식이 일치하지 않습니다.\n다시 입력해주세요.: ");
                }
            }
        }

        private static int inputMoneyInfo (Scanner sc){
            System.out.print("소지 금액을 입력하세요: ");
            while (true) {
                if (sc.hasNextInt()) {
                    return sc.nextInt();
                } else {
                    System.out.println("숫자를 입력해주세요");
                    sc.nextLine();
                }
            }
        }
        private static String inputDateInfo (Scanner sc){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate currentDate = LocalDate.now();
            while (true) {
                System.out.print("예약 날짜를 입력하세요 (예, 2023-11-01): ");
                String inputDate = sc.nextLine();
                try {
                    LocalDate chosenDate = LocalDate.parse(inputDate, formatter); // 형식에 맞는지 검사
                    if (chosenDate.isBefore(currentDate)) {
                        System.out.println("당신은 타임머신타려고요? 지난 날짜는 예약 안돼요~.");
                        continue; // 과거의 날짜를 입력하면 다시 입력 요청
                    }
                    return inputDate;
                } catch (DateTimeParseException e) {
                    System.out.print("날짜 형식이 일치하지 않습니다. 다시 입력해주세요 (예, 2023-11-01): ");
                }
            }
        }

        private static int printRooms (Scanner sc, Hotel hotel, String date){           // 예약 가능한 객실 list 출력
            String[] roomKeyArr = {"Deluxe Twin", "Deluxe Double", "Premier Twin", "Premier Double", "Suite Twin", "Suite Double"};

            System.out.println("해당 날짜에 예약 가능한 방은 다음과 같습니다.");

            int i = 1;
            for (String key : roomKeyArr) {
                if (Hotel.checkRooms(key, date)) {              // checkRoom() 메서드로 해당 날짜에 예약된 방 확인
                    System.out.println(i++ + ". " + key + " (가격: $ " + hotel.getRoomPrice(key) + ")"); // 가격 정보 추가
                }
            }

            if (i == 1) {           // i가 증가되지 않았을 경우 예약 가능한 방이 존재하지 않음
                System.out.println("\n해당 날짜에 예약 가능한 방이 존재하지 않습니다.");
                date = inputInfo("다른 날짜를 입력하세요 (예, 2023-11-01): ", sc);  // 날짜를 다시 입력받음
                printRooms(sc, hotel, date);
            }

            return i;
        }
    }