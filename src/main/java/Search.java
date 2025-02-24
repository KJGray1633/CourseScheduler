import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    private String query;
    private ArrayList<Course> searchResults;

    public List<Course> parseJSON() {
        ArrayList<Course> courses = new ArrayList<>();
       try{
           String content = new String(Files.readAllBytes(Paths.get("data_wolfe.json")));
           List<JSONObject> jsonObjects = extractJsonObjects(content);
       } catch(IOException e){
           System.out.println(e.getMessage());
       }
       return courses;
    }

    public static List<JSONObject> extractJsonObjects(String content){
        List<JSONObject> jsonObjects = new ArrayList<>();
        String jsonString = "";
        boolean inWord = false;
        int i = 0;

        while(i < content.length() - 2){
            if((content.charAt(i) == '{' && content.charAt(i+2) == 'r') || inWord){
                i++;
                jsonString += content.charAt(i);
                inWord = true;
            }
            if(content.charAt(i) == '}'){
                inWord = false;
                JSONObject jsonObject = new JSONObject(jsonString);
                jsonObjects.add(jsonObject);
                System.out.println(jsonString);
                jsonString = "";
                i++;
            }
        }

        // string contains


//        while (matcher.find()) {
//            String jsonString = matcher.group();
//            try {

//                System.out.println(jsonObject);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
        return jsonObjects;
    }

    public Search() {
    }
//    public Search(String query) {
//
//    }

    public ArrayList<Course> filter(Filter filter) {
        return null;
    }

    public String spellCheck(String s) {
        return null;
    }
}
