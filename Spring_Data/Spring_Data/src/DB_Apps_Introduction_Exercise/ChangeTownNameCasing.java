package DB_Apps_Introduction_Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeTownNameCasing {
    private static final String UPDATE_TOWN_NAME = "update towns as t set name = upper(name) where t.country = ?";
    private static final String GET_ALL_TOWN_NAMES_BY_COUNTRY_NAME = "select t.name FROM towns as t where t.country = ?";
    private static final String NO_TOWN_AFFECTED_MESSAGE = "No town names were affected";
    private static final String COUNT_OF_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";
    private static final String COLUMN_LABEL_NAME = "name";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final String townName = new Scanner(System.in).nextLine();

        final PreparedStatement statement = connection.prepareStatement(UPDATE_TOWN_NAME);
        statement.setString(1, townName);

        final int updatedCount = statement.executeUpdate();

        if (updatedCount == 0) {
            System.out.println(NO_TOWN_AFFECTED_MESSAGE);
            connection.close();
            return;
        }

        System.out.printf(COUNT_OF_AFFECTED_TOWNS_FORMAT, updatedCount);

        final PreparedStatement selectAllTowns = connection.prepareStatement(GET_ALL_TOWN_NAMES_BY_COUNTRY_NAME);
        selectAllTowns.setString(1, townName);

        final ResultSet allTownsResult = selectAllTowns.executeQuery();
        ArrayList<String> towns = new ArrayList<>();

        while (allTownsResult.next()) {
            towns.add(allTownsResult.getString(COLUMN_LABEL_NAME));
        }
        System.out.println(towns);
    }
}
