package com.example.oskarjerzyk.stoperprpn;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int homeScore = 0;
    private int awayScore = 0;

    private static final long START_TIME_IN_MILLIS = 600000;
    private static final long PENALTY_TIME_IN_MILLIS = 120000;

    /**
     * This variables are used to operate on main timer with buttons start/pause and reset
     */
    private TextView mainTimerTextView;
    private Button buttonStartPause;
    private Button buttonReset;
    private CheckBox resetCheckBox;
    private CountDownTimer mainCountDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    /**
     * Variables for penalty nr 1
     */
    private TextView penalty1TextView;
    private Button penalty1Button;
    private CheckBox penalty1CheckBox;
    private CountDownTimer penalty1CountDownTimer;
    private long timeLeftPenalty1 = PENALTY_TIME_IN_MILLIS;
    boolean isPenalty1Running;
    boolean isPenalty1Finished;

    /**
     * Variables for penalty nr 2
     */
    private TextView penalty2TextView;
    private Button penalty2Button;
    private CheckBox penalty2CheckBox;
    private CountDownTimer penalty2CountDownTimer;
    private long timeLeftPenalty2 = PENALTY_TIME_IN_MILLIS;
    boolean isPenalty2Running;
    boolean isPenalty2Finished;

    /**
     * Variables for penalty nr 3
     */
    private TextView penalty3TextView;
    private Button penalty3Button;
    private CheckBox penalty3CheckBox;
    private CountDownTimer penalty3CountDownTimer;
    private long timeLeftPenalty3 = PENALTY_TIME_IN_MILLIS;
    boolean isPenalty3Running;
    boolean isPenalty3Finished;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Main timer init code
         */
        mainTimerTextView = findViewById(R.id.main_timer_textview);
        buttonStartPause = findViewById(R.id.start_stop_button);
        buttonReset = findViewById(R.id.reset_button);
        resetCheckBox = findViewById(R.id.reset_checkbox);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning) {
                    pauseTimer();
                    if (isPenalty1Running) {
                        pausePenalty1();
                    }
                    if (isPenalty2Running) {
                        pausePenalty2();
                    }
                    if (isPenalty3Running) {
                        pausePenalty3();
                    }
                } else {
                    startTimer();
                    if (penalty1CheckBox.isChecked()) {
                        startPenalty1Timer();
                    }
                    if (penalty2CheckBox.isChecked()) {
                        startPenalty2Timer();
                    }
                    if (penalty3CheckBox.isChecked()) {
                        startPenalty3Timer();
                    }
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                resetPenalty1();
                resetPenalty2();
                resetPenalty3();
            }
        });

        updateMainTimer();

        /**
         * Penalty nr 1 init code
         */
        penalty1TextView = findViewById(R.id.penalty1_text_view);
        penalty1Button = findViewById(R.id.penalty1_button);
        penalty1CheckBox = findViewById(R.id.penalty1_checkbox);

        penalty1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning && penalty1CheckBox.isChecked()) {
                    startPenalty1Timer();
                }
            }
        });

        /**
         * Penalty nr 2 init code
         */
        penalty2TextView = findViewById(R.id.penalty2_text_view);
        penalty2Button = findViewById(R.id.penalty2_button);
        penalty2CheckBox = findViewById(R.id.penalty2_checkbox);

        penalty2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning && penalty2CheckBox.isChecked()) {
                    startPenalty2Timer();
                }
            }
        });

        /**
         * Penalty nr 3 init code
         */
        penalty3TextView = findViewById(R.id.penalty3_text_view);
        penalty3Button = findViewById(R.id.penalty3_button);
        penalty3CheckBox = findViewById(R.id.penalty3_checkbox);

        penalty3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning && penalty3CheckBox.isChecked()) {
                    startPenalty3Timer();
                }
            }
        });
    }

    /**
     * Main timer methods
     */
    private void startTimer() {
        mainCountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateMainTimer();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                buttonStartPause.setText("start");
            }
        }.start();

        isTimerRunning = true;
        buttonStartPause.setText("pause");
    }

    private void pauseTimer() {
        mainCountDownTimer.cancel();
        isTimerRunning = false;
        buttonStartPause.setText("start");
    }

    private void resetTimer() {
        if (resetCheckBox.isChecked()) {
            timeLeftInMillis = START_TIME_IN_MILLIS;
            updateMainTimer();
        }
    }

    private void updateMainTimer() {
        int minutes = (int) ((START_TIME_IN_MILLIS - timeLeftInMillis) / 1000) / 60;
        int seconds = (int) ((START_TIME_IN_MILLIS - timeLeftInMillis) / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mainTimerTextView.setText(timeLeftFormatted);
    }

    /**
     * Penalty 1 timer methods
     */
    private void startPenalty1Timer() {
        penalty1CountDownTimer = new CountDownTimer(timeLeftPenalty1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftPenalty1 = millisUntilFinished;
                updatePenalty1Timer();
            }

            @Override
            public void onFinish() {
                isPenalty1Running = false;
                isPenalty1Finished = true;
                resetPenalty1();
            }
        }.start();

        isPenalty1Running = true;
        isPenalty1Finished = false;
    }

    private void pausePenalty1() {
        penalty1CountDownTimer.cancel();
    }

    private void resetPenalty1() {
        if (resetCheckBox.isChecked() || isPenalty1Finished) {
            timeLeftPenalty1 = PENALTY_TIME_IN_MILLIS;
            updatePenalty1Timer();
        }
    }

    private void updatePenalty1Timer() {
        int minutes = (int) (timeLeftPenalty1 / 1000) / 60;
        int seconds = (int) (timeLeftPenalty1 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        penalty1TextView.setText(timeLeftFormatted);
    }

    /**
     * Penalty 2 timer methods
     */
    private void startPenalty2Timer() {
        penalty2CountDownTimer = new CountDownTimer(timeLeftPenalty2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftPenalty2 = millisUntilFinished;
                updatePenalty2Timer();
            }

            @Override
            public void onFinish() {
                isPenalty2Running = false;
                isPenalty2Finished = true;
                resetPenalty2();
            }
        }.start();

        isPenalty2Running = true;
        isPenalty2Finished = false;
    }

    private void pausePenalty2() {
        penalty2CountDownTimer.cancel();
    }

    private void resetPenalty2() {
        if (resetCheckBox.isChecked() || isPenalty2Finished) {
            timeLeftPenalty2 = PENALTY_TIME_IN_MILLIS;
            updatePenalty2Timer();
        }
    }

    private void updatePenalty2Timer() {
        int minutes = (int) (timeLeftPenalty2 / 1000) / 60;
        int seconds = (int) (timeLeftPenalty2 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        penalty2TextView.setText(timeLeftFormatted);
    }

    /**
     * Penalty 3 timer methods
     */
    private void startPenalty3Timer() {
        penalty3CountDownTimer = new CountDownTimer(timeLeftPenalty3, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftPenalty3 = millisUntilFinished;
                updatePenalty3Timer();
            }

            @Override
            public void onFinish() {
                isPenalty3Running = false;
                isPenalty3Finished = true;
                resetPenalty3();
            }
        }.start();

        isPenalty3Running = true;
        isPenalty3Finished = false;
    }

    private void pausePenalty3() {
        penalty3CountDownTimer.cancel();
    }

    private void resetPenalty3() {
        if (resetCheckBox.isChecked() || isPenalty3Finished) {
            timeLeftPenalty3 = PENALTY_TIME_IN_MILLIS;
            updatePenalty3Timer();
        }
    }

    private void updatePenalty3Timer() {
        int minutes = (int) (timeLeftPenalty3 / 1000) / 60;
        int seconds = (int) (timeLeftPenalty3 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        penalty3TextView.setText(timeLeftFormatted);
    }

    /**
     * This method displays home score
     */
    public void displayHomeScore() {
        TextView textView = findViewById(R.id.home_score_textview);
        textView.setText(String.valueOf(homeScore));
    }

    /**
     * This method displays away score
     */
    public void displayAwayScore() {
        TextView textView = findViewById(R.id.away_score_textview);
        textView.setText(String.valueOf(awayScore));
    }

    /**
     * This method adds 1 point for home team
     */
    public void incrementHomeScore(View view) {
        this.homeScore++;
        displayHomeScore();
    }

    /**
     * This method adds 1 point for away team
     */
    public void incrementAwayScore(View view) {
        this.awayScore++;
        displayAwayScore();
    }

    /**
     * This method decrements home team score
     */
    public void decrementHomeScore(View view) {
        if (homeScore > 0) this.homeScore--;
        displayHomeScore();
    }

    /**
     * This method decrements away team score
     */
    public void decrementAwayScore(View view) {
        if (awayScore > 0) this.awayScore--;
        displayAwayScore();
    }

}
