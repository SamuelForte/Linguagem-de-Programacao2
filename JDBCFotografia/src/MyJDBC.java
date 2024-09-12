import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class MyJDBC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            // Conexão com o banco de dados MySQL
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbcfotografia",
                    "root",
                    "root"
            );

            int choice = 0;
            while (choice != 5) {
                // Exibe o menu interativo
                System.out.println("\nEscolha uma operação:");
                System.out.println("1 - Inserir nova pessoa");
                System.out.println("2 - Listar todas as pessoas");
                System.out.println("3 - Atualizar uma pessoa");
                System.out.println("4 - Excluir uma pessoa");
                System.out.println("5 - Sair");
                System.out.print("Digite sua escolha: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer de entrada

                switch (choice) {
                    case 1:
                        // Inserir nova pessoa (CREATE)
                        System.out.print("Digite o primeiro nome: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Digite o sobrenome: ");
                        String lastName = scanner.nextLine();
                        createPerson(connection, firstName, lastName);
                        break;
                    case 2:
                        // Listar todas as pessoas (READ)
                        readPeople(connection);
                        break;
                    case 3:
                        // Atualizar uma pessoa (UPDATE)
                        System.out.print("Digite o ID da pessoa a ser atualizada: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        System.out.print("Digite o novo primeiro nome: ");
                        String newFirstName = scanner.nextLine();
                        System.out.print("Digite o novo sobrenome: ");
                        String newLastName = scanner.nextLine();
                        updatePerson(connection, updateId, newFirstName, newLastName);
                        break;
                    case 4:
                        // Excluir uma pessoa (DELETE)
                        System.out.print("Digite o ID da pessoa a ser excluída: ");
                        int deleteId = scanner.nextInt();
                        deletePerson(connection, deleteId);
                        break;
                    case 5:
                        // Sair
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    // Função para criar uma nova pessoa (CREATE)
    public static void createPerson(Connection connection, String firstName, String lastName) {
        PreparedStatement preparedStatement = null;
        try {
            String insertSQL = "INSERT INTO people (firstname, lastname) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserção completada: " + rowsAffected + " linha(s) afetada(s).");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Função para ler os registros do banco de dados (READ)
    public static void readPeople(Connection connection) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM people");

            System.out.println("\nListando pessoas:");
            while (resultSet.next()) {
                System.out.println(
                        "ID: " + resultSet.getInt("id") +
                                ", Nome: " + resultSet.getString("firstname") +
                                ", Sobrenome: " + resultSet.getString("lastname")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Função para atualizar o nome de uma pessoa (UPDATE)
    public static void updatePerson(Connection connection, int id, String newFirstName, String newLastName) {
        PreparedStatement preparedStatement = null;
        try {
            String updateSQL = "UPDATE people SET firstname = ?, lastname = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, newLastName);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Atualização completada: " + rowsAffected + " linha(s) afetada(s).");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Função para deletar uma pessoa (DELETE)
    public static void deletePerson(Connection connection, int id) {
        PreparedStatement preparedStatement = null;
        try {
            String deleteSQL = "DELETE FROM people WHERE id = ?";
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Exclusão completada: " + rowsAffected + " linha(s) afetada(s).");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
