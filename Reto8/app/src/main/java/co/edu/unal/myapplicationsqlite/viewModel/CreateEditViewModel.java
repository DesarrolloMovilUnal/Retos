package co.edu.unal.myapplicationsqlite.viewModel;

import android.database.Cursor;

import androidx.lifecycle.ViewModel;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;
import co.edu.unal.myapplicationsqlite.controller.DBHelper;

public class CreateEditViewModel extends ViewModel {
    private ContactModel contactModel;
    private DBHelper mDB;

    public DBHelper getDB() {
        return mDB;
    }

    public void setDB(DBHelper mDB) {
        this.mDB = mDB;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    public ContactModel getContactModel() {
        return contactModel;
    }

    public void save() {
        mDB.insertContact(contactModel);
    }
    public void save(int id) {
        mDB.updateContact(id,contactModel);
    }

    public Cursor getData(int value) {
        return mDB.getData(value);
    }
}
