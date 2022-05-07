// to represent a list of strings in a document
interface ILoString {
  // to return the length of the list
  int length();

  // to return the string in the list at the given index if exists, else throws
  String getAt(int index);

  // to generate a list of trigrams from the given list of strings
  ILoTrigram trigrams();
}

// to represent a list of trigrams
interface ILoTrigram {
  // to insert the given trigram in the list in a sorted position
  ILoTrigram insertSorted(Trigram tri);

  // to lexically sort all of the trigrams in the list
  ILoTrigram sortLex();

  // to return the most commonly occurred trigram in the list
  Trigram mostCommonTrigram();

  // to return the most commonly occurred trigram in the list if it is sorted
  Trigram mostCommonTrigramSorted();

  // to return the most commonly occurred trigram in the list if it is sorted
  // maxTri keeps the current maximum occurred trigram and maxCount keeps its
  // occurrences
  // prevTri keeps the previous trigram and prevCount keeps its occurrences
  Trigram mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri, int prevCount);

  // counts the number of occurences of a given trigram in the list
  int count(Trigram trigram);
}

// to represent an empty list of trigrams
class MtLoTrigram implements ILoTrigram {

  /*
  * TEMPLATE:
  * Methods:
  * ... this.mostCommonTrigram() ... -- Trigram
  * ... this.mostCommonTrigramSorted() ... -- Trigram
  * ... this.mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri,
  *                                int prevCount) ... -- Trigram
  * ... this.insertSorted(Trigram tri) ... -- ILoTrigram
  * ... this.sortLex() ... -- ILoTrigram
  * ... this.count(Trigram trigram) ... -- int
  */

  // to return the most commonly occurred trigram in the list
  // An empty list has no trigrams.
  public Trigram mostCommonTrigram() {
    return new Trigram("NO", "SUCH", "TRIGRAM");
  }

  // to return the most commonly occurred trigram if the list is sorted
  // An empty list has no trigrams.
  public Trigram mostCommonTrigramSorted() {
    return new Trigram("NO", "SUCH", "TRIGRAM");
  }

  // to return the most commonly occurred trigram in the list if it is sorted
  // maxTri keeps the current maximum occurred trigram and maxCount keeps its
  // occurrences
  // prevTri keeps the previous trigram and prevCount keeps its occurrences
  public Trigram mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri,
      int prevCount) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... maxTri ... -- Trigram
    * ... maxCount ... -- int
    * ... prevTri ... -- Trigram
    * ... prevCount ... -- int
    *
    * Methods:
    * ... maxTri.compareTo(Trigram other) ... -- int
    * ... maxTri.concatAllStrings() ... -- String
    * ... prevTri.compareTo(Trigram other) ... -- int
    * ... prevTri.concatAllStrings() ... -- String
    */
    if (maxCount == prevCount) {
      if (maxTri.compareTo(prevTri) < 0) {
        return maxTri;
      }
      else {
        return prevTri;
      }
    }
    else {
      return maxTri;
    }
  }

  // to insert the given trigram in the list in a sorted position
  // on the empty list, it will create a new list containing the trigram
  public ILoTrigram insertSorted(Trigram tri) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... tri ... -- Trigram
    *
    * Methods:
    * ... tri.compareTo(Trigram other) ... -- int
    * ... tri.concatAllStrings() ... -- String
    */
    return new ConsLoTrigram(tri, this);
  }

  // to lexically sort all of the trigrams in the list
  // an empty list is already sorted
  public ILoTrigram sortLex() {
    return this;
  }

  // counts the number of occurences of a given trigram in the list
  // an empty list contains zero trigrams inside
  public int count(Trigram trigram) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... trigram ... -- Trigram
    *
    * Methods:
    * ... trigram.compareTo(Trigram other) ... -- int
    * ... trigram.concatAllStrings() ... -- String
    */
    return 0;
  }
}

// to represent a non-empty list of trigrams
class ConsLoTrigram implements ILoTrigram {
  Trigram first;
  ILoTrigram rest;

