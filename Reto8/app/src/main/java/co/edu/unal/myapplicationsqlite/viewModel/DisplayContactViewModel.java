package co.edu.unal.myapplicationsqlite.viewModel;

import androidx.lifecycle.ViewModel;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;
import co.edu.unal.myapplicationsqlite.controller.DBHelper;

public class DisplayContactViewModel extends ViewModel {
    private ContactModel contactModel;
    private DBHelper mDB;

    public ContactModel getContactModel() {
        return contactModel;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    public DBHelper getDB() {
        return mDB;
    }

    public void setDB(DBHelper mDB) {
        this.mDB = mDB;
    }
}
