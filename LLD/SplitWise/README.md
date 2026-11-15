# Splitwise

Build the core engine for a bill splitting app (like splitwise). Friends add shared expenses - a donner, a trip, groceries - and the app tracks who owes whom how much and lets them settle up

Implement the domain engine only.

## High level requirements

1. A new user can register.
2. Any registered user can add expense and the list of users sharing the expense. Adding an expense must immediately update everyone's balances.
3. At any point, show each other's net balance and the pairwise who owes whom for a set of users.
4. User can pay back another user directly.