package handlers;

import java.sql.*;

public class Database {
    private static Connection connection;

    public static void open() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            createTablesIfNotExists();
            System.out.println("[Database.java] Verbindung erfolgreich hergestellt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTablesIfNotExists() {
        String createSettingsTable = "CREATE TABLE IF NOT EXISTS settings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "musicEnabled BOOLEAN, " +
                "soundsEnabled BOOLEAN" +
                ");";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR UNIQUE, " + // Add UNIQUE constraint
                "lastSeen INTEGER, " +
                "highscore INTEGER" +
                ");";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createSettingsTable);
            statement.execute(createUsersTable);

            String insertQuery = "INSERT INTO settings (musicEnabled, soundsEnabled) VALUES (?, ?);";
            PreparedStatement statement2 = connection.prepareStatement(insertQuery);
            statement2.setBoolean(1, true);
            statement2.setBoolean(2, true);
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            connection.close();
            System.out.println("[Database.java] Verbindung erfolgreich beendet.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(String name, int lastSeen, int highscore) {
        try {
            String insertOrUpdateQuery = "INSERT INTO users (name, lastSeen, highscore) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT (name) DO UPDATE SET lastSeen = ?, highscore = ?;";
            PreparedStatement statement = connection.prepareStatement(insertOrUpdateQuery);
            statement.setString(1, name);
            statement.setInt(2, lastSeen);
            statement.setInt(3, highscore);
            statement.setInt(4, lastSeen);
            statement.setInt(5, highscore);
            statement.executeUpdate();
            System.out.println("[Database.java] Spieler " + name + " erfolgreich eingetragen.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSettings(boolean musicEnabled, boolean soundsEnabled) {
        try {
            String replaceQuery = "UPDATE settings SET musicEnabled = ?, soundsEnabled = ? WHERE id = 1;";
            PreparedStatement statement = connection.prepareStatement(replaceQuery);
            statement.setBoolean(1, musicEnabled);
            statement.setBoolean(2, soundsEnabled);
            statement.executeUpdate();
            System.out.println("[Database.java] Einstellungen erfolgreich gespeichert.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getLastSeenPlayerName() {
        String playerName = null;

        try {
            String query = "SELECT name FROM users ORDER BY lastSeen DESC LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                playerName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[Database.java] Letzten Spieler erfolgreich ausgelesen.");
        return playerName;
    }

    public static boolean isMusicEnabled() {
        boolean musicEnabled = true;

        try {
            String query = "SELECT musicEnabled FROM settings WHERE id = 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                musicEnabled = resultSet.getBoolean("musicEnabled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[Database.java] Musikstatus erfolgreich ausgelesen.");
        return musicEnabled;
    }

    public static boolean areSoundsEnabled() {
        boolean soundsEnabled = true;

        try {
            String query = "SELECT soundsEnabled FROM settings WHERE id = 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                soundsEnabled = resultSet.getBoolean("soundsEnabled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[Database.java] Musikstatus erfolgreich ausgelesen.");
        return soundsEnabled;
    }
}
