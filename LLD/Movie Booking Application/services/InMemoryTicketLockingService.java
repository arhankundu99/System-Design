package services;
import providers.TicketLockingProvider;
import exceptions.*;

public class InMemoryTicketLockingService implements ITicketLockingService {
    ConcurrentHashMap<String, TicketLock> ticketLockMap;
    int lockTTL;

    private static InMemoryTicketLockingService instance;
    private InMemoryTicketLockingService() {
        ticketLockMap = new ConcurrentHashMap<>();
        this.lockTTL = 100;
    }

    public InMemoryTicketLockingService getInstance() {
        if (instance == null) {
            synchronized (InMemoryTicketLockingService.class) {
                if (instance == null) {
                    instance = new InMemoryTicketLockingService();
                }
            }
        }
        return instance;
    }

    synchronized public boolean lockTickets(List<String> ticketIds, String userId) {
        for (String ticketId: ticketIds) {
            if (isLockAcquired(ticketId, userId)) {
                return false;
            }
        }
        for (String ticketId: ticketIds) {
            lockTicket(ticketId, userId);
        }
    }
    public boolean unlockTickets(List<String> ticketIds, String userId) {
        for (String ticketId: ticketIds) {
            TicketLock ticketLock = lockMap.get(ticketId);
            if (ticketLock != null && ticketLock.get(ticketId).getUserId().equals(userId)) {
                ticketLock.remove(ticketId);
            }
        }
    }
    public boolean extendLockTTLIfNecessary(String ticketId, String userId, int thresholdTTL) {
        return true;
    }
    public boolean isLockAquired(String ticketId, String userId) {
        TicketLock ticketLock = ticketLockMap.get(ticketId);
        if (ticketLock == null) {
            return false;
        }
        
        // Check expiry
        int timeElapsed = (new Date()).getTime() - (ticketLock.getStartDate()).getTime();
        if (timeElapsed < ticketLock.getTTL()) {
            return false;
        }

        // Check which user has acquired the lock
        if (!ticketLock.getUserId().equals(userId)) {
            return false;
        }

        return true;
    }

    public boolean isLockAcquired(String ticketId) {
        TicketLock ticketLock = ticketLockMap.get(ticketId);
        if (ticketLock == null) {
            return false;
        }
        
        // Check expiry
        int timeElapsed = (new Date()).getTime() - (ticketLock.getStartDate()).getTime();
        if (timeElapsed < ticketLock.getTTL()) {
            return false;
        }

        return true;
    }

    private synchronized boolean lockTicket(String ticketId, String userId) {
        if (isLockAcquired(ticketId)) {
            throw new LockAlreadyAcquiredException("Lock is already acquired on the ticket id: " + ticketId);
        }

        TicketLock ticketLock = new TicketLock(userId, ttl);
        ticketLockMap.put(ticketId, ticketLock);
        return true;
    }

    public synchronized void setTTL(int lockTTL) {
        if (lockTTL <= 0) {
            throw new IllegalArgumentException("TTL must be greater than 0");
        }
        this.lockTTL = lockTTL;
    }
}