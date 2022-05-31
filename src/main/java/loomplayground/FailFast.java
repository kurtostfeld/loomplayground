package loomplayground;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FailFast {
    static String fastComputation(boolean fail) throws Exception {
        if (fail) {
            throw new Exception("demo exception");
        } else {
            Thread.sleep(Duration.ofSeconds(1));
            return "got result after a short wait";
        }
    }

    static String slowComputation() throws Exception {
        Thread.sleep(Duration.ofSeconds(6));
        return "got result after a longer wait";
    }

    static void tryIt(boolean simulateFailure) throws InterruptedException {
        System.out.printf("trying with simulateFailure=%s%n", simulateFailure);

        // If we simulate failure, this scope will fail and shutdown when the first task fails. This will interrupt and not wait for the slow task.
        // If there is no failure, then this will wait for both tasks to complete.
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> fastF = scope.fork(() -> fastComputation(simulateFailure));
            Future<String> slowF = scope.fork(FailFast::slowComputation);

            scope.join();
            var exceptionOpt = scope.exception();
            if (exceptionOpt.isPresent()) {
                System.out.printf("got exception: %s%n", exceptionOpt.get().getMessage());
            } else {
                System.out.printf("success. fast=%s. slow=%s.%n", fastF.resultNow(), slowF.resultNow());
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        tryIt(true);
        tryIt(false);
        System.out.println("exiting.");
    }

}
