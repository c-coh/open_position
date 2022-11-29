import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Applications. Guarantees the
 * min-heap invariant, so that the Application at the root should have the lowest score, and
 * children always have a higher or equal score as their parent. The root of a non-empty queue is
 * always at index 0 of this array-heap.
 */
public class ApplicationQueue implements PriorityQueueADT<Application>, Iterable<Application> {
  private Application[] queue; // array min-heap of applications representing this priority queue
  private int size; // size of this priority queue

  /**
   * Creates a new empty ApplicationQueue with the given capacity
   * 
   * @param capacity Capacity of this ApplicationQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public ApplicationQueue(int capacity) throws IllegalArgumentException {
    // TODO verify the capacity
    if (capacity <= 0) {
      throw new IllegalArgumentException("invalid capacity");
    }
    queue = new Application[capacity];
    size = 0;
  }

  /**
   * Checks whether this ApplicationQueue is empty
   * 
   * @return {@code true} if this ApplicationQueue is empty
   */
  @Override
  public boolean isEmpty() {
    if (size == 0) {
      return true;
    }
    return false;
  }

  /**
   * Returns the size of this ApplicationQueue
   * 
   * @return the size of this ApplicationQueue
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Adds the given Application to this ApplicationQueue and use the percolateUp() method to
   * maintain min-heap invariant of ApplicationQueue. Application should be compared using the
   * Application.compareTo() method.
   * 
   * 
   * @param o Application to add to this ApplicationQueue
   * @throws NullPointerException  if the given Application is null
   * @throws IllegalStateException with a descriptive error message if this ApplicationQueue is full
   */
  @Override
  public void enqueue(Application o) throws NullPointerException, IllegalStateException {
    // TODO verify the application
    if (o == null) {
      throw new NullPointerException("application is null");
    }
    // TODO verify that the queue is not full
    if (size == queue.length) {
      throw new IllegalStateException("queue is full");
    }
    // TODO if allowed, add the application to the queue and percolate to restore the heap condition
    queue[size] = o;
    percolateUp(size); // TODO fix this argument
    size++;
  }

  /**
   * Removes and returns the Application at the root of this ApplicationQueue, i.e. the Application
   * with the lowest score.
   * 
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException with a descriptive error message if this ApplicationQueue is
   *                                empty
   */
  @Override
  public Application dequeue() throws NoSuchElementException {
    // verify that the queue is not empty
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }

    // save the lowest-scoring application
    Application temp = queue[0];

    // replace the root of the heap and percolate to restore the heap condition
    queue[0] = queue[size - 1];
    queue[size - 1] = null;
    percolateDown(0);

    size--;
    // return the lowest-scoring application
    return temp;
  }

  /**
   * An implementation of percolateDown() method. Restores the min-heap invariant of a given subtree
   * by percolating its root down the tree. If the element at the given index does not violate the
   * min-heap invariant (it is due before its children), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, then swap the element with the correct child and
   * continue percolating the element down the heap.
   * 
   * This method may be implemented recursively OR iteratively.
   * 
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateDown(int i) throws IndexOutOfBoundsException {
    if (i >= queue.length) {
      throw new IndexOutOfBoundsException("invalid index");
    }

    boolean done = false;
    Application temp;
    Application c;
    int curr = i;
    int right;
    int left;
    while (done == false) {
      left = curr * 2 + 1;
      right = curr * 2 + 2;
      c = queue[curr];

      if ((left >= queue.length || queue[left] == null)
          && (right >= queue.length || queue[right] == null)) {
        done = true;
      } else if (right >= queue.length || queue[right] == null) {
        if (c.compareTo(queue[left]) > 0) {
          // System.out.println("I chose " + queue[left].toString());
          temp = queue[left];
          queue[left] = c;
          queue[curr] = temp;
          curr = left;
        } else {
          done = true;
        }
      } else if (left >= queue.length || queue[left] == null) {
        if (c.compareTo(queue[right]) > 0) {
          // System.out.println("I chose " + queue[right].toString());
          temp = queue[right];
          queue[right] = c;
          queue[curr] = temp;
          curr = right;
        } else {
          done = true;
        }
      } else {
        if (queue[left].compareTo(queue[right]) < 0) {
          if (c.compareTo(queue[left]) > 0) {
            // System.out.println("I chose " + queue[left].toString() + " over " +
            // queue[right].toString());
            temp = queue[right];
            queue[right] = c;
            queue[curr] = temp;
            curr = right;
          } else {
            done = true;
          }
        } else {
          if (c.compareTo(queue[right]) > 0) {
            // System.out.println("I chose " + queue[right].toString() + " over " +
            // queue[left].toString());
            temp = queue[right];
            queue[right] = c;
            queue[curr] = temp;
            curr = right;
          } else {
            done = true;
          }
        }
      }
    }
  }



  /**
   * An implementation of percolateUp() method. Restores the min-heap invariant of the tree by
   * percolating a leaf up the tree. If the element at the given index does not violate the min-heap
   * invariant occurs after its parent), then this method does not modify the heap. Otherwise, if
   * there is a heap violation, swap the element with its parent and continue percolating the
   * element up the heap.
   * 
   * This method may be implemented recursively OR iteratively.
   * 
   * Feel free to add private helper methods if you need them.
   * 
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateUp(int i) {
    int curr = i;
    Application temp;
    while (curr != 0 && queue[curr].compareTo(queue[(curr - 1) / 2]) < 0) {
      temp = queue[curr];
      queue[curr] = queue[(curr - 1) / 2];
      queue[(curr - 1) / 2] = temp;
      curr = (curr - 1) / 2;
    }
  }


  /**
   * Returns the Application at the root of this ApplicationQueue, i.e. the Application with the
   * lowest score.
   * 
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException if this ApplicationQueue is empty
   */
  @Override
  public Application peek() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }

    return queue[0];
  }

  /**
   * Returns a deep copy of this ApplicationQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * applications. Only the instance of the heap (including the array and its size) will be
   * duplicated.
   * 
   * @return a deep copy of this ApplicationQueue. The returned new application queue has the same
   *         length and size as this queue.
   */
  public ApplicationQueue deepCopy() {
    ApplicationQueue copy = new ApplicationQueue(queue.length);
    for (Application a : queue) {
      if (a != null) {
        copy.enqueue(a);
      }
    }
    // TODO: use toString() to duplicate
    // TODO implement this method according to its Javadoc comment

    return copy; // TODO fix
  }

  /**
   * Returns a String representing this ApplicationQueue, where each element (application) of the
   * queue is listed on a separate line, in order from the lowest score to the highest score.
   * 
   * This implementation is provided.
   * 
   * @see Application#toString()
   * @see ApplicationIterator
   * @return a String representing this ApplicationQueue
   */
  @Override
  public String toString() {
    StringBuilder val = new StringBuilder();

    for (Application a : this) {
      val.append(a).append("\n");
    }

    return val.toString();
  }

  /**
   * Returns an Iterator for this ApplicationQueue which proceeds from the lowest-scored to the
   * highest-scored Application in the queue.
   * 
   * This implementation is provided.
   * 
   * @see ApplicationIterator
   * @return an Iterator for this ApplicationQueue
   */
  @Override
  public Iterator<Application> iterator() {
    return new ApplicationIterator(this);
  }
}
