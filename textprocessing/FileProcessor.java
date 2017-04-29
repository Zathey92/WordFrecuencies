package textprocessing;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FileProcessor implements Runnable{
    private FileContents fileContents;
    private WordFrequencies wordFrequencies;
    private Pattern pattern;
    public FileProcessor(FileContents fc, WordFrequencies wf){
        pattern = Pattern.compile("[a-zA-Z0-9]*");
        fileContents = fc;
        wordFrequencies = wf;
    }
    private Map<String, Integer> getWordFrequency(String content){
        String[] arr = content.split(" ");
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String ss : arr){
            Matcher matcher = pattern.matcher(ss);
            if(matcher.matches()){
                Integer n = map.get(ss);
                n = (n == null) ? 1 : ++n;
                map.put(ss, n);
            }
        }
        return map;
    }
    public void run(){
        try{
            String content;
            while((content=fileContents.getContents())!=null){
                Map<String, Integer> map = getWordFrequency(content);
                wordFrequencies.addFrequencies(map);
                Thread.sleep(50);
            }
        }catch(InterruptedException exc){}
        System.out.println("-> Finalizando Word "+Thread.currentThread().getName());
    }
}
