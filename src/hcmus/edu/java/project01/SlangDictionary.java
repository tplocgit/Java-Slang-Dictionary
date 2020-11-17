package hcmus.edu.java.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SlangDictionary {
    TreeMap<String, LinkedList<String>> dict;

    SlangDictionary() {
        this.dict = new TreeMap<>();
    }

    public void addWord(String word, String mean) {
        if (!this.dict.containsKey(word)) {
            LinkedList<String> init_means_list = new LinkedList<>();
            init_means_list.add(mean);
            this.dict.put(word, init_means_list);
        } else {
            LinkedList<String> means_list = this.dict.get(word);
            means_list.add(mean);
            this.dict.put(word, means_list);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Word\t|\tMeans\n");

        for (Map.Entry<String, LinkedList<String>> entry : this.dict.entrySet()) {
            builder.append(entry.getKey()).append("\t\t|\t");

            Iterator<String> it = entry.getValue().iterator();
            while (it.hasNext()) {
                String mean = it.next();
                builder.append(mean);
                if (it.hasNext())
                    builder.append(", ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public void showAll() {
        System.out.println(this.toString());
    }

    public LinkedList<String> removeWord(String word) {
        return this.dict.remove(word);
    }

    public void fromFile(String path, String word_mean_parameter, String mean_mean_parameter) {
        try {
            File fin = new File(path);
            Scanner myReader = new Scanner(fin);

            if (myReader.hasNextLine())
                myReader.nextLine();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer tokens = new StringTokenizer(data, word_mean_parameter);
                String word = tokens.nextToken();
                String means = tokens.nextToken();
                tokens = new StringTokenizer(means, mean_mean_parameter);
                while(tokens.hasMoreTokens()) {
                    String mean = tokens.nextToken().trim();
                    this.addWord(word, mean);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
