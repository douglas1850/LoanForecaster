public class Loan {

    private float amountRemaining;
    private float interestRate;
    private float minimumPayment;

    public Loan(float amountRemaining, float interestRate, float minimumPayment) {
        this.amountRemaining = amountRemaining;
        this.interestRate = interestRate;
        this.minimumPayment = minimumPayment;
    }

    public float updateAmountRemaining(float payment) {

        // if the payment is more than the amountRemaining, you have money left over to pay into another loan
        if(payment > amountRemaining) {
            payment -= amountRemaining;
            amountRemaining = 0;
            System.out.println("You paid off a loan! $" + payment + " is remaining this month.");
            return payment;
        } else { // your payment is less than the amount due. Subtract it from the amount due and return 0, no money left
            amountRemaining -= payment;
            System.out.println("The amount remaining on this loan is: " + amountRemaining);
            return 0;
        }
    }

    public float makeMinPayment(float payment) {
        // if the payment is more than the amountRemaining, you have money left over to pay into another loan
        if(payment > minimumPayment) {
            payment -= minimumPayment;
            if(amountRemaining < minimumPayment) {
                amountRemaining = 0;
            } else {
                amountRemaining -= minimumPayment;
            }
        } else { // your payment is less than the minimum due. Uh oh.
            System.out.println("Didn't have enough to make the minimum payment.");
            amountRemaining -= payment;
            payment = 0;
        }
        return payment;
    }

    public void accrueInterest() {
        amountRemaining += (amountRemaining * (interestRate/100.0F));
        amountRemaining = Math.round(amountRemaining);
    }

    public float getAmountRemaining() {
        return amountRemaining;
    }

    public float getInterestRate() {
        return interestRate;
    }

}
