package com.example.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class SudokuView extends View{

    private static final String TAG = "SudokuView";

    private Paint smallLinePaint;
    private Paint bigLinePaint;
    private Paint letter1;
    int rowColCount = 9;
    int height,width;
    float letterTrans;
    int[][] matrix;

    public SudokuView(Context context) {
        super(context);
        init(context);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        smallLinePaint = new Paint();
        bigLinePaint = new Paint();
        smallLinePaint.setColor(Color.BLACK);
        smallLinePaint.setStrokeWidth(width/310f);
        bigLinePaint.setColor(Color.BLACK);
        bigLinePaint.setStrokeWidth(width/180f);


        letter1 = new Paint();
        letter1.setColor(Color.BLACK);
        letter1.setTextSize(width/8f);
        letter1.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int c = 1;c<rowColCount;c++){
            canvas.drawLine((c*getWidth())/rowColCount,0,(c*getWidth())/rowColCount,getHeight(),smallLinePaint);
        }
        for (int r = 1;r<rowColCount;r++){
            Log.d(TAG," "+(r*getHeight())/rowColCount+" , "+getHeight());
            canvas.drawLine(0,(r*getHeight())/rowColCount, getWidth(),(r*getHeight())/rowColCount,smallLinePaint);
        }

        if (matrix != null){
            for(int r = 0;r < rowColCount; r++){
                for(int c = 0; c < rowColCount; c++){
                    if (matrix[r][c] != -1){
                        canvas.drawText(String.valueOf(matrix[r][c]),width/(2*rowColCount)+(c*(width/rowColCount)),
                                height/(2*rowColCount)+(r*(height/rowColCount))-letterTrans,letter1);
                    }
                }
            }}

        //      canvas.drawLine(getWidth()/2f,0f,getWidth()/2f,getHeight(),bigLinePaint);
        //    canvas.drawLine(0f,height/2,width,width/2,bigLinePaint);

        canvas.drawLine(6*(width/rowColCount),0,6*(width/rowColCount),height,bigLinePaint);
        canvas.drawLine(3*(width/rowColCount),0,3*(width/rowColCount),height,bigLinePaint);

        canvas.drawLine(0,6*(width/rowColCount),width,6*(width/rowColCount),bigLinePaint);
        canvas.drawLine(0,3*(width/rowColCount),width,3*(width/rowColCount),bigLinePaint);

    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        invalidate();
        //  requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(height != View.MeasureSpec.getSize(heightMeasureSpec) || width != View.MeasureSpec.getSize(widthMeasureSpec)) {
            Log.d(TAG, "Width: " + View.MeasureSpec.getSize(widthMeasureSpec));
            height = View.MeasureSpec.getSize(heightMeasureSpec);
            width = View.MeasureSpec.getSize(widthMeasureSpec);

            smallLinePaint.setStrokeWidth(width/350f);
            bigLinePaint.setStrokeWidth(width/180f);

            letter1.setColor(Color.BLACK);
            letter1.setTextSize(width/10f);
            letter1.setTextAlign(Paint.Align.CENTER);


            letterTrans = (letter1.ascent()+letter1.descent())/2;

            Log.d(TAG, "WidthA: " + width);
            Log.d("SUUUDOKU","widthsmall "+smallLinePaint.getStrokeWidth()+" , "+getMeasuredWidth());
            Log.d(TAG,"Width Big: "+bigLinePaint.getStrokeWidth());
            Log.d(TAG,"Width: "+width);
            Log.d(TAG,"Width1: "+getWidth());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        SudokuActivity.sudokuViewTouchEvent(x,y,getContext());
        Log.d(TAG,"x: "+x+" y: "+y);
        return super.onTouchEvent(event);
    }
}
