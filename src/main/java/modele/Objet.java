package modele;
import java.io.Serializable;
import java.util.UUID;



    public class Objet implements Serializable {private String id;
    private String modele;
    private String nom_proprietere;
    private String type_appareil;
    private String marque;
    private boolean est_Vole;
    //constructeur
    public Objet(String id, String modele, String nom_proprietere, String type_appareil, String marque,boolean est_Vole){
        this.modele = modele;
        this.id = id;
        this.nom_proprietere = nom_proprietere;
        this.type_appareil = type_appareil;
        this.marque = marque;
        this.est_Vole = est_Vole;


    }
        private static final long serialVersionUID = 1L;
    //getters


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getModele() {
        return modele;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
    public String getNom_proprietere() {
        return nom_proprietere;
    }
    public void setNom_proprietere(String nom_proprietere) {
        this.nom_proprietere = nom_proprietere;

    }
    public String getType_appareil() {
        return type_appareil;
    }
    public void setType_appareil(String type_appareil) {
        this.type_appareil = type_appareil;

    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public boolean getEst_Vole() {
        return est_Vole;
    }
    public void setEst_Vole(boolean est_Vole) {
        this.est_Vole = est_Vole;
    }

}
