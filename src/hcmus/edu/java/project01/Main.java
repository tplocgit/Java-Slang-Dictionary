package hcmus.edu.java.project01;


import java.util.Scanner;

public class Main {
    public static final String WM_PARAMETER = "`";
    public static final String MM_PARAMETER = "|";
    public static final String PATH = "./dict/slang.txt";
    public static final String[] OPTIONS = {"Search definition", "Search slang", "History", "Add new slang",
                                            "Edit slang", "Remove slang", "Reset to origin dictionary",
                                            "On this day slang", "Quiz: Find right definition",
                                            "Quiz: find right slang", "Exit"};
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        SlangDictionary myDict = new SlangDictionary();
        myDict.fromFile(PATH, WM_PARAMETER, MM_PARAMETER);
        myDict.setMenu(OPTIONS);
        int op;
        do {
            myDict.showMenu();
            op = myDict.getTask();
            myDict.doTask(op);
            System.out.print("Press enter key to continue or finish this function.");
            input.nextLine();
        }
        while (op != OPTIONS.length);
        //myDict.exportFile(PATH, WM_PARAMETER, MM_PARAMETER);
    }
}
