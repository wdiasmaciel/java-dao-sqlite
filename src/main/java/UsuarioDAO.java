import java.sql.SQLException;
import java.sql.Connection;

public class UsuarioDAO {
    public Usuario create(Usuario usuario) {
        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String insertSQL = "INSERT INTO usuario (nome, nascimnto) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Preencher os valores no PreparedStatement:
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getNascimento());

            // Executar o comando SQL:
            preparedStatement.execute();
            usuario.setId(readLastInsertedId(connection));

            System.out.println(
                    "\n USUÁRIO: " +
                            "\n ID: " + usuario.getID() +
                            "\n NOME:" + usuario.getNome() +
                            "\n DATA DE NASCIMENTO: " + usuario.getNascimento() +
                            "\n foi gravado no banco de dados.");
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }
        return usuario;
    }

    public int readLastInsertedId(Connection connection) {
        int lastInsertedId = -1;

        try {
            // Criar um preparedStatement baseado em uma string SQL:
            String selectSQL = "SELECT MAX(id) AS max_id FROM usuario";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // Executar o comando SQL:
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastInsertedId = resultSet.getString("max_id");
            } else {
                System.out.println("Não foi possível recuperar o último identificador gerado pelo banco de dados!");
            }
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return lastInsertedId;
    }

    public Usuario read(int id) {
        Usuario usuario = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String selectSQL = "SELECT * FROM aluno WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // Preencher o valor do identificador no PreparedStatement:
            preparedStatement.setInt(1, id);

            // Executar o comando SQL:
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setNome(resultSet.getString("nome"));
                usuario.setNascimento(resultSet.getString("nascimento"));
                System.out.println(
                        "\n USUÁRIO: " +
                                "\n ID: " + usuario.getID() +
                                "\n NOME:" + usuario.getNome() +
                                "\n DATA DE NASCIMENTO: " + usuario.getNascimento());
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return usuario;
    }

    public void update(Usuario usuario) {
        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String updateSQL = "UPDATE aluno SET nome = ?, nascimento = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            // Preencher os valores no PreparedStatement:
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getNascimento());
            preparedStatement.setInt(3, usuario.getId());

            // Executar o comando SQL:
            preparedStatement.executeUpdate();

            System.out.println("O usuário " + usuario.getNome() + " foi atualizado no banco de dados!");
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }
    }

    public void delete(Usuario usuario) {
        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String deleteSQL = "DELETE FROM aluno WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);

            // Preencher os valores no PreparedStatement:
            preparedStatement.setInt(1, usuario.getId());

            // Executar o comando SQL:
            preparedStatement.execute();

            System.out.println("O aluno " + a.getNome() + " foi removido do BD.");
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }
    }
}
