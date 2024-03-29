package idi.francesc.footballleague;

/**
 * Created by Francesc Lapedriza
 */
public class Equip {
    private int _id;
    private String _nom;
    private byte[] _escut;
    private int _gfavor;
    private int _gcontra;
    private int _victories;
    private int _derrotes;
    private int _empats;
    private int _punts;
    private String _ciutat;

    public Equip() {
        this._id = -1;
    }

    public Equip(int _id, String _nom, String ciutat, byte[] _escut, int _gfavor, int _gcontra, int _victories, int _derrotes, int _empats) {
        this._id = _id;
        this._nom = _nom;
        this._ciutat = ciutat;
        this._escut = _escut;
        this._gfavor = _gfavor;
        this._gcontra = _gcontra;
        this._victories = _victories;
        this._derrotes = _derrotes;
        this._empats = _empats;
        this._punts = (_victories*3) + (_empats);
    }

    public Equip(String nom, String ciutat) {
        byte[] es = {0};
        this._nom = nom;
        this._ciutat = ciutat;
        this._escut = es;
        this._gfavor = 0;
        this._gcontra = 0;
        this._victories = 0;
        this._derrotes = 0;
        this._empats = 0;
        this._punts = 0;
    }

    public Equip(String nom, String _ciutat, byte[] escut, int gfavor, int gcontra, int victories, int derrotes, int empats) {
        this._id = -2;
        this._nom = nom;
        this._ciutat = _ciutat;
        this._escut = escut;
        this._gfavor = gfavor;
        this._gcontra = gcontra;
        this._victories = victories;
        this._derrotes = derrotes;
        this._empats = empats;
        this._punts = (victories*3) + (empats);
    }

    @Override
    public String toString() {
        return "Nom: " + _nom + " Ciutat: " + _ciutat + " \nPunts: " + _punts;
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

    public String get_ciutat() {
        return _ciutat;
    }

    public void set_ciutat(String _ciutat) {
        this._ciutat = _ciutat;
    }

    public byte[] get_escut() {
        return _escut;
    }

    public void set_escut(byte[] _escut) {
        this._escut = _escut;
    }

    public int get_gfavor() {
        return _gfavor;
    }

    public void set_gfavor(int _gfavor) {
        this._gfavor = _gfavor;
    }

    public int get_gcontra() {
        return _gcontra;
    }

    public void set_gcontra(int _gcontra) {
        this._gcontra = _gcontra;
    }

    public int get_victories() {
        return _victories;
    }

    public void set_victories(int _victories) {
        this._victories = _victories;
    }

    public int get_derrotes() {
        return _derrotes;
    }

    public void set_derrotes(int _derrotes) {
        this._derrotes = _derrotes;
    }

    public int get_empats() {
        return _empats;
    }

    public void set_empats(int _empats) {
        this._empats = _empats;

    }

    public int get_punts() {
        return _punts;
    }

    public void set_punts(int _punts) {
        this._punts = _punts;
    }

    public void incrementGolsFavor(int gols) {
        this._gfavor += gols;
    }

    public void incrementGolsContra(int gols) {
        this._gcontra += gols;
    }

    public void addVictoria() {
        this._victories += 1;
        this._punts += 3;
    }

    public void addEmpat() {
        this._punts += 1;
        this._empats += 1;
    }

    public void addDerrota() {
        this._derrotes += 1;
    }
}
