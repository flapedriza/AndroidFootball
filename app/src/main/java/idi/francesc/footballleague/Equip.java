package idi.francesc.footballleague;

import android.media.Image;

/**
 * Created by franc on 23/05/2016.
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

    public Equip() {

    }

    public Equip(String nom, byte[] escut, int gfavor, int gcontra, int victories, int derrotes, int empats) {
        this._nom = nom;
        this._escut = escut;
        this._gfavor = gfavor;
        this._gcontra = gcontra;
        this._victories = victories;
        this._derrotes = derrotes;
        this._empats = empats;
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
}
