// SalesContract.java
package com.pluralsight.cardealership.model;

public class SalesContract extends Contract {
    private final double salesTax;
    private final double recordingFee = 100.00;
    private final double processingFee;
    private final boolean isFinanced;
    private final double annualInterestRate;
    private final int loanTerm;
    private final double monthlyPayment;

    public SalesContract(int contractID, String startDate, String customerName, String customerEmail, int customerId, Vehicle vehicle, boolean isFinanced) {
        super(startDate, customerName, customerEmail, customerId, vehicle, contractID);
        this.salesTax = 0.05 * vehicle.getPrice();
        if (vehicle.getPrice() < 10000) {
            this.processingFee = 295.00;
        } else {
            this.processingFee = 495.00;
        }
        this.isFinanced = isFinanced;
        if (isFinanced && vehicle.getPrice() >= 10000) {
            this.annualInterestRate = 0.0425;
            this.loanTerm = 24;
        } else if (isFinanced && vehicle.getPrice() < 10000) {
            this.annualInterestRate = 0.0525;
            this.loanTerm = 48;
        } else {
            this.annualInterestRate = 0.0;
            this.loanTerm = 1;
        }
        this.monthlyPayment = getMonthlyPayment();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sales Contract\n");
        sb.append("Date: ").append(getStartDate()).append("\n");
        sb.append("Customer: ").append(getCustomerName()).append(" (").append(getCustomerEmail()).append(")\n");
        sb.append("Customer ID: ").append(getCustomerId()).append("\n");
        sb.append("Vehicle: ").append(getVehicle().getYear()).append(" ").append(getVehicle().getMake()).append(" ").append(getVehicle().getModel()).append("\n");
        sb.append("Price: ").append(getVehicle().getPrice()).append("\n");
        sb.append("Sales Tax: ").append(salesTax).append("\n");
        sb.append("Recording Fee: ").append(recordingFee).append("\n");
        sb.append("Processing Fee: ").append(processingFee).append("\n");
        sb.append("Total Price: ").append(getTotalPrice()).append("\n");
        if (isFinanced) {
            sb.append("Financed: Yes\n");
            sb.append("Annual Interest Rate: ").append(annualInterestRate).append("\n");
            sb.append("Loan Term: ").append(loanTerm).append(" months\n");
            sb.append("Monthly Payment: ").append(monthlyPayment).append("\n");
        } else {
            sb.append("Financed: No\n");
        }
        return sb.toString();
    }

    @Override
    public double getTotalPrice() {
        return getVehicle().getPrice() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (isFinanced) {
            double principal = getTotalPrice();
            double monthlyInterestRate = annualInterestRate / 12;
            return (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTerm));
        } else {
            return getTotalPrice();
        }
    }

    @Override
    public String formatContract(Contract contract, Vehicle vehicle) {
        return String.format("SALE |%-10s|%-15s|%-30s|%-7d|%-5d|%-10s|%-10s|%-10s|%-10s|%-7d|%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-9.2f|%-7b|%-7.3f",
                contract.getStartDate(), contract.getCustomerName(), contract.getCustomerEmail(), vehicle.getVin(), vehicle.getYear(), vehicle.getMake(),
                vehicle.getModel(), vehicle.getType(), vehicle.getColor(), contract.getCustomerId(), vehicle.getPrice(), salesTax, recordingFee,
                processingFee, getTotalPrice(), isFinanced, annualInterestRate);
    }

    public boolean isFinanced() {
        return isFinanced;
    }

    public int getLoanTerm() {
        return loanTerm;
    }
}