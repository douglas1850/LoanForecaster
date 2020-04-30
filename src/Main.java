import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {

    private static float monthlyPaymentAmount;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("How many loans do you have?");
        int numLoans = in.nextInt();

        System.out.println("How much will you be setting aside for payments each month?");
        monthlyPaymentAmount = in.nextFloat();

        ArrayList<Loan> loans = new ArrayList<>();

        for(int i = 1; i <= numLoans; i++) {
            System.out.println("Enter the amount owed on Loan #" + i + ":");
            float amountOwed = in.nextFloat();
            System.out.println("Enter the interest rate on Loan #" + i + ":");
            float interestRate = in.nextFloat();
            System.out.println("Enter the minimum monthly payment on Loan #" + i + ":");
            float minPayment = in.nextFloat();

            Loan loan = new Loan(amountOwed, interestRate, minPayment);
            loans.add(loan);
        }

        // Now have all my loan objects. Need to sort them by interest rate
        boolean sorted = false;
        Loan temp;
        while(!sorted) { // bubble sort the loan objects by interest rate
            sorted = true;
            for (int i = 0; i < loans.size()-1; i++) {
                if(loans.get(i).getInterestRate() < loans.get(i+1).getInterestRate()) {
                    temp = loans.get(i);
                    loans.set(i, loans.get(i+1));
                    loans.set(i+1, temp);
                    sorted = false;
                }
            }
        }

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        // Now have a sorted array list of all my Loan objects. Time to start calculating
        while(!loans.isEmpty()) {

            System.out.println("Date: " + month + "/" + year + "\nNumber of loans remaining: " + loans.size());

            float amountRemaining = monthlyPaymentAmount;

            // make your minimum payments
            System.out.println("Making monthly minimum payments...");
            for (Loan loan : loans) {
                amountRemaining = loan.makeMinPayment(amountRemaining);
                if (amountRemaining <= 0) {
                    System.out.println("Couldn't make minimum payments! That sucks.");
                    break;
                }
            }

            //use whatever money you have left to pay off the highest interest loan
            System.out.println("$" + amountRemaining + " remaining after paying minimum payments. Spending the rest on the highest interest loan.");
            for(Iterator<Loan> itr = loans.iterator(); itr.hasNext();) {
                Loan loan = itr.next();
                if(amountRemaining > 0) {
                    amountRemaining = loan.updateAmountRemaining(amountRemaining);
                } else {
                    break;
                }
                if (loan.getAmountRemaining() == 0) {
                    itr.remove();
                }
            }

            // at the end of the month, accrue interest
            for (Loan loan : loans) {
                loan.accrueInterest();
            }
            month++;
            if(month > 12) {
                month = 1;
                year++;
            }
        }
        System.out.println("All loans have been paid off! Payments are completed as of " + month + "/" + year);
    }
}
