package collections.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class ImmutableCollection<E> {
  private final List<E> items;

  private ImmutableCollection() {
    this.items = new ArrayList<>();
  }

  private ImmutableCollection(E[] items) {
    this.items = new ArrayList<>();
    for (var i : items) {
      this.items.add(Objects.requireNonNull(i));
    }
  }

  public static <E> ImmutableCollection<E> of() {
    return new ImmutableCollection<>();
  }

  @SafeVarargs
  public static <E> ImmutableCollection<E> of(E... items) {
    return new ImmutableCollection<>(items);
  }

  public boolean contains(E element) {
    return items.contains(element);
  }

  public int size() {
    return items.size();
  }

  public boolean isEmpty() {
    return this.size() == 0;
  }
}
