package collections.framework;

public interface Serializer {
  byte[] getBytes(Object object);

  Object getObject(byte[] bytes);
}
