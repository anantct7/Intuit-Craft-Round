package com.example.intuit.clients;

import com.example.intuit.entities.subscription.QbAccountingSubscription;
import com.example.intuit.entities.subscription.QbPaymentsSubscription;
import com.example.intuit.entities.subscription.QbPayrollSubscription;
import com.example.intuit.entities.subscription.QbTimeSubscription;

import java.util.concurrent.CompletableFuture;

public interface ValidationClient {
    CompletableFuture<Boolean> approve(QbTimeSubscription timeSubscription);
    CompletableFuture<Boolean> approve(QbPayrollSubscription payrollSubscription);
    CompletableFuture<Boolean> approve(QbPaymentsSubscription paymentsSubscription);
    CompletableFuture<Boolean> approve(QbAccountingSubscription accountingSubscription);
}
