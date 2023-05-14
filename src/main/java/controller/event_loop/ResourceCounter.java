package controller.event_loop;

public class ResourceCounter {

    private int counter = 0;

    public void inc() {
        this.counter++;
    }

    public void dec() {
        this.counter--;
    }

    public boolean noMoreResources() {
        return this.counter == 0;
    }
}
