package co.edu.unal.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {
    public static final int GRID_WIDTH = 6;
    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;
    private Paint mPaint;
    private TicTacToeGame mGame;

    public void setGame(TicTacToeGame mGame) {
        this.mGame = mGame;
    }

    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mHumanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.x_img);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_img);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //lines
        int boardW = getWidth();
        int boardH = getHeight();

        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_WIDTH);

        int cellWidth = boardW / 3;
        int cellHeight = boardH/ 3;
        canvas.drawLine(cellWidth, 0, cellWidth, boardH, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardH, mPaint);
        canvas.drawLine(0, cellHeight, boardW, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardW, cellHeight * 2, mPaint);
        //x~o
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            int col = i % 3;
            int row = i / 3;
            int left = col*cellWidth;;
            int top = row*cellWidth;
            int right = left+cellHeight;
            int bottom = top+cellHeight;
            if(mGame != null){

                if (mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER) {
                    canvas.drawBitmap(mHumanBitmap, null, new Rect(left,top,right,bottom),null);
                }else if (mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER){
                    canvas.drawBitmap(mComputerBitmap, null, new Rect(left,top,right,bottom),null);
                }
            }

        }
    }

    public int getBoardCellWidth(){
        return getWidth() / 3;
    }
    public int getBoardCellHeight(){
        return getHeight() / 3;
    }
}
