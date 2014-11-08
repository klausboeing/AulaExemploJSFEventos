/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.model;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContatoDao implements Closeable {

    private final Connection con;

    public ContatoDao() {
        this.con = new ConnectionFactory().getConnection();
    }

    @Override
    public void close() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleta(Contato contato) throws SQLException {
        String sql = "delete from agenda.contatos "
                + " where id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, contato.getId());

            // executa
            stmt.execute();
        }
    }

    public void edita(Contato contato) throws SQLException {
        String sql = "update agenda.contatos "
                + " set nome = ?, email = ?, endereco = ?, dataNascimento=? where id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            if (contato.getDataNascimento() != null) {
                stmt.setDate(4, new java.sql.Date(contato.getDataNascimento().getTimeInMillis()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setLong(5, contato.getId());

            // executa
            stmt.execute();
        }
    }

    public void adiciona(Contato contato) throws SQLException {
        String sql = "insert into agenda.contatos "
                + "(id,nome,email,endereco,dataNascimento)"
                + " values (?,?,?,?,?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            Long id = getProximoId();
            stmt.setLong(1, id);
            stmt.setString(2, contato.getNome());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getEndereco());
            if (contato.getDataNascimento() != null) {
                stmt.setDate(5, new java.sql.Date(contato.getDataNascimento().getTimeInMillis()));
            } else {
                stmt.setDate(5, null);
            }

            // executa
            stmt.execute();
        }
    }

    public Long getProximoId() throws SQLException {
        String sql = "select max(id) from agenda.contatos ";
        try (PreparedStatement st = con.prepareStatement(sql);
                ResultSet set = st.executeQuery()) {
            if (set.next()) {
                return set.getLong(1) + 1;
            }
        }
        return 1L;
    }

    public List<Contato> getLista() throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "select * from agenda.contatos";
        try (PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));

                if (rs.getDate("dataNascimento") != null) {
                    Calendar dataNascimento = Calendar.getInstance();
                    dataNascimento.setTime(rs.getDate("dataNascimento"));

                    contato.setDataNascimento(dataNascimento);
                }

                contatos.add(contato);
            }

            return contatos;
        }
    }

    public Contato buscarPorEmail(String email) throws SQLException {
        String sql = "select * from agenda.contatos "
                + " where email = ? ";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, email);
            Contato contato = null;

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    contato = new Contato();
                    contato.setId(resultSet.getLong("id"));
                    contato.setNome(resultSet.getString("nome"));
                    contato.setEmail(resultSet.getString("email"));
                    contato.setEndereco(resultSet.getString("endereco"));
                    if (resultSet.getDate("dataNascimento") != null) {
                        Calendar dataNascimento = Calendar.getInstance();
                        dataNascimento.setTime(resultSet.getDate("dataNascimento"));

                        contato.setDataNascimento(dataNascimento);
                    }
                }
            }
            return contato;
        }
    }

    public Contato buscarPorId(Long id) throws SQLException {
        String sql = "select * from agenda.contatos "
                + " where id = ? ";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setLong(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Contato contato = new Contato();
                    contato.setId(rs.getLong("id"));
                    contato.setNome(rs.getString("nome"));
                    contato.setEmail(rs.getString("email"));
                    contato.setEndereco(rs.getString("endereco"));

                    if (rs.getDate("dataNascimento") != null) {
                        Calendar dataNascimento = Calendar.getInstance();
                        dataNascimento.setTime(rs.getDate("dataNascimento"));

                        contato.setDataNascimento(dataNascimento);
                    }
                    return contato;
                }
            }
            return null;
        }
    }
}
