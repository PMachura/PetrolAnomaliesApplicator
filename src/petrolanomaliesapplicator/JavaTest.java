/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemek
 */
public class JavaTest {

    public static class Person {

        int age;
        String name;
        LocalDateTime birthDate;

        public Person(int age, String name, LocalDateTime birthDate) {
            this.age = age;
            this.name = name;
            this.birthDate = birthDate;
        }

    }

    public static void main(String[] args) {
        Collection<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Object person = new Person(23, "Jan", LocalDateTime.now());
        try {
            Field field = person.getClass().getDeclaredField("birthDate");
            field.setAccessible(true);
            Object date = field.get(person);
            
            System.out.println(date);
            
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
