import java.util.List;

import models.Expense;
import models.Group;
import models.User;
import services.ExpenseService;
import services.GroupService;
import services.PaymentService;
import services.SplitWiseService;
import services.UserService;
import strategies.DefaultBalancingStrategy;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User alice = new User("Alice", "alice@example.com");
        User bob = new User("Bob", "bob@example.com");

        userService.registerUser(alice.getName(), alice.getEmail());
        userService.registerUser(bob.getName(), bob.getEmail());

        GroupService groupService = new GroupService();
        Group tripGroup = groupService.createGroup("Weekend Trip", alice);
        groupService.addMemberToGroup(tripGroup.getId(), bob);

        DefaultBalancingStrategy balancingStrategy = new DefaultBalancingStrategy();
        ExpenseService expenseService = new ExpenseService(balancingStrategy);
        PaymentService paymentService = new PaymentService(balancingStrategy);
        SplitWiseService splitWiseService = new SplitWiseService(expenseService, paymentService, balancingStrategy);

        Expense dinner = splitWiseService.createExpense("Dinner", 600.0, alice, tripGroup, List.of(alice, bob));
        System.out.println("Dinner expense created: " + dinner.getDescription());
        System.out.println("Alice pending payments: " + splitWiseService.getPendingPaymentsForUser(alice));

        System.out.println("Alice pending payments in trip group: "
                + splitWiseService.getPendingPaymentsForUserInGroup(alice, tripGroup));

        splitWiseService.createPayment(bob, alice, 200, tripGroup);
        System.out.println("Payment created from Bob to Alice for 200 in trip group.");

        System.out.println("Alice pending payments: " + splitWiseService.getPendingPaymentsForUser(alice));
        System.out.println("Bob pending payments: " + splitWiseService.getPendingPaymentsForUser(bob));

    }
}
