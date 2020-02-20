package mage.util;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class KeywordHolder {

    private static ArrayList<String> keywords_list = new ArrayList<>();
    private static String keywords_filepath = "C:\\Users\\Armin\\IdeaProjects\\mage\\Mage\\src\\main\\java\\mage\\util\\keywords_simple.txt";
    private static File keywords_file = new File(keywords_filepath);

    public static void ReadKeywords() {
        try {
            Scanner reader = new Scanner(keywords_file);
            while (reader.hasNextLine()) {
                String keyword = reader.nextLine();
                keywords_list.add(keyword.toLowerCase());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getKeywords_list() {
        return keywords_list;
    }
}
