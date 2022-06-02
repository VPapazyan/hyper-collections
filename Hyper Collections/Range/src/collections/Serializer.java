package collections;

public interface Serializer {
  byte[] getBytes(Object object);

  Object getObject(byte[] bytes);
}
