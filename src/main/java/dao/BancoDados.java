package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Pessoa;

public class BancoDados {
    private static final String URL = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";

    public static void salvarPessoa(Pessoa pessoa) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sql = "INSERT INTO pessoas (nome, cpf, email, telefone, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getCpf());
            statement.setString(3, pessoa.getEmail());
            statement.setString(4, pessoa.getTelefone());
            statement.setString(5, pessoa.getEndereco().getCep());
            statement.setString(6, pessoa.getEndereco().getLogradouro());
            statement.setString(7, pessoa.getEndereco().getNumero());
            statement.setString(8, pessoa.getEndereco().getComplemento());
            statement.setString(9, pessoa.getEndereco().getBairro());
            statement.setString(10, pessoa.getEndereco().getCidade());
            statement.setString(11, pessoa.getEndereco().getUf());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
