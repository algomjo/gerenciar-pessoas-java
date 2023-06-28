package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Pessoa;

public class BancoDados {
    private static final String URL = "jdbc:mysql://localhost:3306/dbgerenciarpessoas";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";
    
    public static void testarConexao(){
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)){
            System.out.println("Conexão bem sucedida com o banco de dados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
        public static void criarTabelas() {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sqlPessoas = "CREATE TABLE IF NOT EXISTS pessoas (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY," +
                                "nome VARCHAR(100) NOT NULL," +
                                "cpf VARCHAR(14) NOT NULL," +
                                "email VARCHAR(100) NOT NULL," +
                                "telefone VARCHAR(20) NOT NULL," +
                                "cep VARCHAR(9) NOT NULL," +
                                "logradouro VARCHAR(100) NOT NULL," +
                                "numero VARCHAR(10) NOT NULL," +
                                "complemento VARCHAR(100)," +
                                "bairro VARCHAR(100) NOT NULL," +
                                "cidade VARCHAR(100) NOT NULL," +
                                "uf VARCHAR(2) NOT NULL" +
                                ")";
            PreparedStatement statementPessoas = conn.prepareStatement(sqlPessoas);
            statementPessoas.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
     

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
