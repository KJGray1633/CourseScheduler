import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class Search {
    private String query;
    private ArrayList<Course> searchResults;

    public static List<Course> parseJSON() {
        ArrayList<Course> courses = new ArrayList<>();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("data_wolfe.json")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JSONObject json = new JSONObject(content);
        JSONArray classes = json.getJSONArray("classes");
        for (int i = 0; i < classes.length(); i++) {
            JSONObject c = classes.getJSONObject(i);
            String num = c.getString("location");
            System.out.println(num);
        }

        return null;
//       try{
//           String content = new String(Files.readAllBytes(Paths.get("data_wolfe.json")));
//           List<JSONObject> jsonObjects = extractJsonObjects(content);
//       } catch(IOException e){
//           System.out.println(e.getMessage());
//       }
//       return courses;
    }

    public static void main(String[] args) {
        parseJSON();
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
