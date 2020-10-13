package co.edu.unal.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DifficultyDialogFragment extends DialogFragment {
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] difficultyArray = getActivity().getResources().getStringArray(R.array.difficulty_menu);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_difficulty)
                .setItems(difficultyArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                listener.option(TicTacToeGame.DifficultyLevel.Easy);
                                break;
                            case 1:
                                listener.option(TicTacToeGame.DifficultyLevel.Harder);
                                break;
                            case 2:
                                listener.option(TicTacToeGame.DifficultyLevel.Expert);
                                break;

                        }
                    }
                });
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void option(TicTacToeGame.DifficultyLevel option);
    }
}
