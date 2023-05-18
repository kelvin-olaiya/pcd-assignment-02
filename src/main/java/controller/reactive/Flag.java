package controller.reactive;

public class Flag {
    private boolean flag = false;

    synchronized public void set() {
        flag = true;
    }

    synchronized public void unset() {
        flag = false;
    }

    synchronized public boolean isSet() {
        return flag;
    }
}
