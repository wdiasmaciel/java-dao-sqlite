public class Main {
    public static void main(String[] args) {
        // Criar o DAO para conexão com o banco de dados:
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Criar um usuario:
        Usuario usuario = new Usuario();
        usuario.setNome("Ana");
        usuario.setNascimento("2000-06-07");

        // Salvar o usuario no banco de dados:
        usuario = usuarioDAO.create(usuario);

        // Ler as informações cadastradas no banco de dados:
        alunoDAO.read(usuario.getId());

        // Atualizar as informações do usuario:
        usuario.setNome("Ana Silva"); 
        usuarioDAO.update(usuario); 

        // Ler as informações atualizadas no banco de dados:
        usuarioDAO.read(usuario.getId());

        // Remover o usário:
        usuarioDAO.delete(usuario);

        // Verificar se as informações foram mesmo removidas:
        usuarioDAO.read(usuario.getId());
    }
}