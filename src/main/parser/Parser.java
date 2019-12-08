package main.parser;
import org.json.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Parser {
    public JSONObject obj;
    public Parser() throws IOException {

        Reader reader = new FileReader("C:\\Users\\Pawel\\Documents\\Java\\PObiektowe1\\src\\main\\parameters.json");


        int intValueOfChar;
        String str = "";
        while ((intValueOfChar = reader.read()) != -1) {
            str += (char) intValueOfChar;
        }
        reader.close();

        this.obj = new JSONObject(str);
    }
}
