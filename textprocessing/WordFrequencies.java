package textprocessing;
import java.util.Map;
import java.util.HashMap;

public class WordFrequencies {
    private Map<String, Integer> frequenciesMap;
    public WordFrequencies(){
        frequenciesMap = new HashMap<String, Integer>();
    }
    public synchronized void addFrequencies(Map<String,Integer> f){
        System.out.println("@"+Thread.currentThread().getName()+": Añadiendo Frecuencia");
        frequenciesMap.putAll(f);
        notifyAll();
    }
    /* No es necesario que sea sincronizado(Unico hilo en el momento de ejecución)
       Si se quisiera ir mostrando los resultados mientras avanza el procesamiento debería ser Sincronizado */
    public Map<String,Integer> getFrequencies(){
        //System.out.println("@"+Thread.currentThread().getName()+": Tomando Frecuencias");
        return frequenciesMap;
    }
}
