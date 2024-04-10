package Model;

public class Task {
    private int arrivalTime;
    private int serviceTime;

    private int id;

    public Task(int arrivalTime, int serviceTime, int id) {
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime( int arrivalTime ) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime( int serviceTime ) {
        this.serviceTime = serviceTime;
    }
}
