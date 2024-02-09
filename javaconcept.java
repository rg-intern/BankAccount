/**
Answer to Question: The only abstract class is the BankAccount class and the rest are 
concrete classes. My reasoning behind this is that the endOfMonth method is fully implemented 
in all classes besides the BankAccount class. And the endOfMonth method is the reason for 
the BankAccount class becaming abstract in the first place.
BankAccount Class
*/

/**
 * A bank account has a balance that can be changed by
 * deposits and withdrawals.
 */
public abstract class BankAccount
{
 private double balance;
 
 /**
 * Constructs a bank account with a zero balance.
 */
 public BankAccount()
 {
 balance = 0;
 }
 
 /**
 * Constructs a bank account with a given balance.
 * @param initialBalance the initial balance
 */
 public BankAccount(double initialBalance)
 {
 balance = initialBalance;
 }
 
 /**
 * Deposits money into the bank account.
 * @param amount the amount to deposit
 */
 public void deposit(double amount)
 {
 balance = balance + amount;
 }
 
 /**
 * Withdraws money from the bank account.
 * @param amount the amount to withdraw
 */
 public void withdraw(double amount)
 {
 balance = balance - amount;
 }
 
 /**
 * Gets the current balance of the bank account.
 * @return the current balance
 */
 public double getBalance()
 {
 return balance;
 }
 
 /**
 * Transfers money from the bank account to another account.
 * @param amount the amount to transfer
 * @param other the other account
 */
 public void transfer(double amount, BankAccount other)
 {
 withdraw(amount);
 other.deposit(amount);
 }
 /**
 * Abstract method to be implemented by subclasses.
 * This method is called at the end of each month.
 */
 public abstract void endOfMonth();
}
Checking Account Class
/**
 * A checking account that charges transaction fees.
 */
public class CheckingAccount extends BankAccount
{
 private int transactionCount;
 
 private static final int FREE_TRANSACTIONS = 3;
 private static final double TRANSACTION_FEE = 2.0;
 
 /**
 * Constructs a checking account with a given balance
 * @param initialBalance the initial balance
 */
 public CheckingAccount(double initialBalance)
 {
 // Construct superclass
 super(initialBalance);
 
 // Initialize transaction count
 transactionCount = 0;
 }
 
 public void deposit(double amount)
 {
 transactionCount++;
 // Now add amount to balance
 super.deposit(amount);
 }
 
 public void withdraw(double amount)
 {
 transactionCount++;
 // Now subtract amount from balance
 super.withdraw(amount);
 }
 
 /**
 * Deducts the accumulated fees and resets the
 * transaction count.
 */
 public void endOfMonth()
 {
 if (transactionCount > FREE_TRANSACTIONS)
 {
 double fees = TRANSACTION_FEE *
 (transactionCount - FREE_TRANSACTIONS);
 super.withdraw(fees);
 }
 transactionCount = 0;
 }
}
SavingsAccount Class
/**
 * An account that earns interest at a fixed rate
 */
public class SavingsAccount extends BankAccount
{
 private double interestRate;
 private double minBalance;
 /**
 * Constructs a bank account with a given interest rate.
 * @param rate the interest rate
 */
 public SavingsAccount(double rate)
 {
 interestRate = rate;
 minBalance = getBalance();
 }
 
 /**
 * Adds the earned interest to the account balance.
 */
 public void endOfMonth()
 { 
 if (minBalance == 0) {
 minBalance = getBalance();
 }
 double interest = minBalance * (interestRate / 100);
 deposit(interest);
 minBalance = getBalance(); // Update minimum balance for the next month
 }
 /**
 * Deposit money into the account.
 * @param amount the amount to deposit
 */
 public void initalDeposit(double amount)
 {
 minBalance = amount;
 deposit(amount);
 }
}
TimeDepositAccount Class
public class TimeDepositAccount extends BankAccount
{
 private double interestRate;
 private int monthsToMaturity;
 private int remainingMonths;
 private double minBalance;
 private static final double WITHDRAWAL_PENALTY = 10.0;
 /**
 * Constructs a time deposit account with a given interest rate and months to maturity.
 * @param rate the interest rate
 * @param monthsToMaturity the number of months to maturity
 */
 public TimeDepositAccount(double rate, int monthsToMaturity)
 {
 interestRate = rate;
 this.monthsToMaturity = monthsToMaturity;
 remainingMonths = monthsToMaturity;
 minBalance = getBalance();
 }
 
 /**
 * Adds the earned interest to the account balance.
 */
 public void endOfMonth()
 {
 if (remainingMonths > 0) {
 double interest = minBalance * (interestRate / 100);
 deposit(interest);
 remainingMonths--;
 }
 }
 /**
 * Overrides the withdraw method to apply a penalty for early withdrawal.
 * @param amount the amount to withdraw
 */
 public void withdraw(double amount)
 {
 if (remainingMonths > 0) {
 super.withdraw(amount + WITHDRAWAL_PENALTY);
 } else {
 super.withdraw(amount);
 }
 }
 public void initalDeposit(double amount)
 {
 minBalance = amount;
 deposit(amount);
 }
}
BankAccountTest Class
/**
 * Test class for the bank accounts.
 */
public class BankAccountTest
{
 /**
 * Test method to demonstrate the functionality of bank accounts.
 * @param account the bank account to test
 */
 public static void test(BankAccount account)
 {
 // Make five transactions
 account.deposit(100);
 account.withdraw(50);
 account.deposit(200);
 account.withdraw(20);
 account.deposit(150);
 
 // Call endOfMonth to process interest or fees
 account.endOfMonth();
 
 // Print the balance after the transactions and at the end of the month
 System.out.println("Balance after transactions and at the end of the month: " + 
account.getBalance());
 }
 /**
 * Main method to run the test.
 * @param args command line arguments (not used)
 */
 public static void main(String[] args)
 {
 // Test with instances of all concrete account classes
 // Checking Account
 CheckingAccount checkingAccount = new CheckingAccount(500);
 System.out.println("Checking Account:");
 test(checkingAccount);
 // Savings Account
 SavingsAccount savingsAccount = new SavingsAccount(5);
 savingsAccount.initalDeposit(100.0);
 System.out.println("\nSavings Account:");
 test(savingsAccount);
 // Time Deposit Account
 TimeDepositAccount timeDepositAccount = new TimeDepositAccount(8, 12);
 timeDepositAccount.initalDeposit(100.0);
 System.out.println("\nTime Deposit Account:");
 test(timeDepositAccount);
 }
}
