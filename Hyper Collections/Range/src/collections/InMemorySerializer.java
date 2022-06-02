package collections;

import java.io.*;

public class InMemorySerializer implements Serializer {

  public byte[] getBytes(Object object) {
    ByteArrayOutputStream memory = new ByteArrayOutputStream();
    ObjectOutputStream out;

    try {
      out = new ObjectOutputStream(memory);
      out.writeObject(object);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }

    return memory.toByteArray();
  }

  public Object getObject(byte[] bytes) {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    try {
      ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
      return objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
