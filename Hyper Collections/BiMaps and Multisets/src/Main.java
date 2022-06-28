import collections.BiMap;
import collections.Multiset;

import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    Map<String, Integer> test = new HashMap<>();
    System.out.println(test.getClass().getTypeParameters()[1]);


    Multiset<Character> multiset = new Multiset<>();
    multiset.add('a');
    multiset.add('b', 6);

    System.out.println(multiset); // [a, b, b, b, b, b, b]
    System.out.println(multiset.contains('c')); // false
    System.out.println(multiset.count('b')); // 6
    System.out.println(multiset.elementSet()); // ['a', 'b']

    multiset.remove('a');
    multiset.remove('b', 3);

    System.out.println(multiset); // [b, b, b]

    multiset.add('c');
    multiset.setCount('c', 2);
    multiset.setCount('b', 3, 4);

    System.out.println(multiset); // [b, b, b, b, c, c]
  }

  private static void biMapDemo() {
    BiMap<Character, Integer> biMap = new BiMap<>();

    biMap.put('a', 3);
    biMap.putAll(Map.of('b', 4, 'c', 5));

    System.out.println(biMap.values()); // [3, 4, 5]

    biMap.forcePut('a', 8);

    System.out.println(biMap); // {a=8, b=4, c=5}

    System.out.println(biMap.inverse()); // {8=a, 4=b, 5=c}
  }
}
