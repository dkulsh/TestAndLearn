package Coding;

import com.sun.source.tree.Tree;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ATM {

    private TreeMap<Integer, Integer> totalMoney = new TreeMap<>();
    private Integer totalAmount = 0;

    private static Set<Integer> validDenominations;
    private static final String DEPOSIT = "Deposit";
    private static final String WITHDRAW = "Withdraw";

    public static void main(String[] args) {

        ATM atm = new ATM();

        if (atm.noDenominations(args)) return;

        while(true) {
            Scanner read = new Scanner(System.in);
            String line = read.nextLine();

            String transactionType = line.substring(0, line.indexOf(":"));
            String amount = line.substring(line.indexOf(":") + 2);
            atm.transact(transactionType, amount);
        }

    }

    private boolean noDenominations(String[] args) {

        validDenominations = Arrays.stream(args).map(Integer::valueOf).collect(Collectors.toSet());
        if (validDenominations.isEmpty()) {
            System.out.println("No valid denominations found !!");
            System.out.println("Restart application passing valid denominations as program args. ( separated by spaces )");
            return true;
        }

        return false;
    }

    public static boolean addNewDenomination(int denomination){
        return validDenominations.add(denomination);
    }

    private void transact(String type, String amount){

        String transaction = type.split(" ")[0];

        switch (transaction) {

            case DEPOSIT:
                String[] amounts = amount.split(",");
                deposit(amounts);
                break;

            case WITHDRAW:
                withdraw(Integer.valueOf(amount));
                break;

            default:
                System.out.println("Unknown transaction type :: " + transaction);

        }
    }

    private boolean deposit(String[] amounts){

        Map<Integer, Integer> moneyCount = mapAmounts(amounts);

        if (! depositValidationsSuccess(moneyCount)) { return false; }

        for (Map.Entry<Integer, Integer> entry : moneyCount.entrySet()) {

            totalMoney.merge(entry.getKey(), entry.getValue(), (v1, v2) -> v1 + v2);
            totalAmount = totalAmount + (entry.getKey() * entry.getValue());
        }

        System.out.println(totalMoney + ". Total = " + totalAmount);
        return true;
    }

    @NotNull
    private Map<Integer, Integer> mapAmounts(String[] amounts) {

        Map<Integer, Integer> moneyCount = new HashMap<>();

        for (String amount: amounts) {

            String[] split = amount.trim().split(":");
            int denomination = Integer.parseInt(split[0].replace("s", ""));
            int count = Integer.parseInt(split[1].trim());

            moneyCount.put(denomination, count);
        }
        return moneyCount;
    }

    private boolean withdraw(int amount) {

        if (! withdrawValidationsSuccess(amount)) { return false; }

        Map<Integer, Integer> withdrawBills = new HashMap<>();

        Integer highestDenomination = totalMoney.lastKey();

        while (highestDenomination > amount){

            highestDenomination = totalMoney.lowerKey(highestDenomination);
            if (highestDenomination == null) {
                System.out.println("Requested withdraw amount NOT dispensable");
                return false;
            }
        }

        while (amount > 0) {

            int numberofBillsNeeded = amount / highestDenomination;
            int numberOfBillsAvailable = totalMoney.get(highestDenomination);

            if (numberOfBillsAvailable < numberofBillsNeeded) {
                withdrawBills.put(highestDenomination, numberOfBillsAvailable);
                amount = amount - (highestDenomination * numberOfBillsAvailable);
            } else {
                withdrawBills.put(highestDenomination, numberofBillsNeeded);
                amount = amount - (highestDenomination * numberofBillsNeeded);
            }

            if (amount == 0) {
                break;
            }

            highestDenomination = totalMoney.lowerKey(highestDenomination);
            if (highestDenomination == null) {
                System.out.println("Requested withdraw amount NOT dispensable");
                return false;
            }
        }

        Map<Integer, Integer> filteredWithdrawBills = withdrawBills.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : filteredWithdrawBills.entrySet()) {

            totalMoney.merge(entry.getKey(), entry.getValue(), (v1, v2) -> v1 - v2);
            totalAmount = totalAmount - (entry.getKey() * entry.getValue());
        }

        System.out.println("Dispensed : " + filteredWithdrawBills);
        System.out.println("Balance : " + totalMoney + ". Total = " + totalAmount);
        return true;
    }

    private boolean depositValidationsSuccess(Map<Integer, Integer> moneyCount) {

        for (Map.Entry<Integer, Integer> entry: moneyCount.entrySet()){

            if (entry.getValue() < 0) {
                System.out.println("Incorrect Deposit amount.");
                return false;
            }
            if (entry.getValue() == 0) {
                System.out.println("Deposit amount cannot be zero.");
                return false;
            }
            if (! validDenominations.contains(entry.getKey())) {
                System.out.println(entry.getKey() + " is not a valid denomination !");
                return false;
            }
        }
        return true;
    }

    private boolean withdrawValidationsSuccess(Integer amount) {

        if (amount < 0 || amount == 0 || amount > totalAmount) {
            System.out.println("Incorrect or Insufficient funds");
            return false;
        }

        return true;
    }

    private boolean validationAmount(String input) {
        return false;
    }

    public Map<Integer, Integer> getTotalCash(){
        return totalMoney;
    }

    public Integer getTotalAmount(){
        return totalAmount;
    }
}
