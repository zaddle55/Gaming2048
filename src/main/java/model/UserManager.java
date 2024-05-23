package model;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.util.*;
public class UserManager {
    List<User> userList;
    public UserManager() {
        List<User> userList = new List<>() {
            @Override
            public int size() {
                return 0;
            }
            @Override
            public boolean isEmpty() {
                return false;
            }
            @Override
            public boolean contains(Object o) {
                return false;
            }
            @NotNull
            @Override
            public Iterator<User> iterator() {
                return null;
            }
            @NotNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }
            @NotNull
            @Override
            public <T> T[] toArray(@NotNull T[] a) {
                return null;
            }
            @Override
            public boolean add(User user) {
                return false;
            }
            @Override
            public boolean remove(Object o) {
                return false;
            }
            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }
            @Override
            public boolean addAll(@NotNull Collection<? extends User> c) {
                return false;
            }
            @Override
            public boolean addAll(int index, @NotNull Collection<? extends User> c) {
                return false;
            }
            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }
            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }
            @Override
            public void clear() {
            }
            @Override
            public boolean equals(Object o) {
                return false;
            }
            @Override
            public int hashCode() {
                return 0;
            }
            @Override
            public User get(int index) {
                return null;
            }
            @Override
            public User set(int index, User element) {
                return null;
            }
            @Override
            public void add(int index, User element) {
            }
            @Override
            public User remove(int index) {
                return null;
            }
            @Override
            public int indexOf(Object o) {
                return 0;
            }
            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }
            @NotNull
            @Override
            public ListIterator<User> listIterator() {
                return null;
            }
            @NotNull
            @Override
            public ListIterator<User> listIterator(int index) {
                return null;
            }
            @NotNull
            @Override
            public List<User> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }
    public void register(String name, String password) {
        if (hasUser(name)) {
            JOptionPane.showMessageDialog(null, "This user has existed!", "Register Failure", JOptionPane.ERROR_MESSAGE);
        } else if (!isPasswordValid(password)) {
            JOptionPane.showMessageDialog(null, "Invalid password!" + "Your password should contain at least 8 characters.", "Register Failure", JOptionPane.ERROR_MESSAGE);
        } else {
            userList.add(new User(name, password, "src/main/resources/user/" + name + "/"));

        }
    }
    public void logIn(String name, String password) {
        if (!hasUser(name)) {
            JOptionPane.showMessageDialog(null, "This user doesn't exist!", "Log-in Failure", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getName().equals(name)) {
                    if (!userList.get(i).getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(null, "Incorrect password!", "Log-in Failure", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null,"Welcome," + name + "!", "Log-in Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
            }
        }
    }
    public boolean hasUser(String name) {
        boolean hasUser = false;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(name)) {
                hasUser = true;
                break;
            }
        }
        return hasUser;
    }
    public boolean isPasswordValid(String password) {
        boolean isPasswordValid = false;
        if (password.length() >= 8) {
            isPasswordValid = true;
        }
        return isPasswordValid;
    }
    public boolean isPasswordCorrect(String name, String password) {
        boolean isPasswordCorrect = false;
        return isPasswordCorrect;
    }
}