import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

@InitiatingFlow
@StartableByRPC
public class DebitFlow extends FlowLogic<SignedTransaction> {

    private final int newAmount;
    private final UniqueIdentifier linearId;

    public DebitFlow(int newAmount, UniqueIdentifier linearId) {
        this.newAmount = newAmount;
        this.linearId = linearId;
    }

    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {
        // Retrieve the notary for transaction validation
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        // Fetch the current state from the ledger using its linear ID
        StateAndRef<DebitState> inputStateAndRef = getServiceHub().getVaultService()
                .queryBy(DebitState.class).getStates().stream()
                .filter(state -> state.getState().getData().getLinearId().equals(linearId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("State with linear ID not found."));

        // Create a new debit state with the updated amount
        DebitState oldState = inputStateAndRef.getState().getData();
        DebitState updatedState = new DebitState(newAmount, oldState.getLender(), oldState.getBorrower());

        // Create a transaction builder
        TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addInputState(inputStateAndRef)
                .addOutputState(updatedState, DebitContract.DEPOSIT_CONTRACT_ID)
                .addCommand(new DebitContract.Commands.Update(), getOurIdentity().getOwningKey());

        // Verify the transaction
        txBuilder.verify(getServiceHub());

        // Sign the transaction
        SignedTransaction signedTx = getServiceHub().signInitialTransaction(txBuilder);

        // Finalize and record the transaction in the ledger
        subFlow(new FinalityFlow(signedTx));

        return signedTx;
    }
}
