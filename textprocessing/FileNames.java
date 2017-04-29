package textprocessing;
import java.util.Queue;
import java.util.LinkedList;
public class FileNames {
    private boolean noMore;
    private  Queue<String> fileNameQueQue;
    
    public FileNames(){
        this.fileNameQueQue= new LinkedList<String>();
        noMore = false;
    }
    
    public synchronized void addName(String fileName) {
        if(!noMore){
            System.out.println("@"+Thread.currentThread().getName()+": Añadiendo nombre");
            this.fileNameQueQue.add(fileName);
        }
        notifyAll();
    }
    
    public synchronized String getName() {
         while(fileNameQueQue.isEmpty()){
            if(noMore) {notifyAll();return null;}
            try{
                wait();
            }catch(InterruptedException exc) { };
        }
        System.out.println("@"+Thread.currentThread().getName()+": Tomando nombre");
        String ob = fileNameQueQue.remove();
        notifyAll();
        return ob;
    }
    
    public void noMoreNames() {
        this.noMore = true;
    }
}