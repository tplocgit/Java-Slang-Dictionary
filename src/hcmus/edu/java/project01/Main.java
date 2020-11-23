package hcmus.edu.java.project01;


import java.util.Arrays;
import java.util.List;
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
            System.out.print("Press enter key to continue or finish this function.\n");
            input.nextLine();
        }
        while (op != OPTIONS.length);
        System.out.println("Do you want to overwrite origin file with current dictionary? (YES, Y or NO, N)");
        System.out.print("Your will (uppercase or lowercase accepted): ");
        String[] init = {"YES", "Y", "NO", "N"};
        List<String> acceptedList = Arrays.asList(init);
        String confirm;
        do {
            confirm = input.nextLine().toUpperCase();
            if (!acceptedList.contains(confirm)) {
                System.out.print("Invalid input. Please try again with " + acceptedList.toString() + ": ");
            }
        } while (!acceptedList.contains(confirm));
        if (confirm.equals("YES") || confirm.equals("Y")){
            System.out.print("Your answer is YES. ACCEPTED. Doing last task ...");
            myDict.exportFile(PATH, WM_PARAMETER, MM_PARAMETER);
        }
        else {
            System.out.println("Your answer is NO. ACCEPTED.");
        }
        System.out.println("All task done. See you later!!!");
    }
}
