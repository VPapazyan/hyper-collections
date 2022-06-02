package collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class BiMap<K, V> {

  private final Map<K, V> map;
  private final Map<V, K> inverse;

  private BiMap(Map<K, V> map, Map<V, K> inverse) {
    this.map = map;
    this.inverse = inverse;
  }

  public BiMap() {
    this(new HashMap<>(), new HashMap<>());
  }

  public V forcePut(K key, V value) {
    if (map.containsKey(key)) {
      V v = map.get(key);
      map.remove(key);
      inverse.remove(v);
    }

    return put(key, value);
  }

  public BiMap<V, K> inverse() {
    return new BiMap<>(new HashMap<>(inverse), new HashMap<>(map));
  }

  public V put(K key, V value) {
    map.put(key, value);
    inverse.put(value, key);
    return value;
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
      this.put(e.getKey(), e.getValue());
    }
  }

  public Set<V> values() {
    return new HashSet<>(inverse.keySet());
  }
}
