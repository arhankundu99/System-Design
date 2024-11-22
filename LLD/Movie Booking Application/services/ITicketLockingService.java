package providers;

public interface ITicketLockingService {
    public boolean lockTickets(List<String> ticketIds, String userId);
    public boolean unlockTickets(List<String> ticketIds, String userId);
    public boolean extendLockTTL(List<String> ticketIds, String userId);
    public boolean isLockAcquired(String ticketId, String userId);
    public boolean isLockAcquired(String ticketId);
}