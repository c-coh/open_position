/**
 * A application handler of an open position using priority queue. Only saves a new Application when
 * the queue is not full, or when it can replace older, lower-scored ones with its higher scores.
 */
public class OpenPosition {
  private String positionName;
  private ApplicationQueue applications; // the priority queue of all applications
  private int capacity; // the number of vacancies

  /**
   * Creates a new open position with the given capacity
   * 
   * @param capacity the number of vacancies of this position
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public OpenPosition(String positionName, int capacity) throws IllegalArgumentException {
    if (capacity <= 0) {
      throw new IllegalArgumentException("invalid capacity");
    }
    this.positionName = positionName;
    this.capacity = capacity;
    applications = new ApplicationQueue(capacity);
  }

  public String getPositionName() {
    return this.positionName;
  }

  /**
   * Tries to add the given Application to the priority queue of this position. return False when
   * the new Application has a lower score than the lowest-scored Application in the queue.
   * 
   * @return Whether the given Application was added successfully
   */
  public boolean add(Application application) {
    // if the queue is full, determine whether this application has a higher score than
    // the current lowest-scoring application; if not, do not add it
    try {
      applications.enqueue(application);
      return true;
    } catch (IllegalStateException e) {
      // if this application has a higher score than lowest-scoring app, add it to the queue
      if (applications.peek().compareTo(application) < 0) {
        applications.dequeue();
        applications.enqueue(application);
        return true;
      }
      return false;
    }
  }

  /**
   * Returns the list of Applications in the priority queue.
   * 
   * @return The list of Applications in the priority queue, in increasing order of the scores.
   */
  public String getApplications() {
    return applications.toString();
  }

  /**
   * Returns the total score of Applications in the priority queue.
   * 
   * @return The total score of Applications in the priority queue.
   */
  public int getTotalScore() {
    ApplicationIterator iterator = new ApplicationIterator(applications);
    int score = 0;
    Application curr;
    while (iterator.hasNext()) {
      curr = iterator.next();
      score += curr.getScore();
    }

    return score;
  }
}
