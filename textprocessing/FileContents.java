package textprocessing;
import java.util.Queue;
import java.util.LinkedList;
public class FileContents {
    private Queue<String> fileContentsQueQue;
    private int registerCount = 0;
    private boolean closed = false;
    private int maxFiles,maxChars;
    public FileContents(int maxFiles, int maxChars) {
        this.fileContentsQueQue= new LinkedList<String>();
        this.maxFiles = maxFiles;
        this.maxChars = maxChars;
    }
    public void registerWriter() {
        registerCount++;
    }
    public void unregisterWriter() {
        registerCount--;
        if(registerCount==0) {
            closed = true;
            System.out.println("####Se acabó la lectura de Archivos####");
        }
    }
    public synchronized void addContents(String contents) {
        while(fileContentsQueQue.size()>=maxFiles||(contents.length()>maxChars&&!fileContentsQueQue.isEmpty())){
            try{
                wait();
            }catch(InterruptedException exc) { };
        }
        System.out.println("@"+Thread.currentThread().getName()+": Añadiendo Contenido");
        fileContentsQueQue.add(contents);
        notifyAll();

    }
    public synchronized String getContents() {
        while(fileContentsQueQue.isEmpty()){
            if(closed){notifyAll();return null;}
            try{
                wait();
            }catch(InterruptedException exc) { };
        }
        System.out.println("@"+Thread.currentThread().getName()+": Tomando Contenido");
        String content = fileContentsQueQue.remove();
        notifyAll();
        return content;

    }
}
