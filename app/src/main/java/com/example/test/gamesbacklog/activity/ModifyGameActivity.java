package com.example.test.gamesbacklog.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.gamesbacklog.R;
import com.example.test.gamesbacklog.activity.model.Game;
import com.example.test.gamesbacklog.activity.utility.ConfirmDeleteDialog;
import com.example.test.gamesbacklog.activity.utility.DataSource;

public class ModifyGameActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private Game game;
    private EditText titleInput;
    private EditText platformInput;
    private Spinner statusSpinner;
    private EditText notesInput;
    private ArrayAdapter statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the selected game that we've sent from GameDetailsActivity
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("selectedGame");
        setGameView();
        //Create on ArrayAdapter using the string array and a default spinner layout
        statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Set the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);
        setSpinnerPosition(statusAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyGame();
            }
        });
    }

    private void setGameView() {
        //declare textfields
        titleInput = (EditText)findViewById(R.id.modifyTitle);
        platformInput = (EditText)findViewById(R.id.modifyPlatform);
        statusSpinner = (Spinner)findViewById(R.id.modifyStatusSpinner);
        notesInput = (EditText)findViewById(R.id.modifyNotes);
        //Set game data in edit textfields
        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        notesInput.setText(game.getNotes());
    }


    private void setSpinnerPosition(ArrayAdapter adapter){
        if (!game.getGameStatus().equals(null)){
            //Gets the position of the correct spinner item by comparing
            //which item of the Spinner matches with the gameStatus
            int spinnerPosition = adapter.getPosition(game.getGameStatus());
            //Display the correct gameStatus in the Spinner based on the found position
            statusSpinner.setSelection(spinnerPosition);
        }
    }

    void modifyGame() {
        // Get the input from the Views
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        String gameStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if ((title != null) && title.isEmpty()) {
            // Make EditText titleInput display an error message, and display a toast
            // That the title field is empty
            ModifyGameActivity.setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if ((platform != null) && platform.isEmpty()) {
            // Make EditText platformInput display an error message, and display a toast
            // That the platform field is empty
            ModifyGameActivity.setErrorText(platformInput, getString(R.string.platform_is_required));
            showToast(getString(R.string.platform_field_is_empty));
        } else {
            // Update the game with the new data
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);

            // Create a DataSource object, and pass it the context of this activity
            DataSource datasource = new DataSource (this);
            datasource.modifyGame(game);

            //Notify the user of the success
            showToast(getString(R.string.game_has_been_modified));

            // Starting the previous Intent
            Intent previousActivity = new Intent(this, GameDetailsActivity.class);
            // Sending the data to GameDetailsActivity
            previousActivity.putExtra("selectedGame", game);
            setResult(1000, previousActivity);
            finish();
        }
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private static void setErrorText(EditText editText, String message) {
        // Get the color white in integer form
        int RGB = Color.argb(255, 255, 0, 0);
// Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);
// Object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
// Give the message from the first till the last character a white color.
// The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
// Make the EditText display the error message
        editText.setError(ssbuilder);
    }


    @Override
    public void onBackPressed() {
        super.onResume();  // Always call the superclass method first
        // Save game and go back to MainActivity
        modifyGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_cancel)
        {
            // Show the ConfirmDiscardDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_game_discard));
            bundle.putString("positiveButton", getString(R.string.dialog_game_modify_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        Intent previousActivity = new Intent(this, GameDetailsActivity.class);
        //Sending the origional data to GameDetailActivity
        previousActivity.putExtra("selectedGame", game);
        setResult(1000, previousActivity);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {

    }

}