  // to construct a non-empty list of trigrams
  ConsLoTrigram(Trigram first, ILoTrigram rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
  * TEMPLATE:
  * Fields:
  * ... this.first ... -- Trigram
  * ... this.rest ... -- ILoTrigram
  *
  * Methods:
  * ... this.mostCommonTrigram() ... -- Trigram
  * ... this.mostCommonTrigramSorted() ... -- Trigram
  * ... this.mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri,
  *                                int prevCount) ... -- Trigram
  * ... this.insertSorted(Trigram tri) ... -- ILoTrigram
  * ... this.sortLex() ... -- ILoTrigram
  * ... this.count(Trigram trigram) ... -- int
  *
  * Methods of Fields:
  * ... this.rest.mostCommonTrigram() ... -- Trigram
  * ... this.rest.mostCommonTrigramSorted() ... -- Trigram
  * ... this.rest.mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri,
  *                                int prevCount) ... -- Trigram
  * ... this.rest.insertSorted(Trigram tri) ... -- ILoTrigram
  * ... this.rest.sortLex() ... -- ILoTrigram
  * ... this.rest.count(Trigram trigram) ... -- int
  */

  // to return the most commonly occurred trigram in the list
  // sorts the list, and delegates to mostCommonTrigramSorted
  public Trigram mostCommonTrigram() {
    return this.sortLex().mostCommonTrigramSorted();
  }

  // to return the most commonly occurred trigram if the list is sorted
  // sets up the accumulators, and delegates mostCommonTrigramHelp
  public Trigram mostCommonTrigramSorted() {
    return this.rest.mostCommonTrigramHelp(this.first, 1, this.first, 1);
  }

  // to return the most commonly occurred trigram in the list if it is sorted
  // maxTri keeps the current maximum occurred trigram and maxCount keeps its
  // occurrences
  // prevTri keeps the previous trigram and prevCount keeps its occurrences
  public Trigram mostCommonTrigramHelp(Trigram maxTri, int maxCount, Trigram prevTri,
      int prevCount) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... maxTri ... -- Trigram
    * ... maxCount ... -- int
    * ... prevTri ... -- Trigram
    * ... prevCount ... -- int
    *
    * Methods:
    * ... maxTri.compareTo(Trigram other) ... -- int
    * ... maxTri.concatAllStrings() ... -- String
    * ... prevTri.compareTo(Trigram other) ... -- int
    * ... prevTri.concatAllStrings() ... -- String
    */
    if (this.first.compareTo(prevTri) == 0) {
      if (prevCount + 1 > maxCount) {
        return this.rest.mostCommonTrigramHelp(prevTri, prevCount + 1, prevTri, prevCount);
      }
      else {
        return this.rest.mostCommonTrigramHelp(maxTri, maxCount, prevTri, prevCount + 1);
      }
    }
    else {
      return this.rest.mostCommonTrigramHelp(maxTri, maxCount, this.first, 1);
    }
  }

  // to insert the given trigram in the list in a sorted position
  public ILoTrigram insertSorted(Trigram tri) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... tri ... -- Trigram
    *
    * Methods:
    * ... tri.compareTo(Trigram other) ... -- int
    * ... tri.concatAllStrings() ... -- String
    */
    if (this.first.compareTo(tri) < 0) {
      return new ConsLoTrigram(this.first, this.rest.insertSorted(tri));
    }
    else {
      return new ConsLoTrigram(tri, this);
    }
  }

  // to lexically sort all of the trigrams in the list
  public ILoTrigram sortLex() {
    return this.rest.sortLex().insertSorted(this.first);
  }

  // counts the number of occurences of a given trigram in the list
  public int count(Trigram trigram) {
    /*
    * TEMPLATE:
    * Everything in templates class plus params
    *
    * Parameters:
    * ... trigram ... -- Trigram
    *
    * Methods:
    * ... trigram.compareTo(Trigram other) ... -- int
    * ... trigram.concatAllStrings() ... -- String
    */
    if (this.first.compareTo(trigram) == 0) {
      return 1 + this.rest.count(trigram);
    }
    return this.rest.count(trigram);
  }

}

