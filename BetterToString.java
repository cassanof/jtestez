// NOTE: the idea is that we would put this class automatically in every java file

class BetterToString {
  public static <T> String make(T obj) {
    if (obj instanceof String) {
      // TODO: actually do the conversion
      return obj.toString();
    }
    else {
      return obj.toString();
    }
  }
}
