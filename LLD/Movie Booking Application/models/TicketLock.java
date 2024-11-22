package models;

class TicketLock {
    private String userId;
    private int ttl;
    private Date startDate;

    public TicketLock(String userId, int ttl) {
        this.userId = userId;
        this.ttl = ttl;
        startDate = new Date();
    }

    public String getUserId() {
        return this.userId;
    }

    public int getTTL() {
        return this.ttl;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setTTL(int ttl) {
        this.ttl = ttl;
    }
}