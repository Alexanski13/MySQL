package DB_Apps_Introduction_Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMinion {

    private static final String TOWN_GET_BY_NAME = "SELECT t.id FROM towns t WHERE t.name = ?";
    private static final String TOWN_INSERT = "INSERT INTO towns (name) VALUES (?)";
    private static final String TOWN_ADDED = "Town %s was added to the database.";
    private static final String VILLAIN_GET_BY_NAME = "SELECT v.id FROM villains v WHERE v.name = ?";
    private static final String VILLAIN_INSERT = "INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil')";
    private static final String VILLAIN_ADDED = "Villain %s was added to the database.";
    private static final String MINION_INSERT = "INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?)";
    private static final String MINION_GET_LAST = "SELECT m.id FROM minions m ORDER BY m.id DESC LIMIT 1";
    private static final String MINION_VILLAIN_INSERT = "INSERT INTO minions_villains (minion_id, villain_id) VALUES (?, ?)";
    private static final String MINION_ADDED = "Successfully added %s to be minion of %s";


    public static void main(String[] args) throws SQLException {
        final Scanner scanner = new Scanner(System.in);
        final Connection connection = Utils.getSQLConnection();

        final String[] minionInput = scanner.nextLine().split("\\s+");

        final String minionName = minionInput[1];
        final int minionAge = Integer.parseInt(minionInput[2]);
        final String minionTown = minionInput[3];

        final String villainName = scanner.nextLine().split("\\s+")[1];

        final int townId = getID(connection, minionTown, TOWN_GET_BY_NAME, TOWN_INSERT, TOWN_ADDED);
        final int villainId = getID(connection, villainName, VILLAIN_GET_BY_NAME, VILLAIN_INSERT, VILLAIN_ADDED);

        //Add minion
        final PreparedStatement addMinionStatement = connection.prepareStatement(MINION_INSERT);
        addMinionStatement.setString(1, minionName);
        addMinionStatement.setInt(2, minionAge);
        addMinionStatement.setInt(3, townId);
        addMinionStatement.executeUpdate();

        //Get minion ID
        final PreparedStatement getMinionId = connection.prepareStatement(MINION_GET_LAST);
        ResultSet minionIdRS = getMinionId.executeQuery();
        minionIdRS.next();
        int minionId = minionIdRS.getInt(Constants.COLUMN_LABEL_ID);

        //Add minion to villain
        final PreparedStatement addMinionToVillain = connection.prepareStatement(MINION_VILLAIN_INSERT);
        addMinionToVillain.setInt(1, minionId);
        addMinionToVillain.setInt(2, villainId);
        addMinionToVillain.executeUpdate();

        System.out.printf(MINION_ADDED, minionName, villainName);

        connection.close();
    }



    private static int getID(Connection connection, String argument, String selectQuery, String insertQuery, String printFormat) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, argument);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next()){
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, argument);
            insertStatement.executeUpdate();
            System.out.printf(printFormat, argument);
            System.out.println();
        }


        ResultSet newResultSet = statement.executeQuery();
        newResultSet.next();
        return newResultSet.getInt(Constants.COLUMN_LABEL_ID);
    }
}
