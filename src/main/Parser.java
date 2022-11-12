package main;
import org.json.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Parser {
    public JSONObject obj;
    public Parser() throws IOException {

        Reader reader = new FileReader("parameters.json");


        int intValueOfChar;
        String str = "";
        while ((intValueOfChar = reader.read()) != -1) {
            str += (char) intValueOfChar;
        }
        reader.close();

        obj = new JSONObject(str);
    }
}
