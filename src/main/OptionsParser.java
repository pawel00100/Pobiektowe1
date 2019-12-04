package main;

import java.util.LinkedList;
import java.util.List;

public class OptionsParser {

    private static MoveDirection parseString(String stringToParse) {
        MoveDirection result;
        switch (stringToParse) {
            case "f":
            case "forward":
                result = MoveDirection.FORWARD;
                break;
            case "b":
            case "backward":
                result = MoveDirection.BACKWARD;
                break;
            case "l":
            case "left":
                result = MoveDirection.LEFT;
                break;
            case "r":
            case "right":
                result = MoveDirection.RIGHT;
                break;
            default:
                throw new IllegalArgumentException(stringToParse + " is not legal move");
        }
        return result;
    }

    public static MoveDirection[] parse(String[] strings) {
        List<MoveDirection> finished = new LinkedList<>();

        for (String stringToParse : strings) {
            MoveDirection result = parseString(stringToParse);
            if (result != null)
                finished.add(result);
        }

        MoveDirection[] finishedArray = new MoveDirection[finished.size()];
        return finished.toArray(finishedArray);
    }

}
