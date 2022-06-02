package collections;

import collections.framework.ImmutableCollection;

public class Main {
  public static void main(String[] args) {
    ImmutableCollection<Address> addresses = ImmutableCollection.of(
        new Address(1, "Street 1", "City 1", "State 1"),
        new Address(2, "Street 2", "City 2", "State 2"),
        new Address(3, "Street 3", "City 3", "State 3"),
        new Address(4, "Street 4", "City 4", "State 4"),
        new Address(5, "Street 5", "City 5", "State 5")
    );

    System.out.println(addresses.contains(new Address(1, "Street 1", "City 1", "State 1")));

    ImmutableCollection<Integer> collection = ImmutableCollection.of(1, 2, 3, 4, 5);
    System.out.println(collection.contains(4));
    System.out.println(collection.size());
  }
}
