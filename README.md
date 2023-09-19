# SolidCorda
Example of R3 Corda Blockchain 

Example of CorDapp that defines a state with a debit amount and a corresponding contract :

a) CreateState.java : Define a custom state class, which represents the state of the ledger. In this case, it represents a debit state with an amount.
b) CreateContract.java : Define a contract class that implements the Contract interface. This contract enforces the rules governing the DebitState transactions.
c) Flow.java : allows to issue a DebitState and update its amount.
