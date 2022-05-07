
import lombok.ToString;

import java.util.ArrayList;

@ToString
class ArrList {
    ArrayList<String> list;
    int x;

    ArrList(ArrayList<String> list, int x) {
        this.list = list;
        this.x = x;
    }
}

class BetterToString {
    public static <T> String make(T obj) {
        if (obj instanceof String) {
//      // TODO: actually do the conversion
//      return "\"" + obj.toString() + "\"";
            String buffer = "";
            char[] chars = ((String) obj).toCharArray();
            for (char c : chars) {
                switch(c) {
                    case '\t':
                        buffer += "\\" + "t";
                        break;
                    case '\b':
                        buffer += "\\" + "b";
                        break;
                    case '\n':
                        buffer += "\\" + "n";
                        break;
                    case '\r':
                        buffer += "\\" + "r";
                        break;
                    case '\f':
                        buffer += "\\" + "f";
                        break;
                    case '\'':
                    case '\"':
                    case '\\':
                        buffer += "\\" + c;
                        break;
                    default:
                        buffer += c;
                        break;
                }
            }
            return "\"" + buffer + "\"";
        }
        else {
            return obj.toString();
        }
    }
}
