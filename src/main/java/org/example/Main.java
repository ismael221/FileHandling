package org.example;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String JBDC_DRIVER = "com.mysql.jdbc.Driver";
    static final  String DB_URL = "jdbc:mysql://localhost:3306/files";
    static final  String USER = "root";
    static final  String PASS = "ismael";


    public static void main(String[] args) throws ClassNotFoundException, SQLException,FileNotFoundException {

        Connection conn = null;
        Statement stmt = null;

        try{

            FileInputStream obj = new FileInputStream("C:/Users/Usuario/Downloads/MOCK_DATA.csv");

            InputStreamReader input = new InputStreamReader(obj);

            BufferedReader br = new BufferedReader(input);

            String linha;

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to the database ...");

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Connected to the database successfully ...");

            do {
                linha = br.readLine();
                if (linha != null){

                    String[] campo = linha.split(",");
                    try {

                       // int id = Integer.parseInt(campo[0]);
                        String first_name = campo[1];
                        String last_name = campo[2];
                        String email = campo[3];
                        String gender = campo[4];
                        String ip_address= campo[5];

                        stmt = conn.createStatement();

                        String sql = "INSERT INTO pessoas ( first_name, last_name, email, gender, ip_address) VALUES ( ?, ?, ?, ?, ?)";

                        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                            //preparedStatement.setInt(1, id);
                            preparedStatement.setString(1, first_name);
                            preparedStatement.setString(2, last_name);
                            preparedStatement.setString(3, email);
                            preparedStatement.setString(4, gender);
                            preparedStatement.setString(5, ip_address);

                            preparedStatement.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        stmt.close();

                    }catch (NumberFormatException e){
                        e.getMessage();
                    }


                }
            }while (linha !=null);

        }catch (FileNotFoundException ex){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}