package co.edu.unal.jsonrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View content = inflater.inflate(R.layout.dialog_fragment, container, false);
        mTextView = content.findViewById(R.id.textView);
        String text = getArguments().getString("text");
        mTextView.setText(text);
        return content;
    }
    public void appendText(String text){
        mTextView.append(text);
    }
    public void cleanText(){
        mTextView.setText("");
    }
}
