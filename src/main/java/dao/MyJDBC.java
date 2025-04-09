package dao;
import modele.Objet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyJDBC {

    Connection connection;

    {
        try {
            // Load the driver first
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Then establish connection
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema", "root", "stellawesh");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }
    public boolean ajouterObjet(Objet objet) {
        String sql = "INSERT INTO objets (id, type_appareil, marque, modele, nom_proprietaire, est_vole) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, objet.getId());
            pstmt.setString(2, objet.getType_appareil());
            pstmt.setString(3, objet.getMarque());
            pstmt.setString(4, objet.getModele());
            pstmt.setString(5, objet.getNom_proprietere());
            pstmt.setInt(6, objet.getEst_Vole() ? 1 : 0);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'objet: " + e.getMessage());
            return false;
        }
    }

    // Rechercher un objet par ID
    public Objet rechercherParId(String id) {
        String sql = "SELECT * FROM objets WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return convertirResultatEnObjet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'objet: " + e.getMessage());
        }

        return null;
    }

    // Obtenir tous les objets volés
    public List<Objet> getObjetsVoles() {
        List<Objet> objetsVoles = new ArrayList<>();
        String sql = "SELECT * FROM objets WHERE est_vole = 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                objetsVoles.add(convertirResultatEnObjet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des objets volés: " + e.getMessage());
        }

        return objetsVoles;
    }

    // Marquer un objet comme volé
    public boolean marquerCommeVole(String id) {
        String sql = "UPDATE objets SET est_vole = 1 WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut de l'objet: " + e.getMessage());
            return false;
        }
    }

    // Méthode utilitaire pour convertir un ResultSet en objet Objet
    private Objet convertirResultatEnObjet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String type_appareil= rs.getString("type_appareil");
        String marque = rs.getString("marque");
        String modele = rs.getString("modele");
        String proprietere = rs.getString("nom_proprietaire");
        boolean estVole = rs.getInt("est_vole") == 1;

        Objet objet = new Objet(id ,modele, proprietere, type_appareil, marque,estVole);
        objet.setId(id);
        objet.setEst_Vole(estVole);

        return objet;
    }







}
