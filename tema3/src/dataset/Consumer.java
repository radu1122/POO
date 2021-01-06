package dataset;

public final class Consumer extends Entity {
    private int id;
    private int budget;
    private int monthlyIncome;
    private boolean isBankrupt = false;
    private int currInvoice = 0;
    private int lastInvoice = 0;
    private boolean hasContract = false;
    private int distributorIdLastInvoice;
    private int distributorId;
    private int contractPrice = 0;
    private int remainedContractMonths = -1;
    private int distributorProfit = 0;

    public Consumer() {

    }

    /**
     * populate the class
     *
     */
    public Consumer populateEntity(final int idX, final int budgetX, final int monthlyIncomeX) {
        this.id = idX;
        this.budget = budgetX;
        this.monthlyIncome = monthlyIncomeX;
        return this;
    }

    /**
     * trigger the reset contract on distributor bankrupt
     *
     */
    public void distributorBankrupt() {
        this.resetContractData();
    }

    /**
     * trigger reset all the contracts fields and delete it from distributor
     *
     */
    public void resetContract() {
        Distributors.getInstance().getDistributors().get(distributorId).getContracts().remove(id);
        int x = Distributors.getInstance().getDistributors().get(distributorId).
                getNumberOfClients();
        Distributors.getInstance().getDistributors().get(distributorId).setNumberOfClients(x - 1);
        this.resetContractData();
    }

    /**
     * reset all the contracts fields
     *
     */
    public void resetContractData() {
        distributorId = 0;
        contractPrice = 0;
        remainedContractMonths = -1;
        currInvoice = 0;
        hasContract = false;
        distributorProfit = 0;
    }

    /**
     * bankruptcy trigger
     *
     */
    public void declareBankruptcy() {
        isBankrupt = true;
        Distributors.getInstance().getDistributors().get(this.distributorId).clientBankrupt(id);
    }

    /**
     * generate montly bill
     *
     */
    public void generateBill() {
        if (!isBankrupt) {
            this.currInvoice = contractPrice;
            remainedContractMonths--;
            Contract contract = Distributors.getInstance().getDistributors().
                    get(distributorId).getContracts().get(id);
            contract.setRemainedContractMonths(remainedContractMonths);
        }

    }

    /**
     * pay monthly bills and add montly income to the budget
     *
     */
    public void payBills() {
        this.budget = this.budget + monthlyIncome;
        double indices = 1.2;
        if (lastInvoice != 0) {
            int bill = (int) (Math.round(Math.floor(indices * lastInvoice)) + currInvoice);
            if (Distributors.getInstance().getDistributors().
                    get(distributorIdLastInvoice).isBankrupt()) {
                bill = currInvoice;
            }
            if (budget - bill < 0) {
                this.declareBankruptcy();
                if (budget - (int) (Math.round(Math.floor(indices * lastInvoice))) > 0) {
                    if (distributorIdLastInvoice != distributorId) {
                        Distributors.getInstance().getDistributors().get(distributorIdLastInvoice).
                                receivePayment(
                                        (int) (Math.round(Math.floor(indices * lastInvoice))));
                        lastInvoice = currInvoice;
                        distributorIdLastInvoice = distributorId;
                        currInvoice = 0;
                    }
                }
            } else {
                budget = budget - bill;
                if (!Distributors.getInstance().getDistributors().
                        get(distributorIdLastInvoice).isBankrupt()) {
                    Distributors.getInstance().getDistributors().get(distributorIdLastInvoice).
                            receivePayment((int) (Math.round(Math.floor(indices * lastInvoice))));
                }
                Distributors.getInstance().getDistributors().
                        get(distributorId).receivePayment(currInvoice);
                currInvoice = 0;
                lastInvoice = 0;
                distributorIdLastInvoice = 0;
            }
        } else {
            if (budget - currInvoice < 0) {
                lastInvoice = currInvoice;
                distributorIdLastInvoice = distributorId;
            } else {
                budget = budget - currInvoice;
                Distributors.getInstance().getDistributors().
                        get(distributorId).receivePayment(currInvoice);
            }
            currInvoice = 0;
        }
    }

    public int getDistributorProfit() {
        return distributorProfit;
    }

    public void setDistributorProfit(final int distributorProfit) {
        this.distributorProfit = distributorProfit;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(final int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(final int distributorId) {
        this.distributorId = distributorId;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return budget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.budget = initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getCurrInvoice() {
        return currInvoice;
    }

    public void setCurrInvoice(final int currInvoice) {
        this.currInvoice = currInvoice;
    }

    public int getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(final int lastInvoice) {
        this.lastInvoice = lastInvoice;
    }

    public boolean getHasContract() {
        return hasContract;
    }

    public void setHasContract(final boolean hasContract) {
        this.hasContract = hasContract;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id
                + ", \"isBankrupt\":" + isBankrupt
                + ", \"budget\":" + budget
                + '}';
    }
}
