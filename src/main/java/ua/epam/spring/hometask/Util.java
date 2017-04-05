package ua.epam.spring.hometask;

public class Util {

    public static void pause() {
        pause(1000);
    }

    public static void pause(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
