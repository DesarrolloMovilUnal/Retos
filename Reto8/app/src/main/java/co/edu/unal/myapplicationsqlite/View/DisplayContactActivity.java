package co.edu.unal.myapplicationsqlite.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;
import co.edu.unal.myapplicationsqlite.R;
import co.edu.unal.myapplicationsqlite.controller.DBHelper;
import co.edu.unal.myapplicationsqlite.viewModel.DisplayContactViewModel;

public class DisplayContactActivity extends AppCompatActivity implements ConfirmationDialogFragment.DialogListener {
    private TextView mEnterpriseTextView;
    private TextView mURLTextView;
    private TextView mPhoneNumberTextView;
    private TextView mEmailTextView;
    private TextView mProdServicesTextView;
    private TextView mClassificationTextView;
    private Button mEdit;
    private Button mDelete;
    private DisplayContactViewModel viewModel;
    private int idToSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        configView();
    }
    private void configView(){
        viewModel = new ViewModelProvider(this).get(DisplayContactViewModel.class);
        viewModel.setDB(new DBHelper(this));
        Bundle extras = getIntent().getExtras();
        idToSearch = extras.getInt(CreateEditContactActivity.ID);
        mEnterpriseTextView = findViewById(R.id.textViewName);
        mURLTextView = findViewById(R.id.URLTextView);
        mPhoneNumberTextView = findViewById(R.id.textViewPhoneNumber);
        mEmailTextView = findViewById(R.id.textViewEmail);
        mProdServicesTextView = findViewById(R.id.textViewProducts);
        mClassificationTextView = findViewById(R.id.textViewClass);
        mEdit = findViewById(R.id.buttonEdit);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEditContactActivity.class);
                intent.putExtra(CreateEditContactActivity.ID,idToSearch);
                intent.putExtra(CreateEditContactActivity.IS_NEW_DATA,false);
                startActivity(intent);
            }
        });
        mDelete = findViewById(R.id.buttonDelete);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
                confirmationDialogFragment.show(getSupportFragmentManager(),"Dialog");
            }
        });
        setView();
    }
    private void setView(){
        Cursor rs = viewModel.getDB().getData(idToSearch);
        rs.moveToFirst();
        String name = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_ENTERPRISE_NAME));
        String url = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_URL));
        String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_PHONE));
        String email = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_EMAIL));
        String prod = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_PROD_SERVICES));
        String classification = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_CLASSIFICATION));
        classification = ContactModel.classToString(ContactModel.Classification.valueOf(classification));
        mEnterpriseTextView.setText(name);
        mURLTextView.setText(url);
        mPhoneNumberTextView.setText(phone);
        mEmailTextView.setText(email);
        mProdServicesTextView.setText(prod);
        mClassificationTextView.setText(classification);
        if (!rs.isClosed())  {
            rs.close();
        }
    }

    @Override
    public void onGetAns(Boolean ans) {
        if (ans){
            viewModel.getDB().deleteContact(idToSearch);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}