// to represent a trigram
class Trigram {
  String first;
  String second;
  String third;

  // to construct a trigram
  Trigram(String first, String second, String third) {
    Utils utils = new Utils();
    // validates each word in order to determine if it would be valid for a trigram
    this.first = utils.confrimValidTrigramWord(first);
    this.second = utils.confrimValidTrigramWord(second);
    this.third = utils.confrimValidTrigramWord(third);
  }

  /*
  * TEMPLATE:
  * Fields:
  * ... this.first ... -- String
  * ... this.second ... -- String
  * ... this.third ... -- String
  *
  * Methods:
  * ... this.compareTo(Trigram other) ... -- int
  * ... this.concatAllStrings() ... -- String
  */

  // to compare two trigrams lexicographically but case-insesitively
  // returns negative if this trigram comes before, and positive is this trigram
  // comes after
  int compareTo(Trigram other) {
    /*
    * TEMPLATE:
    * Parameters:
    * ... other ... -- Trigram
    *
    * Methods:
    * ... other.compareTo(Trigram other) ... -- int
    * ... other.concatAllStrings() ... -- String
    */
    return this.concatAllStrings().compareToIgnoreCase(other.concatAllStrings());
  }

  // to concatenate the three words in the trigram into a string
  String concatAllStrings() {
    return this.first + " " + this.second + " " + this.third;
  }
}

// to represent an empty list of strings in a document
class MtLoString implements ILoString {

  /*
  * TEMPLATE:
  * Methods:
  * ... this.length() ... -- int
  * ... this.getAt(int index) ... -- String
  * ... this.trigrams() ... -- ILoTrigram
  */

  // to return the length of the list
  // an empty list has length zero
  public int length() {
    return 0;
  }

  // to return the string in the list at the given index if exists, else throws
  public String getAt(int index) {
    throw new IndexOutOfBoundsException("Index out of bounds.");
  }

  // to generate a list of trigrams from the given list of strings
  public ILoTrigram trigrams() {
    return new MtLoTrigram();
  }
}

// to represent a non-empty list of strings in a document
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  // to construct a non-empty list of strings
  ConsLoString(String first, ILoString rest) {
    // checks if the given string would be valid for a trigram word
    this.first = new Utils().confrimValidTrigramWord(first);
    this.rest = rest;
  }

  /*
  * TEMPLATE:
  * Fields:
  * ... this.first ... -- String
  * ... this.rest ... -- ILoString
  *
  * Methods:
  * ... this.length() ... -- int
  * ... this.getAt(int index) ... -- String
  * ... this.trigrams() ... -- ILoTrigram
  *
  * Methods of Fields:
  * ... this.rest.length() ... -- int
  * ... this.rest.getAt(int index) ... -- String
  * ... this.rest.trigrams() ... -- ILoTrigram
  */

  // to return the length of the list
  // an empty list has length zero
  public int length() {
    return 1 + rest.length();
  }

  // to return the string in the list at the given index if exists, else throws
  public String getAt(int index) {
    if (index == 0) {
      return this.first;
    }
    else {
      return this.rest.getAt(index - 1);
    }
  }

  // to generate a list of trigrams from the given list of strings
  public ILoTrigram trigrams() {
    if (this.length() >= 3) {
      return new ConsLoTrigram(new Trigram(this.getAt(0), this.getAt(1), this.getAt(2)),
          this.rest.trigrams());
    }
    else {
      return new MtLoTrigram();
    }
  }
}

class Utils {

  /*
  * TEMPLATE:
  * Methods:
  * ... this.confrimValidTrigramWord(String s) ... -- String
  */

  // checks if the given string is a valid word for a trigram, else throws
  String confrimValidTrigramWord(String s) {
    if (s.contains(" ")) {
      throw new IllegalArgumentException(
          "The given word <" + s + "> has spaces, thus it is invalid for a trigram");
    }
    if (s.equals("")) {
      throw new IllegalArgumentException(
          "The given word is an empty string, thus it is invalid for a trigram");
    }
    return s;
  }
}
