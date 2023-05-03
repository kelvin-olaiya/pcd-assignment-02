public class Main {
    public static void main(String... args) throws InterruptedException {
        Thread
                .ofVirtual()
                .name("myVirtualThread-")
                .start(() -> System.out.println("ciao"));
        Thread.sleep(1000);
    }
}