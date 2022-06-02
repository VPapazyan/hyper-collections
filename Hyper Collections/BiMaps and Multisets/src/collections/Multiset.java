package collections;

import java.util.*;

@SuppressWarnings("unused")
public class Multiset<E> {

  private final Map<E, Integer> countMap = new HashMap<>();

  public void add(E element, int occurrences) {
    Integer count = countMap.get(element);
    if (count != null) {
      countMap.put(element, count + occurrences);
      return;
    }
    countMap.put(element, occurrences);
  }

  public void add(E element) {
    this.add(element, 1);
  }

  public boolean contains(E element) {
    return countMap.containsKey(element);
  }

  public int count(E element) {
    return Optional.ofNullable(countMap.get(element)).orElse(0);
  }

  public Set<E> elementSet() {
    return countMap.keySet();
  }

  public void remove(E element) {
    this.remove(element, 1);
  }

  public void remove(E element, int occurrences) {
    int count = this.count(element);
    if (count == 0) {
      return;
    }

    if (count <= occurrences) {
      this.countMap.remove(element);
      return;
    }

    int newCount = count - occurrences;
    this.setCount(element, newCount);
  }

  public void setCount(E element, int count) {
    if (!contains(element))
      return;

    countMap.put(element, count);
  }

  public void setCount(E element, int oldCount, int newCount) {
    int count = count(element);

    if (count == 0)
      return;

    if (oldCount != count) {
      return;
    }

    this.setCount(element, newCount);
  }

  private List<E> list() {
    List<E> list = new ArrayList<>();
    for (var it : this.countMap.entrySet()) {
      for (int i = 0; i < it.getValue(); i++) {
        list.add(it.getKey());
      }
    }
    return list;
  }

  @Override
  public String toString() {
    return list().toString();
  }
}

