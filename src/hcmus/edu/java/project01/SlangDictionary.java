package hcmus.edu.java.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SlangDictionary {
    private TreeMap<String, LinkedList<String>> dict;
    private String[] taskList;
    private StringBuilder menu;
    private final Scanner input;
    private Stack<String> searchLog;
    private Stack<String> changeLog;
    public static String UNKNOWN_WORD_MESG = "Unknown slang word";
    public static String UNKNOWN_TASK_MESG = "Unknown task";
    public static String UNKNOWN_DEFI_MESG = "Unknown key word";
    public static int EDIT_TASKNUM = 5;
    public static int ADD_TASKNUM = 4;
    public static int RM_TASKNUM = 6;


    SlangDictionary() {
        this.dict = new TreeMap<>();
        this.menu = new StringBuilder();
        this.input = new Scanner(System.in);
        this.searchLog = new Stack<>();
        this.changeLog = new Stack<>();
    }

    private void pushToSearchLog(StringBuilder builder) {
        this.searchLog.push(builder.toString());
    }

    private void pushToChangeLog(StringBuilder builder) {
        this.changeLog.push(builder.toString());
    }

    public String strOfTask(int taskNum) {
        if (taskNum <= 0 || taskNum > this.taskList.length)
            return SlangDictionary.UNKNOWN_TASK_MESG;
        return this.taskList[taskNum - 1];
    }

    public LinkedList<String> getDefinitionsOf(String slang) {
        LinkedList<String> def = this.dict.get(slang);
        if (def == null) {
            def = new LinkedList<>();
            def.add(SlangDictionary.UNKNOWN_WORD_MESG);
        }
        return def;
    }

    public LinkedList<String> getSlangWordsOf(String keyword) {
        LinkedList<String> slang_list = new LinkedList<>();
        for (Map.Entry<String, LinkedList<String>> entry : this.dict.entrySet()) {
            for (String definition : entry.getValue()) {
                if (Arrays.asList(definition.toLowerCase().split(" ")).contains(keyword.toLowerCase()))
                    slang_list.push(entry.getKey());
            }
        }
        if (slang_list.size() <= 0)
            slang_list.add(SlangDictionary.UNKNOWN_DEFI_MESG);
        return slang_list;
    }

    public String getSearchHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append("History from present to past:\n");
        int act_counter = 0;
        for (String activity : this.searchLog) {
            ++act_counter;
            String[] tokens = activity.split("`");
            int task = Integer.parseInt(tokens[0]);
            String str_task = this.strOfTask(task);
            builder.append("Task #").append(act_counter).append(" ").append(str_task);
            if (task == 1 || task == 2) {
                builder.append(": Search \"").append(tokens[1]).append("\", get \"").append(tokens[2]).append("\"\n");
            }
        }
        return builder.toString();
    }

    public String getChangeHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append("History from present to past:\n");
        int act_counter = 0;
        for (String activity : this.changeLog) {
            ++act_counter;
            String[] tokens = activity.split("`");
            int task = Integer.parseInt(tokens[0]);
            String str_task = this.strOfTask(task);
            builder.append("Task #").append(act_counter).append(" ").append(str_task);
            if (task == SlangDictionary.ADD_TASKNUM) {
                builder.append(": Add \"").append(tokens[1]).append("\", with definition \"").append(tokens[2]).append("\"\n");
            } else if (task == SlangDictionary.EDIT_TASKNUM) {
                builder.append(": Edit definitions of \"").append(tokens[1]).append("\", from \"").append(tokens[2]).append("\" to \"");
                builder.append(tokens[3]).append("\"\n");
            } else {
                builder.append(": remove \"").append(tokens[1]).append("\"\n");
            }
        }
        return builder.toString();
    }

    public void showSearchHistory() {
        System.out.println(this.getSearchHistory());
    }

    public void showChangeHistory() {
        System.out.println(this.getChangeHistory());
    }

    public boolean hasDefinitionsOf(String slang) {
        return !this.getDefinitionsOf(slang).contains(SlangDictionary.UNKNOWN_WORD_MESG);
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

    public void putWord(String slang, LinkedList<String> defis) {
        this.dict.put(slang, defis);
    }

    public String randomKey() {
        Set<String> k_set = this.dict.keySet();
        List<String> k_list = new ArrayList<>(k_set);
        int randInt = new Random().nextInt(k_list.size());
        return k_list.get(randInt);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Word: Definitions\n");

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
        this.taskList = options;
        this.menu.delete(0, this.menu.length());
        this.menu = new StringBuilder();
        for (int i = 0; i < options.length; ++i)
            this.menu.append(i + 1).append(". ").append(options[i]).append("\n");
    }

    public String getMenu() {
        return this.menu.toString();
    }

    public void showMenu() {
        System.out.println(this.getMenu());
    }

    private static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    private boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public int getTask() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your choose: ");
        String op;
        do {
            op = input.nextLine();
            if (!isInteger(op))
                System.out.print("Please enter valid integer. Try again: ");
        } while (!isInteger(op));

        return Integer.parseInt(op);
    }

    private static LinkedList<String> stringToLinkedList(String s) {
        s = s.substring(1, s.length() - 1);
        StringTokenizer tokens = new StringTokenizer(s, ",");
        LinkedList<String> defis = new LinkedList<>();
        while (tokens.hasMoreTokens()) {
            defis.push(tokens.nextToken().trim());
        }
        return defis;
    }

    public void doTask(int taskNum) {
        StringBuilder builder = new StringBuilder();
        if (taskNum == 1) {
            System.out.print("Enter slang to find definition: ");
            String slang = this.input.nextLine();
            LinkedList<String> def = this.getDefinitionsOf(slang);
            System.out.println("Definition of " + slang + " is: " + def.toString());
            builder.append(taskNum).append("`").append(slang).append("`").append(def.toString());
            this.pushToSearchLog(builder);
        } else if (taskNum == 2) {
            System.out.print("Enter key words in definition to find slang words: ");
            String keyword = this.input.nextLine();
            LinkedList<String> slang = this.getSlangWordsOf(keyword);
            System.out.println("Slang that definition contains " + keyword + " is: " + slang.toString());
            builder.append(taskNum).append("`").append(keyword).append("`").append(slang.toString());
            this.pushToSearchLog(builder);
        } else if (taskNum == 3) {
            this.showSearchHistory();
        } else if (taskNum == 4) {
            System.out.print("Enter new slang word: ");
            String slang = this.input.nextLine();
            System.out.print("Enter definition: ");
            String def = this.input.nextLine();
            LinkedList<String> search_defs = this.getDefinitionsOf(slang);
            if (search_defs.contains(SlangDictionary.UNKNOWN_WORD_MESG)) {
                this.addWord(slang, def);
                builder.append(taskNum).append("`").append(slang).append("`").append(def);
            } else {
                System.out.print("Your slang word already has been defined in dictionary. Please confirm by enter:\n");
                String[] init = {"OVERWRITE", "OW", "ADDNEW", "NEW"};
                List<String> acceptedAns = Arrays.asList(init);
                System.out.println("1. 'OVERWRITE' (or 'OW') to overwrite");
                System.out.println("2. 'ADDNEW' (or 'NEW') to add new definition");
                System.out.println("Enter your will (lowercase or uppercase are accepted): ");
                String confirm;
                do {
                    confirm = this.input.nextLine().toUpperCase();
                    if (!acceptedAns.contains(confirm)) {
                        System.out.print("Invalid input. Please try again with " + acceptedAns.toString() + ": ");
                    }
                } while (!acceptedAns.contains(confirm));
                if (confirm.equals("OVERWRITE") || confirm.equals("OW")) {
                    System.out.println("Your answer is OVERWRITE. Doing task ...");
                    LinkedList<String> new_def = new LinkedList<>();
                    new_def.add(def);
                    this.dict.put(slang, new_def);
                } else {
                    System.out.println("Your answer is ADDNEW. Doing task ...");
                    this.addWord(slang, def);
                }
                builder.append(SlangDictionary.EDIT_TASKNUM);
                builder.append("`").append(slang).append("`").append(search_defs).append("`").append(def);
            }
            this.changeLog.push(builder.toString());
        } else if (taskNum == 5) {
            System.out.print("Enter slang word to get definitions: ");
            String slang = this.input.nextLine();
            LinkedList<String> defis = this.getDefinitionsOf(slang);
            if (defis.contains(SlangDictionary.UNKNOWN_WORD_MESG)) {
                System.out.println("Your entered word hasn't been defined yet. Wanna add new? (YES, Y/NO, N)");
                System.out.println("Your will (both lowercase or uppercase accepted): ");
                String[] init = {"YES", "Y", "NO", "N"};
                List<String> acceptedList = Arrays.asList(init);
                String confirm;
                do {
                    confirm = input.nextLine().toUpperCase();
                    if (!acceptedList.contains(confirm)) {
                        System.out.print("Invalid input. Please try again with " + acceptedList.toString() + ": ");
                    }
                } while (!acceptedList.contains(confirm));
                if (confirm.equals("YES") || confirm.equals("Y")) {
                    System.out.println("Your answer is YES. ACCEPTED. Doing last task ...");
                    System.out.print("Enter definition: ");
                    String def = this.input.nextLine();
                    addWord(slang, def);
                    builder.append(SlangDictionary.ADD_TASKNUM).append("`").append(slang).append("`").append(def);
                    this.changeLog.push(builder.toString());
                } else {
                    System.out.print("Your answer is NO. ACCEPTED.");
                }
            } else {
                System.out.println("Current definitions: " + defis.toString());
                System.out.print("Enter number of definition of you will enter: ");
                String num;
                do {
                    num = input.nextLine();
                    if (!isInteger(num))
                        System.out.print("Please enter valid integer. Try again: ");
                } while (!isInteger(num));

                int ex_num = Integer.parseInt(num);
                System.out.println("Enter your new definitions (each definition in one line): ");
                LinkedList<String> new_defis = new LinkedList<>();
                for (int i = 0; i < ex_num; ++i) {
                    String inputLine = this.input.nextLine();
                    new_defis.add(inputLine);
                }
                this.putWord(slang, new_defis);
                builder.append(taskNum).append("`").append(slang).append("`").append(defis).append("`").append(new_defis);
                this.changeLog.push(builder.toString());
            }
        } else if (taskNum == 6) {
            System.out.print("Enter slang to remove: ");
            String slang = this.input.nextLine();
            boolean check = this.hasDefinitionsOf(slang);
            if (check) {
                LinkedList<String> defis = this.getDefinitionsOf(slang);
                System.out.println("The definitions of " + slang + " is " + defis.toString());
                System.out.println("Are you sure to remove it? (YES, Y or NO, N)");
                System.out.print("Your will: ");
                String[] init = {"YES", "Y", "NO", "N"};
                List<String> acceptedList = Arrays.asList(init);
                String confirm;
                do {
                    confirm = input.nextLine().toUpperCase();
                    if (!acceptedList.contains(confirm)) {
                        System.out.print("Invalid input. Please try again with " + acceptedList.toString() + ": ");
                    }
                } while (!acceptedList.contains(confirm));
                if (confirm.equals("YES") || confirm.equals("Y")) {
                    System.out.println("Your answer is YES. ACCEPTED. Doing last task ...");
                    this.removeWord(slang);
                    builder.append(taskNum).append("`").append(slang).append("`").append(defis);
                    this.changeLog.push(builder.toString());
                } else {
                    System.out.print("Your answer is NO. ACCEPTED.");
                }
            } else {
                System.out.println("Your entered slang hasn't been defined yed.");
            }
        } else if (taskNum == 7) {
            System.out.println("Are you sure to reset to origin dictionary? (YES, Y or NO, N)");
            System.out.print("Your will: ");
            String[] init = {"YES", "Y", "NO", "N"};
            List<String> acceptedList = Arrays.asList(init);
            String confirm;
            do {
                confirm = input.nextLine().toUpperCase();
                if (!acceptedList.contains(confirm)) {
                    System.out.print("Invalid input. Please try again with " + acceptedList.toString() + ": ");
                }
            } while (!acceptedList.contains(confirm));
            if (confirm.equals("YES") || confirm.equals("Y")) {
                System.out.println("Your answer is YES. ACCEPTED. Doing last task ...");
                while (!this.changeLog.empty()) {
                    String activity = this.changeLog.pop();
                    StringTokenizer tokens = new StringTokenizer(activity, "`");
                    int task = Integer.parseInt(tokens.nextToken());
                    String slang = tokens.nextToken();
                    if (task == SlangDictionary.ADD_TASKNUM) {
                        this.removeWord(slang);
                    } else {
                        String str_defis = tokens.nextToken();
                        LinkedList<String> defis = SlangDictionary.stringToLinkedList(str_defis);
                        this.putWord(slang, defis);
                    }
                }
            } else {
                System.out.print("Your answer is NO. ACCEPTED.");
            }
        } else if (taskNum == 8) {
            String randKey = this.randomKey();
            System.out.println("One of " + this.strOfTask(8) + " is " + randKey +
                    ": " + this.getDefinitionsOf(randKey));
        } else if (taskNum == 9) {
            List<String> randKeys = new ArrayList<>();
            TreeMap<String, Boolean> quizMap = new TreeMap<>();
            for (int i = 0; i < 4; ++i) {
                randKeys.add(this.randomKey());
                quizMap.put(this.getDefinitionsOf(randKeys.get(i)).toString(), false);
            }
            String rightAns = this.getDefinitionsOf(randKeys.get(0)).toString();
            quizMap.put(rightAns, true);
            System.out.println("Today quiz: What is definitions of " + randKeys.get(0) + ":");
            Collections.shuffle(randKeys);
            System.out.println("1. " + getDefinitionsOf(randKeys.get(0)));
            System.out.println("2. " + getDefinitionsOf(randKeys.get(1)));
            System.out.println("3. " + getDefinitionsOf(randKeys.get(2)));
            System.out.println("4. " + getDefinitionsOf(randKeys.get(3)));
            System.out.print("Your answer (1-4): ");
            String op;
            boolean check;
            do {
                op = input.nextLine();
                check = !this.isInteger(op) || Integer.parseInt(op) < 1 || Integer.parseInt(op) > 4;
                if (check)
                    System.out.print("Please enter valid integer. Try again: ");
            } while (check);
            int ans = Integer.parseInt(op);
            Boolean checkAns = quizMap.get(this.getDefinitionsOf(randKeys.get(ans - 1)).toString());
            System.out.println("Your ans is " + (checkAns ? "right" : "wrong") + ". The right answer is " + rightAns);

        } else if (taskNum == this.taskList.length) {
            builder.append(taskNum);
            this.pushToChangeLog(builder);
        } else if (taskNum == 404) {
            this.showChangeHistory();
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
                while (tokens.hasMoreTokens()) {
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

    public void exportFile(String path, String word_mean_parameter, String mean_mean_parameter) {
        File fout = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(fout);
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, LinkedList<String>> entry : this.dict.entrySet()) {
                builder.append(entry.getKey()).append(word_mean_parameter);
                Iterator<String> it = entry.getValue().iterator();
                while (it.hasNext()) {
                    builder.append(it.next());
                    if (it.hasNext())
                        builder.append(mean_mean_parameter);
                }
                builder.append("\n");
            }
            fw.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
