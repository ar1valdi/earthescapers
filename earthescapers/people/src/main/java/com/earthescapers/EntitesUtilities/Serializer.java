package com.earthescapers.EntitesUtilities;

import java.io.*;
import java.util.Collection;

public class Serializer
{
    public static void serializeCollection(Collection<Object> collection, String filepath) {
        try (
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)
        ) {
            out.writeObject(collection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<Object> deserializeCollection(String fileName) {
        try (FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Collection<Object>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
