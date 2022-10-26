package DB_Apps_Introduction_Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private static final String GET_VILLAIN_BY_ID = "select v.name from villains v where id = ?";
    private static final String NO_SUCH_VILLAIN_MESSAGE = "No such villain was found";
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String GET_MINIONS_COUNT_BY_VILLAIN_ID = "select count(distinct mv.minion_id) m_count from minions_villains mv where mv.villain_id = ?";
    private static final String COLUMN_LABEL_MINION_COUNT = "m_count";
    private static final String DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID = "delete from  minions_villains mv where mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID = "delete from villains v where v.id = ?";
    private static final String DELETED_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETED_COUNT_OF_MINIONS_FORMAT = "%d minions released%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement selectedVillain = connection.prepareStatement(GET_VILLAIN_BY_ID);
        selectedVillain.setInt(1, villainId);

        final ResultSet villainSet = selectedVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println(NO_SUCH_VILLAIN_MESSAGE);
            connection.close();
            return;
        }

        final String villainName = villainSet.getString(COLUMN_LABEL_NAME);

        PreparedStatement selectAllMinions = connection.prepareStatement(GET_MINIONS_COUNT_BY_VILLAIN_ID);
        selectAllMinions.setInt(1, villainId);
        ResultSet countOfMinionsSet = selectAllMinions.executeQuery();
        countOfMinionsSet.next();

        final int countOfDeletedMinions = countOfMinionsSet.getInt(COLUMN_LABEL_MINION_COUNT);

        connection.setAutoCommit(false);

        try (PreparedStatement deleteMinionStatement = connection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID);
             PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_VILLAIN_BY_ID)) {
            deleteMinionStatement.setInt(1, villainId);
            deleteMinionStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            connection.commit();
            System.out.printf(DELETED_VILLAIN_FORMAT, villainName);
            System.out.printf(DELETED_COUNT_OF_MINIONS_FORMAT, countOfDeletedMinions);

        } catch (SQLException e) {
            e.printStackTrace();

            connection.rollback();
        }

        connection.close();
    }
}
