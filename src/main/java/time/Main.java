package time;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Main {
    record MonthYear(int month, int year) {
        public MonthYear {
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }
            if (year < 1 ||year > 9999) {
                throw new IllegalArgumentException("Year should be positive and not be more than the end of the world");
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            MonthYear monthYear = getMonthYear(args); 
            printCalendar(monthYear);
        } 
        catch (NumberFormatException e) {
            System.out.println("Only numbers suitable");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        catch (RuntimeException e) {
                e.printStackTrace();
        }
        catch (Exception e) {
           System.out.println(e.getMessage());
        }
       
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printDates(MonthYear monthYear) {
        int firstDayOfWeek = getFirstDayofWeek(monthYear); 
        int lastDayOfMonth = getLastDayOfMonth(monthYear); 
        int offset = getOffset(firstDayOfWeek); 
    

        for (int i = 0; i < offset; i++) {
            System.out.printf("%4s", ""); 
        }
        
        for (int day = 1; day <= lastDayOfMonth; day++) {
            System.out.printf("%4d", day);
            if ((day + offset) % 7 == 0) {
                System.out.println(); 
            }
        }
        System.out.println();
    }

    private static void printWeekDays() {
        DateFormatSymbols df = new DateFormatSymbols(Locale.US);
        String [] weeks = df.getShortWeekdays();
        for (int i = 1; i < weeks.length; i++) {
            System.out.printf("%4s", weeks[i]);
        }
        System.out.println();
    }

    private static void printTitle(MonthYear monthYear) {
        String monthName = Month.of(monthYear.month()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.US) ; 
        System.out.printf("%12d - %s\n", monthYear.year(), monthName);
    }

    private static MonthYear getMonthYear (String[] args) throws Exception {
        LocalDate currentDate = LocalDate.now();
        int month = args.length > 1 ? Integer.parseInt(args[0]) : currentDate.getMonthValue();
        int year = args.length > 0 ? Integer.parseInt(args[1]) : currentDate.getYear();
        return new MonthYear(month, year);
    }
    private static int getFirstDayofWeek(MonthYear monthYear) {
        return LocalDate.of(monthYear.year(), monthYear.month(), 1).getDayOfWeek().getValue();
    }
    private static int getOffset(int firstWeekDay) {
        return firstWeekDay;
    }
    private static int getLastDayOfMonth(MonthYear monthYear) {
        return LocalDate.of(monthYear.year(), monthYear.month(), 1).lengthOfMonth();
    }
}