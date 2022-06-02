package collections.framework;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

@SuppressWarnings("unused")
public class SizeLimitedQueue<E> {

  private final int limit;
  private final Deque<E> q = new ArrayDeque<>();

  public SizeLimitedQueue(int limit) {
    this.limit = limit;
  }

  public void add(E element) {
    if (q.size() == limit) {
      q.removeFirst();
    }
    q.add(element);
  }

  public void clear() {
    q.clear();
  }

  public boolean isAtFullCapacity() {
    return q.size() == limit;
  }

  public boolean isEmpty() {
    return q.isEmpty();
  }

  public int maxSize() {
    return limit;
  }

  public E peek() {
    return q.peek();
  }

  public E remove() {
    return q.remove();
  }

  public int size() {
    return q.size();
  }

  public E[] toArray(E[] e) {
    return q.toArray(e);
  }
}
