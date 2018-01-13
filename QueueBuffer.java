import java.util.LinkedList;
import java.util.Queue;

public class QueueBuffer<T> {
    private Queue<T> queue = new LinkedList<>();
    private int queueSize = 1;

    QueueBuffer(){};

    QueueBuffer(int queueSize){
        this.queueSize = queueSize;
    }

    public void add(T element){
        queue.add(element);
        if(queue.size()>queueSize){
            queue.poll();
        }
    }

    @Override
    public String toString(){
        StringBuilder str= new StringBuilder();
        for (T elem:
             queue) {
            str.append(elem);
        }
        return str.toString();
    }
}
