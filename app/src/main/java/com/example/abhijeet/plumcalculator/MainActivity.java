package com.example.abhijeet.plumcalculator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private TextView inputOutputView;
    //private Button[] buttonArray = new Button[10];
    //private Button buttonC, buttonPlus, buttonMinus, buttonEquals;
    private int stackNumber;
    private String tempString, stackOperator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stackNumber=0;
        inputOutputView=(TextView)findViewById(R.id.inputOutputArea);


        /* ***match buttons inside a  'for' loop**** */
        /*for(int i=0;i<10;i++){
            String but = "button"+i;
            int resID = getResources().getIdentifier(but, "id", getPackageName());
            buttonArray[i]=(Button)findViewById(resID);
        }*/

        clearAll(this.findViewById(android.R.id.content));
    }

    public void setStackNumber(int a){stackNumber=a;}
    public int getStackNumber(){return stackNumber;}
    public void displayToScreen(String x){inputOutputView.setText(x);}
    public boolean isTempStringNull(){
        if(tempString=="")
            return true;
        return false;
    }

    /* ***This function clears everything, called when 'C' is clicked and also when the calculator is first launched*** */
    public void clearAll(View v){
        inputOutputView.setText("0");
        stackNumber=0;
        tempString="";
        stackOperator="";
    }


    /* ***onClickListener for Buttons 1-9, this function is used to display numbers to calculator screen*** */
    public void numericListener(View v){
        String screen = (String)inputOutputView.getText();
        String pressedButton = (String)((Button) v).getText();

        if(tempString==""||tempString=="0"){  //directly display number is 0 is present on screen
            displayToScreen(pressedButton);
            tempString=pressedButton;
        }
        else{
            displayToScreen(screen+pressedButton);  //append number to end if some number is already present
            tempString+=pressedButton;
        }
    }


    /* ***onClickListener for 'zero' button*** */
    public void zeroListener(View v){
        String screen = (String)inputOutputView.getText();

        if(tempString==""){
            if(stackOperator=="+"||stackOperator=="-"){  //for cases like "x+00" or "x-005"
                tempString="0";
                displayToScreen("0");
            }
        }
        else{
            if(tempString!="0") {  //append "0" only if some nonzero number is present on screen
                displayToScreen(screen + "0");
                tempString += "0";
            }
        }
    }


    /* ***onClickListener for '+' button*** */
    public void plusListener(View v){
        if(stackOperator==""){  //for cases "x+y"
            if(tempString=="")
                tempString="0";
            setStackNumber(Integer.parseInt(tempString));
            stackOperator="+";
            tempString="";
        }
        else if(stackOperator=="+"){  //for cases "x+y+z" or "x++y"
            if(tempString==""){
                stackOperator="+";
                return;
            }
            setStackNumber(getStackNumber()+Integer.parseInt(tempString));
            tempString="";
            displayToScreen(Integer.toString(stackNumber));
        }
        else if(stackOperator=="-"){  //for cases "x-y+z" or "x-+y"
            if(tempString==""){
                stackOperator="+";
                return;
            }
            setStackNumber(getStackNumber()-Integer.parseInt(tempString));
            tempString="";
            stackOperator="+";
            displayToScreen(Integer.toString(stackNumber));

        }
        else if(stackOperator=="="){  //for cases like "y=+x"
            stackOperator="+";
            tempString="";
        }
    }


    /* ***onClickListener for '-' button*** */
    public void minusListener(View v){
        if(stackOperator==""){  //for cases "x-y"
            if(tempString=="")
                tempString="0";
            setStackNumber(Integer.parseInt(tempString));
            stackOperator="-";
            tempString="";
        }
        else if(stackOperator=="+"){  //for cases "x+y-z" or "x+-y"
            if(tempString==""){
                stackOperator="-";
                return;
            }
            setStackNumber(getStackNumber()+Integer.parseInt(tempString));
            tempString="";
            stackOperator="-";
            displayToScreen(Integer.toString(stackNumber));
        }
        else if(stackOperator=="-"){  //for cases "x-y-z" or "x--y"
            if(tempString==""){
                stackOperator="-";
                return;
            }
            setStackNumber(getStackNumber()-Integer.parseInt(tempString));
            tempString="";
            displayToScreen(Integer.toString(stackNumber));

        }
        else if(stackOperator=="="){  //for cases "x=-"
            stackOperator="-";
            tempString="";
        }
    }


    /* ***onClickListener for '=' button*** */
    public void equalsListener(View v){
        if(stackOperator=="+"){  //for cases "x+y=" and to handle "x+=" error
            if(isTempStringNull()){
                displayToScreen("invalid input! Press 'C' to continue");
                return;
            }
            setStackNumber(getStackNumber()+Integer.parseInt(tempString));
            displayToScreen(Integer.toString(getStackNumber()));
            tempString="";
            stackOperator="=";
        }
        if(stackOperator=="-"){  //for cases "x-y=" and to handle "x-=" error
            if(isTempStringNull()){
                displayToScreen("invalid input! Press 'C' to continue");
                return;
            }
            setStackNumber(getStackNumber()-Integer.parseInt(tempString));
            displayToScreen(Integer.toString(getStackNumber()));
            tempString="";
            stackOperator="=";
        }
    }

}