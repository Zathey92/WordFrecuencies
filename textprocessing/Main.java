package textprocessing;
import java.util.*;
import java.io.*;
import java.util.Random;

public class Main{
    public static void main(String[] args) {
        Random rnd = new Random(System.currentTimeMillis());
        FileNames fileNames= new FileNames();
        FileContents fileContents= new FileContents(30, 100 * 1024);
        WordFrequencies wordFrequencies= new WordFrequencies();
        //2 FileReader y 3 FileProcessor
        int nReaders=2,nProcessors=3;
        List<Thread> threads = new ArrayList<Thread>();
        for(int i = 0;i<nReaders;i++){
            threads.add(new Thread(new FileReader(fileNames,fileContents),"Reader"+i));
        }
        for(int i = 0;i<nProcessors;i++){
            threads.add(new Thread(new FileProcessor(fileContents,wordFrequencies),"Processor"+i));
        }
        Collections.shuffle(threads);
        for(int i=0;i<threads.size();i++){
            threads.get(i).start();
        }

        Tools.fileLocator(fileNames, "datos", rnd);
        fileNames.noMoreNames();
        //Esperar a que terminen los hilos creados
        try{
            for(int i=0;i<threads.size();i++){
                threads.get(i).join();
            }
        }catch(InterruptedException exc){}
        System.out.println("#########Todos los procesos Finalizados#########");
        System.out.println("####Mostrando Resultados<Palabra,Frecuencia>####");
        for( String palabra : Tools.wordSelector(wordFrequencies.getFrequencies())) {
            System.out.println(palabra);
        }
    }
}

class Tools {
    public static void fileLocator(FileNames fn, String dirname,Random rnd){
        File dir = new File(dirname);
        if ( ! dir.exists() ){
            return;
        }
        if ( dir.isDirectory() ) {
            String[] dirList = dir.list();
            for ( String name: dirList ) {
                if ( name.equals(".") || name.equals("..") ) {
                    continue;
                }
                fileLocator(fn, dir + File.separator + name,rnd);
            }
        } else {
            try{
                Thread.sleep(rnd.nextInt(100));
            }catch(InterruptedException exc) {};
            fn.addName(dir.getAbsolutePath());
            
            //System.out.println(dir.getAbsolutePath());
        }
    }
    public static class Order implements Comparator< Map.Entry<String,Integer> > {
        public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
            if ( o1.getValue().compareTo(o2.getValue()) == 0 ) {
                return o1.getKey().compareTo(o2.getKey());
            }
            return o2.getValue().compareTo(o1.getValue());
        }
    }
    
    public static List<String> wordSelector(Map<String,Integer> wf){
        Set<Map.Entry<String,Integer>> set=wf.entrySet();
        Set<Map.Entry<String,Integer>> orderSet= new TreeSet<Map.Entry<String,Integer>>(
            new Order());
        orderSet.addAll(set);
        List<String> l = new LinkedList<String>();
        int i=0;
        for ( Map.Entry<String,Integer> pair: orderSet){
            l.add(pair.getValue() + " " + pair.getKey() );
            if (++i >= 10 ) break;
        }
        return l;
    }

    public static String getContents(String fileName){
        String text = "";
		try {
		    FileInputStream fis = new FileInputStream( fileName );
	        BufferedReader br = new BufferedReader( new InputStreamReader(fis, "ISO8859-1") );
			String line;
			while (( line = br.readLine()) != null) {
				text += line + "\n";
			}
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
        return text;
    }
    
}
