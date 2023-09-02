package thread;

public class ThreadEx {
    public static void main(String[] args) {
        MyRunnable thread = new MyRunnable();
        thread.run();
    }
}
class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("시작했습니다");
        for (int i = 0; i < 10; i++) {


        }
    }
    //스레드가 실행할 코드
}
