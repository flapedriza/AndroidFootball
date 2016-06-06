package idi.francesc.footballleague;

/**
 * Created by Francesc Lapedriza.
 */
public class Jugador {
    private int _id;
    private String _nom;
    private String _cognoms;
    private String _equip;
    private int _gols;

    public Jugador() {

    }

    public Jugador(int _id, String _nom, String _cognoms, String _equip, int _gols) {
        this._id = _id;
        this._nom = _nom;
        this._cognoms = _cognoms;
        this._equip = _equip;
        this._gols = _gols;
    }

    public Jugador(String nom, String cognoms, String equip, int gols) {
        this._nom = nom;
        this._cognoms = cognoms;
        this._equip = equip;
        this._gols = gols;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    public String get_cognoms() {
        return _cognoms;
    }

    public void set_cognoms(String _cognoms) {
        this._cognoms = _cognoms;
    }

    public String get_equip() {
        return _equip;
    }

    public void set_equip(String _equip) {
        this._equip = _equip;
    }

    public int get_gols() {
        return _gols;
    }

    public void set_gols(int _gols) {
        this._gols = _gols;
    }
}
