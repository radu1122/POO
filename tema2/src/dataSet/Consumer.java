package dataSet;

public class Consumer {
    private int id;
    private int budget;
    private int monthlyIncome;
    private boolean isBankrupt = false;
    private int currInvoice = 0;
    private int lastInvoice = 0;
    private boolean hasContract = false;
    private int distributorId;
    private int contractPrice = 0;
    private int remainedContractMonths = -1;
    private int distributorProfit = 0;

    public Consumer(int id, int budget, int monthlyIncome) {
        this.id = id;
        this.budget = budget;
        this.monthlyIncome = monthlyIncome;
    }

    public void distributorBankrupt() {
        this.resetContract();
    }

    public void resetContract() {
        Distributors.getInstance().getDistributors().get(distributorId).getContracts().remove(id);
        int x = Distributors.getInstance().getDistributors().get(distributorId).getNumberOfClients();
        Distributors.getInstance().getDistributors().get(distributorId).setNumberOfClients(x - 1);
        distributorId = 0;
        contractPrice = 0;
        remainedContractMonths = -1;
        lastInvoice = 0;
        currInvoice = 0;
        hasContract = false;
        distributorProfit = 0;
    }

    public void declareBankruptcy() {
        isBankrupt = true;
        Distributors.getInstance().getDistributors().get(this.distributorId).clientBankrupt(id);
    }

    public void generateBill() {
        if (!isBankrupt) {
            this.currInvoice = contractPrice;
            remainedContractMonths--;
            Contract contract = Distributors.getInstance().getDistributors().get(distributorId).getContracts().get(id);
            contract.setRemainedContractMonths(remainedContractMonths);
        }

    }

    public void payBills() {
        this.budget = this.budget + monthlyIncome;
        if (lastInvoice != 0) {
            int bill = (int) (Math.round(Math.floor(1.2 * lastInvoice)) + currInvoice);
            if (budget - bill < 0) {
                this.declareBankruptcy();
            } else {
                budget = budget - bill;
                Distributors.getInstance().getDistributors().get(distributorId).
                        receivePayment(bill);
            }
        } else {
            if (budget - currInvoice <= 0) {
                lastInvoice = currInvoice;
            } else {
                budget = budget - currInvoice;
                Distributors.getInstance().getDistributors().get(distributorId).receivePayment(currInvoice);
            }
            currInvoice = 0;
        }
    }

    public int getDistributorProfit() {
        return distributorProfit;
    }

    public void setDistributorProfit(int distributorProfit) {
        this.distributorProfit = distributorProfit;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return budget;
    }

    public void setInitialBudget(int initialBudget) {
        this.budget = initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getCurrInvoice() {
        return currInvoice;
    }

    public void setCurrInvoice(int currInvoice) {
        this.currInvoice = currInvoice;
    }

    public int getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(int lastInvoice) {
        this.lastInvoice = lastInvoice;
    }

    public boolean getHasContract() {
        return hasContract;
    }

    public void setHasContract(boolean hasContract) {
        this.hasContract = hasContract;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"isBankrupt\":" + isBankrupt +
                ", \"budget\":" + budget +
                '}';
    }
}
