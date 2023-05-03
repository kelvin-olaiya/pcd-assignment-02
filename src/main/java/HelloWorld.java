public class HelloWorld {
    public static void main(String... args) throws InterruptedException {
//        System.out.println("Hello World!");
        Thread
                .ofVirtual()
                .name("myVirtualThread-")
                .start(() -> System.out.println("ciao"));
        Thread.sleep(1000);
    }
}