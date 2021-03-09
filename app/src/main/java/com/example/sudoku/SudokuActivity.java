package com.example.sudoku;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SudokuActivity extends AppCompatActivity implements ChooseView.onNumberSelected {



    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "Sudoku";


    static int numberSelected = -1;

    private static int mistakes = 0;

    private static final int EASY_MODE = 40;
    private static final int MEDIUM_MODE = 60;
    private static final int HARD_MODE = 70;

    private static SudokuView sudokuView;
    private static ChooseView chooseView;
    private static RelativeLayout sudokuLayout;

    static TextView mistakesView ;

    private static boolean gameStarted = false;
    private static boolean numSel = false;



    static int[][] sudokuArr;
    static int[][] solArr;
    static ArrayList<Integer> compArr;

    private static int rowColCount = 9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        sudokuArr = new int[9][9];
        compArr = new ArrayList<>();
        solArr = new int[9][9];
        fillCompArr();
        sudokuView = findViewById(R.id.sudoku_view);
        chooseView = findViewById(R.id.choose_view);
        mistakesView = findViewById(R.id.mistakes_view);
        sudokuLayout = findViewById(R.id.sudoku_layout);

        chooseView.addNumberListener(this);

        for (int n = 0; n< rowColCount; n++){
            for (int m = 0; m< rowColCount; m++){
                sudokuArr[n][m] = -1;
            }
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sud);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mistakes = 0;
                mistakesView.setText("MISTAKE(S): "+mistakes);

                for (int i = 0; i < rowColCount; i++) {
                    for (int j = 0; j < rowColCount; j++) {
                        sudokuArr[i][j] = -1;
                        solArr[i][j] = -1;
                    }
                }

                generateSudoku();
                Log.d(TAG,"SudokuArr; "+sudokuArr[0][0]+sudokuArr[0][1]+sudokuArr[0][2]+sudokuArr[0][3]+sudokuArr[0][4]+sudokuArr[0][5]+sudokuArr[0][6]+sudokuArr[0][7]+sudokuArr[0][8]);
                changeCol(0,1);
                genSol();

                sudokuView.setMatrix(sudokuArr);
                gameStarted = true;


            }
        });

    }




    private void genSol() {


        for (int n = 0; n< rowColCount; n++){
            for (int m = 0; m< rowColCount; m++){
                solArr[n][m] = sudokuArr[n][m];
            }
        }

        for (int i = 0; i<HARD_MODE;i++){

            Random r = new Random();
            int a = r.nextInt((8) + 1);
            int b = r.nextInt((8) + 1);

            if (sudokuArr[a][b]!= -1){
                sudokuArr[a][b]= -1;
            }
        }
    }

    private void fillCompArr() {

        compArr.clear();
        compArr.add(1);
        compArr.add(2);
        compArr.add(3);
        compArr.add(4);
        compArr.add(5);
        compArr.add(6);
        compArr.add(7);
        compArr.add(8);
        compArr.add(9);

    }





    private void generateSudoku() {

        //Row 1
        ArrayList<Integer>compArr1 = new ArrayList<>();
        compArr1.add(1);
        compArr1.add(2);
        compArr1.add(3);
        compArr1.add(4);
        compArr1.add(5);
        compArr1.add(6);
        compArr1.add(7);
        compArr1.add(8);
        compArr1.add(9);
        Collections.shuffle(compArr1);

        for (int i = 0;i<rowColCount;i++){
            sudokuArr[0][i] = compArr1.get(i);
        }

        //Row 2

        for (int j = 0; j<6;j++){
            sudokuArr[1][j+3] =compArr1.get(j);
        }
        sudokuArr[1][0]=compArr1.get(6);
        sudokuArr[1][1]=compArr1.get(7);
        sudokuArr[1][2]=compArr1.get(8);

        //Row 3

        for (int n = 0; n<3;n++){
            sudokuArr[2][n+6] = compArr1.get(n);
        }
        sudokuArr[2][0]=compArr1.get(3);
        sudokuArr[2][1]=compArr1.get(4);
        sudokuArr[2][2]=compArr1.get(5);

        sudokuArr[2][3]=compArr1.get(6);
        sudokuArr[2][4]=compArr1.get(7);
        sudokuArr[2][5]=compArr1.get(8);

        //Row 4

        for(int m = 0; m<8;m++){
            sudokuArr[3][m+1]=compArr1.get(m);
        }
        sudokuArr[3][0] = compArr1.get(8);

        //Row 5

        for (int k = 0; k<6;k++){
            sudokuArr[4][k+3] =sudokuArr[3][k];
        }
        sudokuArr[4][0]= sudokuArr[3][6];
        sudokuArr[4][1]= sudokuArr[3][7];
        sudokuArr[4][2]= sudokuArr[3][8];

        //Row 6

        for (int h = 0; h<6;h++){
            sudokuArr[5][h+3] =sudokuArr[4][h];
        }
        sudokuArr[5][0]= sudokuArr[4][6];
        sudokuArr[5][1]= sudokuArr[4][7];
        sudokuArr[5][2]= sudokuArr[4][8];

        //Row 7

        for(int t = 0; t<8;t++){
            sudokuArr[6][t+1]= sudokuArr[3][t];
        }
        sudokuArr[6][0] = sudokuArr[3][8];

        //Row 8

        for (int s = 0; s<6;s++){
            sudokuArr[7][s+3] =sudokuArr[6][s];
        }
        sudokuArr[7][0]= sudokuArr[6][6];
        sudokuArr[7][1]= sudokuArr[6][7];
        sudokuArr[7][2]= sudokuArr[6][8];

        //Row 9

        for (int w = 0; w<6;w++){
            sudokuArr[8][w+3] =sudokuArr[7][w];
        }
        sudokuArr[8][0]= sudokuArr[7][6];
        sudokuArr[8][1]= sudokuArr[7][7];
        sudokuArr[8][2]= sudokuArr[7][8];


    }

    public void changeCol(int c1, int c2){
        for (int i = 0; i<rowColCount;i++){
            int a;
            a = sudokuArr[i][c1];
            sudokuArr[i][c1] = sudokuArr[i][c2];
            sudokuArr[i][c2] = a;
        }
    }



    public static void sudokuViewTouchEvent(float x, float y, Context context){

        float width4 = sudokuView.getWidth()/9f;
        float height4 = sudokuView.getHeight()/9f;


        for (int r = 1;r< rowColCount+1;r++){
            for (int c = 1; c < rowColCount+1;c++){
                if (y >= (r-1)*height4 && y<= r*height4){

                    if (x >= (c-1)*width4 && x<= c*width4) {

                        Toast.makeText(context, "" + r + "." + c + " clicked", Toast.LENGTH_SHORT).show();
                        setNumber(r - 1, c - 1, context);
                    }
                }
            }
        }

        chooseView.invalidate();

        boolean solved = true;

        for (int n = 0; n<rowColCount;n++){
            for (int m = 0; m<rowColCount;m++){
                if (sudokuArr[n][m]== -1){
                    solved = false;
                }
            }
        }

        if(solved){
            Toast.makeText(context,"SUDOKU SOLVED",Toast.LENGTH_SHORT).show();
        }

    }

    private static void changeSelectionNum(int n) {
        numberSelected = n;

    }

    private static void setNumber(int r,int c,Context context) {
        if (sudokuArr[r][c]==-1){

            if (solArr[r][c] == numberSelected){

                sudokuArr[r][c] = numberSelected;
                sudokuView.setMatrix(sudokuArr);
                numberSelected = -1;
            }else{
                Toast.makeText(context,"THIS IS WRONG",Toast.LENGTH_SHORT).show();
                if(gameStarted){

                    mistakes++;
                    mistakesView.setText("MISTAKE(S): "+mistakes);

                    if (mistakes>2){
                        resetGame(context);
                    }
                }
            }
        }

    }

    private static void resetGame(Context c){

        Snackbar.make(sudokuLayout,"SORRY || You made too many mistakes",Snackbar.LENGTH_LONG).show();

        for (int i = 0; i<sudokuArr.length;i++){
            for (int k = 0; k<sudokuArr.length;k++){
                sudokuArr[i][k] = -1;
            }
        }

        for (int i = 0; i<sudokuArr.length;i++){
            for (int k = 0; k<sudokuArr.length;k++){
                solArr[i][k] = -1;
            }
        }

        sudokuView.invalidate();

        mistakes = 0;
        mistakesView.setText("MISTAKE(S): "+mistakes);
    }




    @Override
    public void onNumberSelected(int selectedNum) {

        changeSelectionNum(selectedNum);
        chooseView.addMark(selectedNum);
    }
}
