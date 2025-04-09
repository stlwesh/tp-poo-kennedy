package dao;
import modele.Objet;
import java.util.ArrayList;
import java.util.List;
public class ObjetDao {
    private MyJDBC dbManager;

    public ObjetDao() {
        dbManager = new MyJDBC();


}


    public boolean marquerCommeVole(String id) {
        return dbManager.marquerCommeVole(id);
    }

    // Assurez-vous de fermer la connexion quand l'application se termine
    public void fermerConnexion() {
        dbManager.close();
    }
    private List<Objet> objets = new ArrayList<>();

    public void enregistrerObjet(Objet objet) {
        objets.add(objet);
    }

    public Objet chercherParId(String id) {
        for (Objet o : objets) {
            if (o.getId().equalsIgnoreCase(id)) {
                return o;
            }
        }
        return null;
    }

    public List<Objet> getObjetsVoles() {
        List<Objet> voles = new ArrayList<>();
        for (Objet o : objets) {
            if (o.getEst_Vole()) {
                voles.add(o);
            }
        }
        return voles;
    }}
