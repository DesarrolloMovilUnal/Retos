package co.edu.unal.myapplicationsqlite.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;
import co.edu.unal.myapplicationsqlite.R;
import co.edu.unal.myapplicationsqlite.controller.DBHelper;
import co.edu.unal.myapplicationsqlite.viewModel.CreateEditViewModel;

public class CreateEditContactActivity extends AppCompatActivity{
    private EditText mEnterpriseEditText;
    private EditText mURLEditText;
    private EditText mPhoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mProdServicesEditText;
    private RadioGroup mRadioGroup;
    private Button saveButton;
    private RadioButton mRadioButton;
    private CreateEditViewModel viewModel;
    private int idToUpdate;
    private boolean isNewData;
    public static String IS_NEW_DATA ="newData";
    public static String ID ="id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_contact);
        configView();
    }

    public void configView() {
        Bundle extras = getIntent().getExtras();
        viewModel = new ViewModelProvider(this).get(CreateEditViewModel.class);
        viewModel.setDB(new DBHelper(this));
        mEmailEditText = findViewById(R.id.emailEditText);
        mEnterpriseEditText = findViewById(R.id.enterpriseNameEditText);
        mURLEditText = findViewById(R.id.urlEditText);
        mPhoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        mProdServicesEditText = findViewById(R.id.productsServicesEditText);
        mRadioGroup = findViewById(R.id.radioGroup);
        saveButton = findViewById(R.id.saveButton);

        if (extras != null){
             isNewData = extras.getBoolean(IS_NEW_DATA);
            if (!isNewData){
                //edit
                int value = extras.getInt(ID);
                Cursor rs = viewModel.getData(value);
                idToUpdate = value;
                rs.moveToFirst();
                String name = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_ENTERPRISE_NAME));
                String url = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_URL));
                String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_PHONE));
                String email = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_EMAIL));
                String prod = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_PROD_SERVICES));
                String classification = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_CLASSIFICATION));
                classification = ContactModel.classToString(ContactModel.Classification.valueOf(classification));
                setView(name,url,phone,email,prod,classification);
                if (!rs.isClosed())  {
                    rs.close();
                }
            }
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewData){
                    save();
                }else {
                    save(idToUpdate);
                }
            }
        });
    }

    private void setView(String name, String url, String phone, String email, String prod, String classification){
        mEnterpriseEditText.setText(name);
        mURLEditText.setText(url);
        mPhoneNumberEditText.setText(phone);
        mEmailEditText.setText(email);
        mProdServicesEditText.setText(prod);
        activeRadioButton(classification);
    }
    private void getInfo(){
        viewModel.setContactModel(new ContactModel());
        viewModel.getContactModel().setEnterprise(getText(mEnterpriseEditText));
        viewModel.getContactModel().setURL(getText(mURLEditText));
        viewModel.getContactModel().setPhoneNumber(getText(mPhoneNumberEditText));
        viewModel.getContactModel().setEmail(getText(mEmailEditText));
        viewModel.getContactModel().setProductsNServices(getText(mProdServicesEditText));
        viewModel.getContactModel().setClassification(ContactModel.stringToClass(getRadioButtonText()));
    }
    private void save() {
        getInfo();
        viewModel.save();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    private void save(int id){
        getInfo();
        viewModel.save(id);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public String getText(EditText text) {
        return text.getText().toString();
    }

    public String getRadioButtonText() {
        int radioId = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = findViewById(radioId);
        return mRadioButton.getText().toString();
    }
    public void activeRadioButton(String classification){
        RadioButton aux1F = findViewById(R.id.radioButtonFactory);
        RadioButton aux2Dev = findViewById(R.id.radioButtonDev);
        RadioButton aux3C = findViewById(R.id.radioButtonConsultancy);
        if (classification.equals(aux1F.getText())){
            aux1F.setSelected(true);
        }else if(classification.equals(aux2Dev)){
            aux2Dev.setSelected(true);
        }else {
            aux3C.setSelected(true);
        }
    }

}