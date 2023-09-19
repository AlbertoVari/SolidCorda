import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

// Define your state class
public class DebitState implements ContractState {
    private final int amount; // Debit amount
    private final Party lender; // Party who lends the money
    private final Party borrower; // Party who owes the money

    public DebitState(int amount, Party lender, Party borrower) {
        this.amount = amount;
        this.lender = lender;
        this.borrower = borrower;
    }

    public int getAmount() {
        return amount;
    }

    public Party getLender() {
        return lender;
    }

    public Party getBorrower() {
        return borrower;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(lender, borrower);
    }
}
