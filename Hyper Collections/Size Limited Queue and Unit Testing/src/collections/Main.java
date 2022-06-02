package collections;

import collections.framework.SizeLimitedQueue;

public class Main {
  public static void main(String[] args) {
    SizeLimitedQueue<Integer> queue = new SizeLimitedQueue<>(5);
    System.out.println(queue); 
  }
}
