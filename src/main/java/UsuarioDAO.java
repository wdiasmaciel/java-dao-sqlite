import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    public void createTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connection = connectionFactory.createConnection();

            // Criar um statement baseado em uma string SQL:
            String createTableSQL = """
                      CREATE TABLE IF NOT EXISTS usuario (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome VARCHAR(256) NOT NULL,
                        nascimento TEXT
                      );
                    """;
            statement = connection.createStatement();
            statement.execute(createTableSQL);
            System.out.println("Tabela 'usuario' criada ou já existe!");
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Usuario create(Usuario usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String insertSQL = "INSERT INTO usuario (nome, nascimento) values (?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);

            // Preencher os valores no PreparedStatement:
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getNascimento());

            // Executar o comando SQL:
            preparedStatement.execute();
            usuario.setId(this.readLastInsertedId(connection));

            System.out.println(
                    "USUÁRIO GRAVADO NO BANCO DE DADOS: " +
                            "\nID: " + usuario.getId() +
                            "\nNOME: " + usuario.getNome() +
                            "\nDATA DE NASCIMENTO: " + usuario.getNascimento());
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public int readLastInsertedId(Connection connection) {
        int lastInsertedId = -1;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Criar um preparedStatement baseado em uma string SQL:
            String selectSQL = "SELECT MAX(id) AS max_id FROM usuario";
            preparedStatement = connection.prepareStatement(selectSQL);

            // Executar o comando SQL:
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt("max_id");
            } else {
                System.out.println("Não foi possível recuperar o último identificador gerado pelo banco de dados!");
            }
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastInsertedId;
    }

    public Usuario read(int id) {
        Usuario usuario = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String selectSQL = "SELECT * FROM usuario WHERE id = ?";
            preparedStatement = connection.prepareStatement(selectSQL);

            // Preencher o valor do identificador no PreparedStatement:
            preparedStatement.setInt(1, id);

            // Executar o comando SQL:
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setNascimento(resultSet.getString("nascimento"));
                System.out.println(
                        "USUÁRIO LIDO DO BANCO DE DADOS: " +
                                "\nID: " + usuario.getId() +
                                "\nNOME: " + usuario.getNome() +
                                "\nDATA DE NASCIMENTO: " + usuario.getNascimento());
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public void update(Usuario usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String updateSQL = "UPDATE usuario SET nome = ?, nascimento = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateSQL);

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
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Usuario usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Utilizar a fábrica de conexões para criar uma Connection SQL:
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connection = connectionFactory.createConnection();

            // Criar um preparedStatement baseado em uma string SQL:
            String deleteSQL = "DELETE FROM usuario WHERE id = ?";
            preparedStatement = connection.prepareStatement(deleteSQL);

            // Preencher os valores no PreparedStatement:
            preparedStatement.setInt(1, usuario.getId());

            // Executar o comando SQL:
            preparedStatement.execute();

            System.out.println("O usuario " + usuario.getNome() + " foi removido do BD.");
        } catch (SQLException e) {
            System.err.println("Erro na comunicação com o banco de dados!");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
