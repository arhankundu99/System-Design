package services;

import java.util.HashMap;
import java.util.Map;

import models.Group;
import models.Payment;
import models.User;
import strategies.IBalancingStrategy;

public class PaymentService {
    private final IBalancingStrategy balancingStrategy;
    private final Map<String, Payment> paymentsMap;

    public PaymentService(IBalancingStrategy balancingStrategy) {
        this.balancingStrategy = balancingStrategy;
        this.paymentsMap = new HashMap<>();
    }

    public void createPayment(User fromUser, User toUser, double amount, Group group) {
        Payment payment = new Payment(fromUser, toUser, amount, group);
        paymentsMap.put(payment.getId(), payment);
        balancingStrategy.balancePayments(payment.clone());
    }

    public Payment getPaymentById(String paymentId) {
        return paymentsMap.get(paymentId);
    }
}
