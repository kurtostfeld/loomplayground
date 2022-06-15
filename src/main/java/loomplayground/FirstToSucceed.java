package loomplayground;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class FirstToSucceed {
    static String fastComputation() throws Exception {
        Thread.sleep(Duration.ofSeconds(1));
        return "got result after a short wait";
    }

    static String slowComputation() throws Exception {
        Thread.sleep(Duration.ofSeconds(6));
        return "got result after a longer wait";
    }

    static String failedComputation() throws Exception {
        throw new Exception("demo exception");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Try three functions to compute a String. We stop when we get the first success.
        // Note that this doesn't wait for the slow computation to complete and this ignores the failed computation.
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            scope.fork(FirstToSucceed::fastComputation);
            scope.fork(FirstToSucceed::slowComputation);
            scope.fork(FirstToSucceed::failedComputation);

            scope.join();
            String result = scope.result();
            System.out.printf("success. result=%s%n", result);
        }
        System.out.println("exiting.");
    }
}
