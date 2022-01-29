package com.example.intuit.clients;

import com.example.intuit.entities.subscription.QbAccountingSubscription;
import com.example.intuit.entities.subscription.QbPaymentsSubscription;
import com.example.intuit.entities.subscription.QbPayrollSubscription;
import com.example.intuit.entities.subscription.QbTimeSubscription;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class ValidationClientImpl implements ValidationClient {

    @Async
    @Override
    public CompletableFuture<Boolean> approve(QbTimeSubscription timeSubscription) {
        System.out.println("Making Rest Call For Approving Time Subscription asynchronously .....");
        try {
            Thread.sleep(4000);
            return CompletableFuture.completedFuture(true);
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    @Override
    public CompletableFuture<Boolean> approve(QbPayrollSubscription payrollSubscription) {
        System.out.println("Making Rest Call For Approving Payroll Subscription asynchronously .....");
        try {
            Thread.sleep(1000);
            return CompletableFuture.completedFuture(false);
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    @Override
    public CompletableFuture<Boolean> approve(QbPaymentsSubscription paymentsSubscription) {
        System.out.println("Making Rest Call For Approving Payment Subscription asynchronously .....");
        try {
            Thread.sleep(2000);
            return CompletableFuture.completedFuture(true);
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    @Override
    public CompletableFuture<Boolean> approve(QbAccountingSubscription accountingSubscription) {
        System.out.println("Making Rest Call For Approving Accounting Subscription asynchronously .....");
        try {
            Thread.sleep(3000);
            return CompletableFuture.completedFuture(true);
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(false);
        }
    }
}
