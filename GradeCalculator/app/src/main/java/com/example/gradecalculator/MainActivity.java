package com.example.gradecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//Code Reference from SFU CANVAS Lab 3 Activity pdf file
//Student Name : Xiangwei ZHANG
//Student Number : 301337059
public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private static final String DEBUG_TAG = "CourseGrades";//constants used when restoring state
    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private static final String PARTICIPATION = "PARTICIPATION";
    private static final String PROJECT = "PROJECT";
    private static final String QUIZZES = "QUIZZES";
    private static final String EXAM = "EXAM";
    private static final String FINALMARK = "FINAL MARK";

    private double assignments;//assignments mark entered by the users
    private EditText assignmentEditText;
    private double participation;//participation mark entered by the users
    private EditText participationEditText;
    private double project;//project marks entered by the users
    private EditText projectEditText;
    private double quizzes;//quizzes marks entered by the users
    private EditText quizzesEditText;
    private TextView finalMarkTextView;//The final mark display
    private double finalMark;
    private EditText examMarkEditText;
    private SeekBar examSeekBar;
    private double examValue;//the value that users input
    private Button resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(DEBUG_TAG, "onCreate() is called");
        setContentView(R.layout.activity_main);

            //get references to the controls in the layout
            assignmentEditText = findViewById(R.id.assignmentsEditView);
            assignmentEditText.addTextChangedListener((TextWatcher) this);

            participationEditText = findViewById(R.id.participationEditView);
            participationEditText.addTextChangedListener((TextWatcher) this);

            projectEditText = findViewById(R.id.projectEditView);
            projectEditText.addTextChangedListener((TextWatcher) this);

            quizzesEditText = findViewById(R.id.quizzesEditView);
            quizzesEditText.addTextChangedListener((TextWatcher) this);

            examMarkEditText = findViewById(R.id.examEditView);

            finalMarkTextView = findViewById(R.id.finalMarkRightTextView);

            examSeekBar = findViewById(R.id.examSeekBar);
            examSeekBar.setOnSeekBarChangeListener(examSeekBarListener);
            examMarkEditText.setText("80");
            examValue = 80;
            updateStandard();
            resetButton = findViewById(R.id.resetButton);
            resetButton.setOnClickListener((View.OnClickListener) this);

    }

    private void updateStandard(){
        //calculate the final mark
        finalMark = assignments * 15/100 + participation * 15/100 +
                project * 20/100 + quizzes * 20/100 + examValue * 30/100;
        //set the text in finalMarkRightTextView to finalMark
        //"%.02f"represents the float in 2 decimals
        finalMarkTextView.setText(String.format("%.02f", finalMark));
    }

    private void updateExam(){
        //set examMarkEditText to match the position of the examSeekBar
        //"%d" represents decimal integer
        examMarkEditText.setText(String.format("%.0f", examValue));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save values of assignments, participation, project, quizzes, and examValue
        super.onSaveInstanceState(outState);
        Log.i(DEBUG_TAG, "onSaveInstanceState() is called");
        outState.putDouble(ASSIGNMENTS, assignments);
        Log.i("ASSIGNMENTS", assignments+"");
        outState.putDouble(PROJECT,project);
        outState.putDouble(QUIZZES,quizzes);
        outState.putDouble(PARTICIPATION,participation);
        outState.putDouble(EXAM,examValue);
        //save values of final mark
        outState.putDouble(FINALMARK, finalMark);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(DEBUG_TAG, "onRestoreInstanceState() is called");

        assignments = savedInstanceState.getDouble("ASSIGNMENTS");
        Log.i("ASSIGNMENTS on Restore", assignments+"");
        project = savedInstanceState.getDouble("PROJECT");
        quizzes = savedInstanceState.getDouble("QUIZZES");
        participation = savedInstanceState.getDouble("PARTICIPATION");
        examValue = savedInstanceState.getDouble("EXAM");
        updateStandard();


    }

    // create the anonymous inner-class object examSeekBarListener that responds to examSeekBar events.
    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        //update examValue then call updateExam(), updateStandard
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //sets examValue to position of the SeekBar's thumb
            examValue = seekBar.getProgress();
            Log.i(DEBUG_TAG, "examValue" + examValue);
            updateExam();//update examMarkEditText
            updateStandard();
        }//end method onProgressChanged()

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



    //The onTextChanged() method is called whenever the text in an EditText is modified.
    //This method receives four parameters
    //We only use CharSequence s to get the values that the user enters.
    //The text entered by the user is properly converted to double.
    //To check in which component the user has entered text, we will use isFocused().
    @Override
    //CharSequence is a sequence of character
    public void onTextChanged(CharSequence s, int start, int before, int count){
        if(assignmentEditText.isFocused()){
            try{
                //parseDouble can be only input with string
                //Double.parseDouble(string) convert string into floats
                assignments = Double.parseDouble(s.toString());
                if(assignments > 100){
                    Toast.makeText(this, "Your score should no more than 100", Toast.LENGTH_SHORT);
                    assignmentEditText.setText("");
                    assignmentEditText.setHint("ENTER 0 - 100");
                    assignments = 0;
                }else{
                    updateStandard();
                }
            }
            //If the input is not correct, then assign assignments variable 0.0 value
            catch(NumberFormatException e){
                assignments = 0.0;
            }
        }

        if(participationEditText.isFocused()){
            try{
                participation = Double.parseDouble(s.toString());

                if(participation > 100){
                    Toast.makeText(this, "Your score should no more than 100", Toast.LENGTH_SHORT);
                    participationEditText.setText("");
                    participationEditText.setHint("ENTER 0 - 100");
                    participation = 0;
                }else{
                    updateStandard();
                }
            }
            catch(NumberFormatException e){
                participation = 0.0;
            }
        }

        if(projectEditText.isFocused()){
            try{
                project = Double.parseDouble(s.toString());

                if(project > 100){
                    Toast.makeText(this, "Your score should no more than 100", Toast.LENGTH_SHORT);
                    projectEditText.setText("");
                    projectEditText.setHint("ENTER 0 - 100");
                    project = 0;
                }else{
                    updateStandard();
                }
            }
            catch(NumberFormatException e){
                project = 0.0;
            }
        }

        if(quizzesEditText.isFocused()){
            try{
                quizzes = Double.parseDouble(s.toString());

                if(quizzes > 100){
                    Toast.makeText(this, "Your score should no more than 100", Toast.LENGTH_SHORT);
                    quizzesEditText.setText("");
                    quizzesEditText.setHint("ENTER 0 - 100");
                    quizzes = 0;
                }else{
                    updateStandard();
                }
            }
            catch(NumberFormatException e){
                quizzes = 0.0;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable.toString().length() > 1 && editable.toString().startsWith("0")){
            editable.delete(0,1);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onClick(View view) {
        assignmentEditText.setText("");
        assignmentEditText.setHint("INPUT YOUR SCORE HERE");
        assignments = 0;

        participationEditText.setText("");
        participationEditText.setHint("INPUT YOUR SCORE HERE");
        participation = 0;

        projectEditText.setText("");
        projectEditText.setHint("INPUT YOUR SCORE HERE");
        project = 0;

        quizzesEditText.setText("");
        quizzesEditText.setHint("INPUT YOUR SCORE HERE");
        quizzes = 0;

        finalMarkTextView.setText("");
        finalMarkTextView.setHint("YOUR FINAL MARK");

        examSeekBar.setProgress(80);
    }
}