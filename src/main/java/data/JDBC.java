package data;

import java.sql.*;
import java.util.ArrayList;

public class JDBC {

    public void connectMYSQL() {

        try {
            //1.Get connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/greenshoe?useSSL=false", "root", "permmy6480");

            //2.Create a statement
            Statement statement = connection.createStatement();

            //3.Execute SQL query
            statement.executeUpdate("INSERT INTO characters(character1,character2,character3,character4,character5,character6,character7,character8,character9,character10,character11,character12)" +
                    " VALUES ('A','G','C','T','A','G','C','T','A','G','C','T')");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM characters");

            //4.Process the result
            while (resultSet.next()) {
                String letters = resultSet.getString("character1") + resultSet.getString("character2") + resultSet.getString("character3") + resultSet.getString("character4")
                        + resultSet.getString("character5") + resultSet.getString("character6") + resultSet.getString("character7") + resultSet.getString("character8")
                        + resultSet.getString("character9") + resultSet.getString("character10") + resultSet.getString("character11") + resultSet.getString("character12");

                ArrayList<String> arrList=new ArrayList<String>();
                arrList.add(resultSet.getString("character1"));
                arrList.add(resultSet.getString("character2"));
                arrList.add(resultSet.getString("character3"));
                arrList.add(resultSet.getString("character4"));
                arrList.add(resultSet.getString("character5"));
                arrList.add(resultSet.getString("character6"));
                arrList.add(resultSet.getString("character7"));
                arrList.add(resultSet.getString("character8"));
                arrList.add(resultSet.getString("character9"));
                arrList.add(resultSet.getString("character10"));
                arrList.add(resultSet.getString("character11"));
                arrList.add(resultSet.getString("character12"));

                ReadCSV readCSV=new ReadCSV();
                readCSV.createCSV(arrList);
                connection.close();

            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    void writeJDBC(Character character1, Character character2, Character character3, Character character4, Character character5,
                   Character character6, Character character7, Character character8, Character character9,
                   Character character10, Character character11, Character character12) {
        PreparedStatement preparedStatement = null;
        try {
            //1.Get connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/greenshoe?useSSL=false", "root", "permmy6480");

            //2.Create a statement
            Statement statement = connection.createStatement();

            preparedStatement = connection.prepareStatement("INSERT INTO processed (character1,character2,character3,character4,character5,character6,character7,character8,character9,character10,character11,character12)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, String.valueOf(character1));
            preparedStatement.setString(2, String.valueOf(character2));
            preparedStatement.setString(3, String.valueOf(character3));
            preparedStatement.setString(4, String.valueOf(character4));
            preparedStatement.setString(5, String.valueOf(character5));
            preparedStatement.setString(6, String.valueOf(character6));
            preparedStatement.setString(7, String.valueOf(character7));
            preparedStatement.setString(8, String.valueOf(character8));
            preparedStatement.setString(9, String.valueOf(character9));
            preparedStatement.setString(10, String.valueOf(character10));
            preparedStatement.setString(11, String.valueOf(character11));
            preparedStatement.setString(12, String.valueOf(character12));

            preparedStatement.execute();

            connection.close();


        } catch (SQLException e) {
            e.getMessage();
        }

    }
}
