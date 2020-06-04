package call.customthread;

public class SimpleThread extends Thread {

    private final String name;

    public SimpleThread(String name) {
        super(name);
        this.name = name;
    }

    public void run() {
        while(true) {
            // stopped by the control panel of the application
        }
    }
}
