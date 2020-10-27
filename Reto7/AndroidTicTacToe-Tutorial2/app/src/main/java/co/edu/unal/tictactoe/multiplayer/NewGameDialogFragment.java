package co.edu.unal.tictactoe.multiplayer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import co.edu.unal.tictactoe.R;

public class NewGameDialogFragment extends AppCompatDialogFragment {
    private EditText mEditText;
    private DialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_new_game_dialog_fragment,null);

        builder.setView(view)
                .setTitle(R.string.new_game)
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton(R.string.create_game, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = mEditText.getText().toString();
                listener.onGetCodeText(code);
            }
        });
        mEditText = view.findViewById(R.id.editTextNewGameCode);

        return builder.create();
    }
    public interface DialogListener{
        void onGetCodeText(String code);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString());
        }
    }
}