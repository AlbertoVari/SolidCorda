# SolidCorda
Example of R3 Corda v.4 Blockchain 

Example of CorDapp that defines a state with a debit amount and a corresponding contract 

![image](https://github.com/AlbertoVari/SolidCorda/assets/22276126/721c897f-e5ad-45b1-b801-d36951c76ca5)



a) CreateState.java : Define a custom state class, which represents the state of the ledger. In this case, it represents a debit state with an amount.

b) CreateContract.java : Define a contract class that implements the Contract interface. This contract enforces the rules governing the DebitState transactions.

c) Flow.java : allows to issue a DebitState and update its amount.
