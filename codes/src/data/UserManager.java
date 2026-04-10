package data;

public final class UserManager {
    private static List<User> users = new ArrayList<>();
    private static String dataPath;

    public static void addUser(User u){
        users.add(u);
    }

    public static void deleteUser(String username){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
            }
        }
    }

    public static void loadUsers(){

    }

    public static void saveUsers(){

    }
}
