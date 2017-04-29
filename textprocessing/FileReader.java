package textprocessing;
public class FileReader implements Runnable{
    private FileContents fileContents;
    private FileNames fileNames;
    public FileReader(FileNames fn, FileContents fc){
        fileContents = fc;
        fileNames = fn;
    }
    
    public void run(){
        fileContents.registerWriter();
        String fn;
        try{
            while((fn = fileNames.getName()) != null){
               fileContents.addContents(Tools.getContents(fn));
               Thread.sleep(50);
            }
        }catch(InterruptedException exc) {};
        fileContents.unregisterWriter();
        System.out.println("-> Finalizando Word "+Thread.currentThread().getName());
    }
}
