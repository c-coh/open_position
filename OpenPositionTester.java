import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Application,
 * ApplicationIterator, ApplicationQueue and OpenPosition classes in the assignment.
 *
 */
public class OpenPositionTester {

  /**
   * This method tests and makes use of the Application constructor, getter methods, toString() and
   * compareTo() methods.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplication() {
    // create an Application with valid input
    Application test = new Application("test", "test@gmail.com", 10);

    // create an Application with invalid input:
    // blank name
    try {
      test = new Application("", "test@gmail.com", 10);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    try {
      test = new Application(null, "test@gmail.com", 10);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // null email
    try {
      test = new Application("test", null, 10);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // no @ email
    try {
      test = new Application("test", "test.gmail.com", 10);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // too many @ email
    try {
      test = new Application("test", "test@gmail@com", 10);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // invalid score
    try {
      test = new Application("test", "test@gmail.com", -1);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    try {
      test = new Application("test", "test@gmail.com", 101);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    try {
      test = new Application("test", "test@gmail.com", 0);
    } catch (Exception e) {
      return false;
    }

    try {
      test = new Application("test", "test@gmail.com", 100);
    } catch (Exception e) {
      return false;
    }

    test = new Application("test", "test@gmail.com", 10);
    Application test2 = new Application("test2", "test2@gmail.com", 1);

    // verify getters
    if (!test.getName().equals("test") || !test.getEmail().equals("test@gmail.com")
        || test.getScore() != 10) {
      return false;
    }
    // verify compareTo
    if (test2.compareTo(test) >= 0) {
      return false;
    }

    // verify toString
    if (!test.toString().equals("test:test@gmail.com:10")) {
      return false;
    }

    return true;
  }

  /**
   * This method tests and makes use of the ApplicationIterator class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplicationIterator() {
    // create an ApplicationQueue with capacity at least 3
    // and at least 3 Applications with different scores
    ApplicationQueue test = new ApplicationQueue(5);
    Application one = new Application("1", "1@gmail.com", 10);
    Application two = new Application("2", "2@gmail.com", 20);
    Application three = new Application("3", "3@gmail.com", 30);

    String ans = "1:1@gmail.com:10\n2:2@gmail.com:20\n3:3@gmail.com:30\n";

    // add those Applications to the queue
    test.enqueue(three);
    test.enqueue(two);
    test.enqueue(one);

    // verify that iterating through the queue gives you the applications in order of
    // INCREASING score
    if (!test.toString().equals(ans)) {
      return false;
    }

    return true;
  }

  /**
   * This method tests and makes use of the enqueue() and dequeue() methods in the ApplicationQueue
   * class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testEnqueueDequeue() {
    // create an ApplicationQueue with capacity 3
    // and at least 4 Applications with different scores
    ApplicationQueue test = new ApplicationQueue(3);
    Application one = new Application("1", "1@gmail.com", 10);
    Application two = new Application("2", "2@gmail.com", 20);
    Application three = new Application("3", "3@gmail.com", 30);
    Application four = new Application("4", "4@gmail.com", 40);
    // enqueue an invalid value (null)
    try {
      test.enqueue(null);
      return false;
    } catch (Exception e) {
      if (!(e instanceof NullPointerException)) {
        return false;
      }
    }

    // enqueue one valid application
    test.enqueue(three);

    if (test.size() != 1 || test.peek() != three) {
      return false;
    }
    // enqueue two more valid applications TODO

    test.enqueue(two);
    test.enqueue(one);
    if (test.size() != 3 || test.peek() != one) {
      return false;
    }
    // enqueue one more application (exceeds capacity)
    try {
      test.enqueue(four);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalStateException)) {
        return false;
      }
    }

    // dequeue one application (should be lowest score)
    if (test.dequeue() != one) {
      return false;
    }

    // dequeue all applications
    if (test.dequeue() != two) {
      return false;
    }
    if (test.dequeue() != three) {
      return false;
    }

    // dequeue from an empty queue
    try {
      test.dequeue();
      return false;
    } catch (Exception e) {
      if (!(e instanceof NoSuchElementException)) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method tests and makes use of the common methods (isEmpty(), size(), peek()) in the
   * ApplicationQueue class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testCommonMethods() {
    // create an ApplicationQueue with 0 capacity (should fail)
    try {
      ApplicationQueue fail = new ApplicationQueue(0);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // create an ApplicationQueue with capacity 3
    // and at least 3 Applications with different scores
    ApplicationQueue test = new ApplicationQueue(3);
    Application one = new Application("1", "1@gmail.com", 10);
    Application two = new Application("2", "2@gmail.com", 20);
    Application three = new Application("3", "3@gmail.com", 30);
    // verify the methods' behaviors on an empty queue

    if (test.isEmpty() != true || test.size() != 0) {
      return false;
    }

    try {
      test.peek();
      return false;
    } catch (Exception e) {
      if (!(e instanceof NoSuchElementException)) {
        return false;
      }
    }

    // add one Application and verify the methods' behaviors
    test.enqueue(two);

    if (test.isEmpty() != false || test.size() != 1 || test.peek() != two) {
      return false;
    }

    // add the rest of the Applications and verify the methods' behaviors
    test.enqueue(one);
    test.enqueue(three);
    if (test.isEmpty() != false || test.size() != 3 || test.peek() != one) {
      return false;
    }
    return true;
  }

  /**
   * This method tests and makes use of OpenPosition class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testOpenPosition() {
    // create an OpenPosition with 0 capacity (should fail)
    try {
      OpenPosition fail = new OpenPosition("test", 0);
      return false;
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException)) {
        return false;
      }
    }

    // create an OpenPosition with capacity 3
    OpenPosition test = new OpenPosition("test", 3);
    // and at least 5 Applications with different scores
    Application one = new Application("1", "1@gmail.com", 10);
    Application two = new Application("2", "2@gmail.com", 20);
    Application three = new Application("3", "3@gmail.com", 30);
    Application four = new Application("4", "4@gmail.com", 40);
    Application five = new Application("5", "5@gmail.com", 50);

    // verify that the 3 MIDDLE-scoring Applications can be added
    // don't use the highest and lowest scoring applications YET
    String ans = "2:2@gmail.com:20\n3:3@gmail.com:30\n4:4@gmail.com:40\n";
    test.add(four);
    test.add(two);
    test.add(three);

    // verify that getApplications returns the correct value for your input
    if (!test.getApplications().equals(ans)) {
      return false;
    }

    // verify that the result of getTotalScore is the sum of all 3 Application scores
    if (test.getTotalScore() != 90) {
      return false;
    }

    // verify that the lowest-scoring application is NOT added to the OpenPosition
    if (test.add(one) == true) {
      return false;
    }

    // verify that the highest-scoring application IS added to the OpenPosition
    if (test.add(five) == false) {
      return false;
    }

    // verify that getApplications has changed correctly
    String ans2 = "3:3@gmail.com:30\n4:4@gmail.com:40\n5:5@gmail.com:50\n";
    if (!test.getApplications().equals(ans2)) {
      return false;
    }

    // verify that the result of getTotalScore has changed correctly
    if (test.getTotalScore() != 120) {
      return false;
    }
    return true; // TODO change this
  }

  /**
   * This method calls all the test methods defined and implemented in your OpenPositionTester
   * class.
   * 
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    return testApplication() && testApplicationIterator() && testEnqueueDequeue()
        && testCommonMethods() && testOpenPosition();
  }

  /**
   * Driver method defined in this OpenPositionTester class
   * 
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    System.out.println(runAllTests());

  }
}
