import java.time.LocalDate;

public class CalenderBook {
    public void printCalender() {
        LocalDate now = LocalDate.now();
        int thisYear = now.getYear(); // 연도
        int thisMonth = now.getMonthValue(); // 월
        int thisDay = now.getDayOfMonth(); // 일
        int thisDayOfWeek = now.getDayOfWeek().getValue(); // 월요일부터 (1,2,3,4,5,6,7)
        int endDay = now.lengthOfMonth(); // 마지막 일

        System.out.println();
        System.out.println("---------------------");
        System.out.println("      " + thisYear + "년 " + thisMonth + "월");
        System.out.println(" SU MO TU WE TH FR SA");

        for (int i = 1; i <= thisDayOfWeek; i++)
            System.out.print("   ");

        for (int day = thisDay, n = thisDayOfWeek; day <= endDay; day++, n++) {
            System.out.print((day < 10) ? "  " + day : " " + day);
            if ((n + 1) % 7 == 0) System.out.println();
        }
        System.out.println();

        if (endDay - thisDay < 14) { // 당월 달력의 노출 날짜가 2주 이하일 경우
            LocalDate temp = now.plusMonths(1).withDayOfMonth(1); // 다음 달 1일로 로컬데이터 변경
            int nextYear = temp.getYear(); // 다음 월의 연도
            int nextMonth = temp.getMonthValue(); // 다음 월
            int nextEndDay = temp.lengthOfMonth(); // 다음 월 마지막 날짜
            int nextMonthStartDayOfWeek = temp.getDayOfWeek().getValue(); // 다음 월 시작 요일

            System.out.println();
            System.out.println("---------------------");
            System.out.println("      " + nextYear + "년 " + nextMonth + "월");
            System.out.println(" SU MO TU WE TH FR SA");

            for (int i = 1; i <= nextMonthStartDayOfWeek; i++)
                System.out.print("   ");

            for (int day = 1, n = nextMonthStartDayOfWeek; day <= nextEndDay; day++, n++) {
                System.out.print((day < 10) ? "  " + day : " " + day);
                if ((n + 1) % 7 == 0) System.out.println();
            }
            System.out.println();
            System.out.println();

        }
    }
}
