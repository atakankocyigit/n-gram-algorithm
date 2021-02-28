import java.util.*;
import java.io.*;
public class Main {

	public static void CreateNgrams(int n, String corpus) {
		System.out.println("----------"+ n + "-Gram----------");
		long startTime = System.nanoTime();
		String [] corpusWords = corpus.split("\\s+");
        Map<String, Integer> nGramcounts = new LinkedHashMap<>();
		for (int i = 0; i < corpusWords.length - n + 1; i++) {
			String gram = "";
			if(corpusWords[i] != "") {
				//create n gram
				for(int j=0; j< n;j++) {
					gram+= (j != n-1) ? corpusWords[i+j] + " " : corpusWords[i+j];
				}
			}
			//hash-map adding
			if (nGramcounts.containsKey(gram))
				nGramcounts.put(gram, nGramcounts.get(gram)+1);
            else
            	nGramcounts.put(gram, 1);
		}

        Map<String, Integer> sortedNgrams = new LinkedHashMap<>();
        //sorting
        nGramcounts.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .forEach(entry -> { 
                        for(int i = 1; i <= entry.getValue(); i++) 
                        	sortedNgrams.put(entry.getKey(), nGramcounts.get(entry.getKey()));
                         });
        
        long end   = System.nanoTime();
        long totalTime = end - startTime;
        //print first 100 item
        for(int i=0; i< 100; i++) 
        	System.out.println(i+1 + " - " +sortedNgrams.keySet().toArray()[i] + " frequency: "+ sortedNgrams.get(sortedNgrams.keySet().toArray()[i]));

		System.out.println("Time: "  +totalTime * Math.pow(10, -9)+"\n\n\n");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String corpus ="";
		String file="";
		
		//reading files
		String [] filePath =  {"BİLİM İŞ BAŞINDA.txt", "BOZKIRDA.txt", "DEĞİŞİM.txt", "DENEMELER.txt", "UNUTULMUŞ DİYARLAR.txt"};		
		try {
			for(int i=0; i< filePath.length;i++) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
			                       new FileInputStream(filePath[i]), "UTF8"));		
				while((file = br.readLine()) != null)
					corpus+=" " + file;
				br.close();
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		//preprocessing
		corpus = corpus.toLowerCase().replaceAll("(?<=\\S)(?:(?<=\\p{Punct})|(?=\\p{Punct}))(?=\\S)", " ")
				.replace("? . .", "?..").replace("! . .", "!..").replace(". . .", "...")
				.replaceAll("[»,«,’,“,”,—]", " $0 ").replaceAll("\\s+", " ").replace(". .", "..");
		
		CreateNgrams(1, corpus);
		CreateNgrams(2, corpus);
		CreateNgrams(3, corpus);
	}
}
