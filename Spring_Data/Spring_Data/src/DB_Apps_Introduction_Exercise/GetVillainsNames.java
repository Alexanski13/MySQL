package DB_Apps_Introduction_Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {

    private static final String GET_VILLAIN_NAMES = "SELECT v.name, count(distinct mv.minion_id) minions_count FROM villains v" +
            " join minions_villains mv on v.id = mv.villain_id" +
            " group by mv.villain_id" +
            " having minions_count > ?" +
            " order by minions_count";
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement  = connection.prepareStatement(GET_VILLAIN_NAMES);

        statement.setInt(1, 15);

        final ResultSet result = statement.executeQuery();

        while (result.next()) {
            final String villainName = result.getString(COLUMN_LABEL_NAME);
            final int minionsCount = result.getInt(COLUMN_LABEL_MINIONS_COUNT);

            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        }

        connection.close();
    }
}
