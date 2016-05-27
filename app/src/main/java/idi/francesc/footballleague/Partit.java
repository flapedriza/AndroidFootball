package idi.francesc.footballleague;

import java.util.Date;

/**
 * Created by Francesc Lapedriza.
 */
public class Partit {
    private int _ID;
    private Equip _Local;
    private Equip _Visitant;
    private Date _Data;

    public Partit(Equip _Local, Equip _Visitant, Date _Data) {
        this._Local = _Local;
        this._Visitant = _Visitant;
        this._Data = _Data;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public Equip get_Local() {
        return _Local;
    }

    public void set_Local(Equip _Local) {
        this._Local = _Local;
    }

    public Equip get_Visitant() {
        return _Visitant;
    }

    public void set_Visitant(Equip _Visitant) {
        this._Visitant = _Visitant;
    }

    public Date get_Data() {
        return _Data;
    }

    public void set_Data(Date _Data) {
        this._Data = _Data;
    }
}
