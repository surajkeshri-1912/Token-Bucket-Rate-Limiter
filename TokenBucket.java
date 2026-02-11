import java.util.concurrent.locks.ReentrantLock;

/*
 Token Bucket implementation.
 Controls request rate using:
 - max capacity
 - refill rate per second
 Thread-safe via ReentrantLock.
*/
public class TokenBucket {

    private final int capacity;
    private final double refillRatePerSecond;

    // Current available tokens
    private double tokens;

    // Last refill timestamp (nanoseconds)
    private long lastRefillTime;

    // Lock protects critical section
    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucket(int capacity, double refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;

        // Start bucket full → allow initial burst
        this.tokens = capacity;

        this.lastRefillTime = System.nanoTime();
    }

    /*
     Thread-safe request check.
     Refill → check → consume token.
    */
    public boolean allowRequest() {
        lock.lock();
        try {
            // Refill tokens based on elapsed time
            refillTokens();

            // Allow if token available
            if (tokens >= 1) {
                tokens -= 1;
                return true;
            }

            // Otherwise reject
            return false;

        } finally {
            lock.unlock();
        }
    }

    /*
     Lazy refill strategy.
     Tokens added only when request arrives.
     No background scheduler needed.
    */
    private void refillTokens() {

        long now = System.nanoTime();

        // Time passed since last refill
        double secondsPassed =
                (now - lastRefillTime) / 1_000_000_000.0;

        // Tokens to add
        double tokensToAdd = secondsPassed * refillRatePerSecond;

        if (tokensToAdd > 0) {

            // Cap at max capacity
            tokens = Math.min(capacity, tokens + tokensToAdd);

            // Update refill timestamp
            lastRefillTime = now;
        }
    }
}
