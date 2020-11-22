package hcmus.edu.java.project01;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SlangDictionary {
    private TreeMap<String, LinkedList<String>> dict;
    private StringBuilder menu;
    private int ntask;
    private final Scanner input;

    SlangDictionary() {
        this.dict = new TreeMap<>();
        this.menu = new StringBuilder();
        this.ntask = 0;
        this.input = new Scanner(System.in);
    }

    public LinkedList<String> getDefinitionsOf(String slang) {
        return this.dict.get(slang);
    }

    public LinkedList<String> getSlangsOf(String kword) {
        LinkedList<String> slang_list = new LinkedList<>();
        for(Map.Entry<String, LinkedList<String>> entry : this.dict.entrySet()) {
            for (String definition : entry.getValue()) {
                if (Arrays.asList(definition.split(" ")).contains(kword))
                    slang_list.push(entry.getKey());
            }
        }
        return slang_list;
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
        builder.append("Word: Means\n");

        for (Map.Entry<String, LinkedList<String>> entry : this.dict.entrySet()) {
            builder.append(entry.getKey()).append(": ");

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

    public void setMenu(String[] options) {
        this.menu.delete(0, this.menu.length());
        this.menu = new StringBuilder();
        for (int i = 0; i < options.length; ++i)
            this.menu.append(i + 1).append(". ").append(options[i]).append("\n");
        ntask = options.length;
    }

    public String getMenu() {
        return this.menu.toString();
    }

    public void showMenu() {
        System.out.println(this.getMenu());
    }

    public int getTask() {
        Scanner input = new Scanner(System.in);
        int op;
        System.out.print("Please enter your choose: ");
        while(true) {
            try {
                op = input.nextInt();
                if (op <= 0 || op > this.ntask)
                    continue;
                break;
            } catch (Exception ignored){
                System.out.print("Invalid input. Try again: ");
            }
        }

        return op;
    }

    public void doTask(int taskNum) {
        if (taskNum == 1) {
            System.out.print("Enter slang to find definition: ");
            String slang = this.input.nextLine();
            LinkedList<String> def = this.getDefinitionsOf(slang);
            System.out.println("Definition of " + slang + " is: " + def.toString());
        } else if (taskNum == 2) {
            System.out.print("Enter key words in definition to find slang words: ");
            String kword = this.input.nextLine();
            LinkedList<String> slangs = this.getSlangsOf(kword);
            System.out.println("Slang that definition contains " + kword + " is: " + slangs.toString());
        } else if (taskNum == this.ntask) {
            System.out.println("See you later.");
        } else {
            System.out.println("This function hasn't been implemented yet. " +
                    " Please try another function on our current menu.");
        }
    }

    public void showAllSlang() {
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
                //System.out.println(data);
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
