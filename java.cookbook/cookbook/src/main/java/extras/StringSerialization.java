package extras;

import java.io.*;
import java.util.Base64;


public class StringSerialization {
    static record Person(String name, Integer age) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
    }

    static String serialize(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    static Object deserialize(String input) throws IOException, ClassNotFoundException {
        Object result = null;
        byte[] data = Base64.getDecoder().decode(input);
        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            result = ois.readObject();
        }

        return result;
    }
}
