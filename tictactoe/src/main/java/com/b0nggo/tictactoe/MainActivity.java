package com.b0nggo.tictactoe;

import android.content.res.ColorStateList;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    static int[] row_top_buttons = {
            R.id.b11,
            R.id.b12,
            R.id.b13,
    };
    static int[] row_middle_buttons = {
            R.id.b21,
            R.id.b22,
            R.id.b23,
    };
    static int[] row_bottom_buttons = {
            R.id.b31,
            R.id.b32,
            R.id.b33
    };
    static int[] row_horizontal_buttons_ltr = {
            R.id.b11,
            R.id.b22,
            R.id.b33
    };
    static int[] row_horizontal_buttons_rtl = {
            R.id.b13,
            R.id.b22,
            R.id.b31
    };
    static int[] col_left_buttons = {
            R.id.b11,
            R.id.b21,
            R.id.b31
    };
    static int[] col_right_buttons = {
            R.id.b12,
            R.id.b22,
            R.id.b32
    };
    static int[] col_mid_buttons = {
            R.id.b13,
            R.id.b23,
            R.id.b33
    };

    static int[] all_buttons = {
            R.id.b11,
            R.id.b12,
            R.id.b13,
            R.id.b21,
            R.id.b22,
            R.id.b23,
            R.id.b31,
            R.id.b32,
            R.id.b33
    };

    static int[][] arrays = {row_top_buttons, row_middle_buttons, row_bottom_buttons, row_horizontal_buttons_ltr, col_left_buttons, col_right_buttons, col_mid_buttons, row_horizontal_buttons_rtl};
    TextView text;
    public void updateTurnText(String turn){
        TextView text = (TextView) findViewById(R.id.current_turn);
        text.setText(turn + "'s turn");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        updateTurnText(turn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        }


    String turn = "X";
    int empty_buttons = 9;
    public void displayWinner(int winner) {
        // Display winner screen
        for (int button_id : all_buttons) {
            Log.d("Disabler","Disabling btns");
            Button btn_obj = findViewById(button_id);
            btn_obj.setEnabled(false);
            btn_obj.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_pressed)));
        }
        FrameLayout gameOverFrame = findViewById(R.id.endgamescreen);
        TextView txt_obj = findViewById(R.id.twinner);
        // Handle who is the winner
        String winner_handle = "";
        if (winner >= 2) {
        if (winner == 2) {
            winner_handle = "X";
            }
        else if (winner == 3) {
            winner_handle = "O";
            }
        txt_obj.setText(winner_handle + " is the winner!");
        }
        else {
            txt_obj.setText("The game ended in a tie!");
        }
        gameOverFrame.setVisibility(View.VISIBLE);

    }
    public void restartGame(View v) {
        // Reset game state
        for (int button_id : all_buttons) {
            Log.d("Restart","Restarting all buttons");
            Button btn_obj = findViewById(button_id);
            btn_obj.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.normalstate)));
            btn_obj.setText("");
            btn_obj.setEnabled(true);
        }
        FrameLayout gameOverFrame = findViewById(R.id.endgamescreen);
        gameOverFrame.setVisibility(View.GONE);
        empty_buttons = 9;
    }
    public void findWinner(int btn_id) {
        for (int[] array : arrays) {
            for (int element : array) {
                if (btn_id == element) {
                    boolean finished = true;
                    boolean x_winner = true;
                    boolean o_winner = true;
                    for (int inside_element : array) {
                        Log.d("test","Inside Loop");
                        Button button_object = findViewById(inside_element);
                        if (button_object.getText() == "X") {
                            o_winner = false;
                        }
                        if (button_object.getText() == "O") {
                            x_winner = false;
                        }
                        String ym = (String) button_object.getText();
                        Log.d("output", ym);
                        if (button_object.getText() == "") {
                            Log.d("ERR","No winners!");
                            x_winner = false;
                            o_winner = false;
                            finished = false;
                            //return;
                        }
                    }
                    if (x_winner){
                        Log.d("ERRR", "X is winner!");
                        displayWinner(2);
                        return;
                        //return 2;
                    }
                    if (o_winner) {
                        Log.d("ERRR", "O is winner!");
                        displayWinner(3);
                        return;
                    }
                    if (empty_buttons == 0){
                        // tie
                        Log.d("ENDGAME","End of game detected");
                        displayWinner(1);
                        return;
                    }
                }
            }
        }
    }

    public void setStatus(View v) {
        empty_buttons--;
        int btn_id = v.getId();
        Button btn = findViewById((v.getId()));
        v.setEnabled(false);
        if (turn.equals("X")) {
            btn.setText("X");
            turn = "O";
        }
        else {
            btn.setText("O");
            turn = "X";
        }
        btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_pressed)));
        findWinner(btn_id);
        updateTurnText(turn);

    }
}