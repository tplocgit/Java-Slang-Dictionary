package hcmus.edu.java.project01;


public class Main {
    public static final String WM_PARAMETER = "`";
    public static final String MM_PARAMETER = "|";
    public static final String PATH = "./dict/slang.txt";

    public static void main(String[] args) {
        SlangDictionary myDict = new SlangDictionary();
        myDict.fromFile(PATH, WM_PARAMETER, MM_PARAMETER);
        myDict.showAll();
    }
}
