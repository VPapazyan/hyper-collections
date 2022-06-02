package collections.framework;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
public class ImmutableCollection<E extends Serializable> {

  private final byte[][] data;
  private static final Serializer serializer = new InMemorySerializer();


  private ImmutableCollection() {
    this(new byte[][]{});
  }

  private ImmutableCollection(byte[][] data) {
    this.data = data;
  }

  @SafeVarargs
  public static <E extends Serializable> ImmutableCollection<E> of(E... args) {
    byte[][] data = new byte[args.length][];

    for (int i = 0; i < args.length; i++) {
      data[i] = serializer.getBytes(Objects.requireNonNull(args[i]));
    }
    return new ImmutableCollection<>(data);
  }

  public static <E extends Serializable> ImmutableCollection<E> of() {
    return new ImmutableCollection<>();
  }

  public boolean contains(E element) {
    byte[] elementBytes = serializer.getBytes(element);

    /* for (byte[] e : this.items) {
      if (Arrays.equals(e, elementBytes))
        return true;
    }

    return false; */

    for (byte[] e : data) {

      if (elementBytes.length != e.length)
        continue;

      boolean flag = true;
      for (int i = 0; i < e.length; i++) {
        if (e[i] != elementBytes[i]) {
          flag = false;
          break;
        }
      }

      if (flag)
        return true;
    }

    return false;
  }

  public int size() {
    return this.data.length;
  }

  public boolean isEmpty() {
    return this.size() == 0;
  }
}
