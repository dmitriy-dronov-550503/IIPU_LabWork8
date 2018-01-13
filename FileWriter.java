import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWriter {

    private String fileName;
    private PrintWriter writer;

    public FileWriter(String fileName){
        this.fileName = fileName;
        open();
    }

    public void write(String message){
        writer.println(message);
        writer.flush();
    }

    private void open(){
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void flush(){
        writer.close();
        open();
    }

}
