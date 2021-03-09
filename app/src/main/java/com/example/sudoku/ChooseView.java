package com.example.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ChooseView extends View {
    private Paint letter;
    private Paint line;
    private Paint lineVert;
    private Paint rect;
    private Rect rectSel;

    private int width, height;
    private float letterTrans;
    private float selectedPosition = -1;

    private static final String TAG = "ChooseView";

    private onNumberSelected listener;

    public ChooseView(Context context) {
        super(context);
        init(context);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ChooseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    public void init(Context context) {

        letter = new Paint();
        line = new Paint();
        lineVert = new Paint();
        rect = new Paint();

        letter.setColor(Color.BLACK);
        letter.setTextSize(width/11f);
        line.setColor(Color.BLACK);
        line.setStrokeWidth(width / 55f);
        lineVert.setColor(Color.BLACK);
        lineVert.setStrokeWidth(width/ 10f);
        rect.setColor(Color.GREEN);

        addNumberListener(listener);

        Log.d(TAG,"width: "+width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, 0, getWidth(), 0, line);
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),line);

     /*   canvas.drawLine(0,0,0,getHeight(),line);
        canvas.drawLine(getWidth(),getHeight(),getWidth(),0,line);  */

        for(int i = 0;i < 10;i++){
            canvas.drawLine(i*getWidth()/9f,0,i*getWidth()/9f,getHeight(),lineVert);
        }

        if (selectedPosition != -1){

            canvas.drawRect((selectedPosition*width/9)-width/9f,0,selectedPosition*width/9,height,rect);

            selectedPosition = -1;
        }

        for (int n = 0; n < 10; n++){
            canvas.drawText(String.valueOf(n+1),n*getWidth()/9+getWidth()/(9*4),getHeight()/2-letterTrans,letter);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (height != View.MeasureSpec.getSize(heightMeasureSpec) || width != View.MeasureSpec.getSize(widthMeasureSpec)) {
            height = View.MeasureSpec.getSize(heightMeasureSpec);
            width = View.MeasureSpec.getSize(widthMeasureSpec);

            letterTrans = (letter.ascent()+letter.descent())/2;

            line.setStrokeWidth(width/55f);
            letter.setTextSize(width/11f);

            lineVert.setColor(Color.BLACK);
            lineVert.setStrokeWidth(width/ 80f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float width9 = getWidth() / 9;
        int n = -1;

        for (int k = 1; k < 10; k++) {
            if (x >= (k - 1) * width9 && x <= k * width9) {
                n = k;
                break;
            }

        }
        if (listener != null) {
            listener.onNumberSelected(n);
        }


        return super.onTouchEvent(event);

    }

    public void addMark(int position){
        selectedPosition = position;
        invalidate();
    }

    public void addNumberListener(onNumberSelected listener){
        this.listener = listener;
    }



    public interface onNumberSelected {

        void onNumberSelected(int selectedNum);
    }
}
