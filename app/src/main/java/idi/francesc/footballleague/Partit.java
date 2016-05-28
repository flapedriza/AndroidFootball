package idi.francesc.footballleague;

import android.provider.Telephony;

import java.util.Date;

/**
 * Created by Francesc Lapedriza.
 */
public class Partit {
    private int _ID;
    private String _Local;
    private String _Visitant;
    private Date _Data;
    private int _GolsLocal;
    private int _GolsVisitant;

    public Partit() {}

    public Partit(String _Local, String _Visitant, Date _Data, int _GolsLocal, int _GolsVisitant) {
        this._Local = _Local;
        this._Visitant = _Visitant;
        this._Data = _Data;
        this._GolsLocal = _GolsLocal;
        this._GolsVisitant = _GolsVisitant;
    }

    public Partit(int _ID, String _Local, String _Visitant, Date _Data, int _GolsLocal, int _GolsVisitant) {
        this._ID = _ID;
        this._Local = _Local;
        this._Visitant = _Visitant;
        this._Data = _Data;
        this._GolsLocal = _GolsLocal;
        this._GolsVisitant = _GolsVisitant;
    }

    public Partit (Partit partit) {
        this(partit.get_Local(), partit.get_Visitant(), partit.get_Data(), partit._GolsLocal, partit._GolsVisitant);
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String get_Local() {
        return _Local;
    }

    public void set_Local(String _Local) {
        this._Local = _Local;
    }

    public String get_Visitant() {
        return _Visitant;
    }

    public void set_Visitant(String _Visitant) {
        this._Visitant = _Visitant;
    }

    public Date get_Data() {
        return _Data;
    }

    public void set_Data(Date _Data) {
        this._Data = _Data;
    }

    public int get_GolsLocal() {
        return _GolsLocal;
    }

    public void set_GolsLocal(int _GolsLocal) {
        this._GolsLocal = _GolsLocal;
    }

    public int get_GolsVisitant() {
        return _GolsVisitant;
    }

    public void set_GolsVisitant(int _GolsVisitant) {
        this._GolsVisitant = _GolsVisitant;
    }
}
