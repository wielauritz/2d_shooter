package handlers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    Database.java
    Einrichten einer Datenbank(verbindung) zum Speichern von Einstellungen und Spielern mit deren Punkten und Namen
    Geschrieben von Lauritz Wiebusch
 */

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
                "musicEnabled BOOLEAN UNIQUE, " +
                "soundsEnabled BOOLEAN UNIQUE" +
                ");";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR UNIQUE, " +
                "lastSeen INTEGER, " +
                "lastScore INTEGER, " +
                "highscore INTEGER" +
                ");";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createSettingsTable);
            statement.execute(createUsersTable);

            String settingsCheckQuery = "SELECT COUNT(*) FROM settings;";
            PreparedStatement settingsCheckStatement = connection.prepareStatement(settingsCheckQuery);
            ResultSet settingsResultSet = settingsCheckStatement.executeQuery();
            settingsResultSet.next();

            if (settingsResultSet.getInt(1) == 0) {
                String settingsInsertQuery = "INSERT INTO settings (musicEnabled, soundsEnabled) VALUES (?, ?);";
                PreparedStatement settingsInsertStatement = connection.prepareStatement(settingsInsertQuery);
                settingsInsertStatement.setBoolean(1, true);
                settingsInsertStatement.setBoolean(2, true);
                settingsInsertStatement.executeUpdate();
            }

            String usersCheckQuery = "SELECT COUNT(*) FROM users;";
            PreparedStatement usersCheckStatement = connection.prepareStatement(usersCheckQuery);
            ResultSet usersResultSet = usersCheckStatement.executeQuery();
            usersResultSet.next();

            if (usersResultSet.getInt(1) == 0) {
                String usersInsertQuery = "INSERT INTO users (name, lastSeen, lastScore, highscore) VALUES (?, ?, ?, ?);";
                PreparedStatement usersInsertStatement = connection.prepareStatement(usersInsertQuery);
                usersInsertStatement.setString(1, "Klara Zufall");
                usersInsertStatement.setLong(2, System.currentTimeMillis());
                usersInsertStatement.setInt(3, 0);
                usersInsertStatement.setInt(4, 0);
                usersInsertStatement.executeUpdate();
            }

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

    public static void updatePlayerName(String name) {
        try {
            String updateQuery = "INSERT INTO users (name, lastSeen, lastScore, highscore) VALUES (?, ?, ?, ?) ON CONFLICT (name) DO UPDATE SET lastSeen = ?, lastScore = ?;";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, name);
            statement.setLong(2, System.currentTimeMillis());
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setLong(5, System.currentTimeMillis());
            statement.setInt(6, 0);
            statement.executeUpdate();
            System.out.println("[Database.java] Spieler \"" + name + "\" erfolgreich aktualisiert.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayerLastSeen(String name, long lastSeen) {
        try {
            String updateQuery = "UPDATE users SET lastSeen = ? WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setLong(1, lastSeen);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("[Database.java] Spieler \"" + name + "\" erfolgreich gespeichert.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayerLastScore(String name, int lastScore) {
        try {
            String updateQuery = "UPDATE users SET lastScore = ? WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, lastScore);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("[Database.java] Score von Spieler \"" + name + "\" erfolgreich aktualisiert.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayerHighscore(String name, int highscore) {
        try {
            String updateQuery = "UPDATE users SET highscore = ? WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, highscore);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("[Database.java] Highscore von Spieler \"" + name + "\" erfolgreich aktualisiert.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long getPlayerLastSeen(String name) {
        long lastSeen = 0;

        try {
            String query = "SELECT lastSeen FROM users WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastSeen = resultSet.getLong("lastSeen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastSeen;
    }

    public static int getPlayerLastScore(String name) {
        int lastScore = 0;

        try {
            String query = "SELECT lastScore FROM users WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastScore = resultSet.getInt("lastScore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastScore;
    }

    public static int getPlayerHighscore(String name) {
        int highscore = 0;

        try {
            String query = "SELECT highscore FROM users WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                highscore = resultSet.getInt("highscore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return highscore;
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

    public static List<String> getAllScoresList() {
        List<String> scoreboard = new ArrayList<>();

        try {
            String query = "SELECT name, highscore, RANK() OVER (ORDER BY highscore DESC) as rank FROM users ORDER BY highscore DESC;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int rank = resultSet.getInt("rank");
                String name = resultSet.getString("name");
                int score = resultSet.getInt("highscore");
                scoreboard.add(rank + ". " + name + ": " + score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreboard;
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
