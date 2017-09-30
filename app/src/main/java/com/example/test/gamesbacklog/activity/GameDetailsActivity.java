package com.example.test.gamesbacklog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.gamesbacklog.R;
import com.example.test.gamesbacklog.activity.model.Game;
import com.example.test.gamesbacklog.activity.utility.ConfirmDeleteDialog;
import com.example.test.gamesbacklog.activity.utility.DataSource;

public class GameDetailsActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private Game game;
    private TextView title;
    private TextView platform;
    private TextView status;
    private TextView date;
    private TextView notes;

    /*Open GameDetailsActivity.java, declare a Game object and five TextView objects.
    Call them title, platform, status, date and notes. Inside a separate method (called from ‘onCreate’ of course), initialize the TextViews.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get the game from the intent, which was passed as parameter
        this.game = (Game) getIntent().getSerializableExtra("selectedGame");
        setGameViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_modify_game);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
                intent.putExtra("selectedGame", game);
                startActivityForResult(intent, 1000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Set the Game Card with updated game
        game = (Game) data.getSerializableExtra("selectedGame");
        setGameViews();
    }


    public void setGameViews(){
        title = (TextView)findViewById(R.id.detailTitle);
        platform = (TextView)findViewById(R.id.detailPlatformB);
        status = (TextView)findViewById(R.id.detailStatusB);
        date = (TextView)findViewById(R.id.detailDateB);
        notes = (TextView)findViewById(R.id.detailNotesB);

        title.setText(game.getTitle().toString());
        platform.setText(game.getPlatform().toString());
        status.setText(game.getGameStatus().toString());
        date.setText(game.getDateAdded().toString());
        notes.setText(game.getNotes().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_details, menu);
        return true;
    }


    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog)
    {
        //User clicked on the confirm button of the Dialog, delete the game from Database
        DataSource datasource = new DataSource(this);
        //We only need the id of the game to delete it
        datasource.deleteGame(game.getId());
        //Game has been deleted, go back to the MainActivity
        showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(android.app.DialogFragment dialog)
    {
        //Do nothing, Dialog will disappear
    }

    private void showGameDeletedToast()
    {
        Context context = getApplicationContext();
        String text = String.format("%s %s", game.getTitle(), getString(R.string.game_deleted));
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_delete_game)
        {
            // Show the ConfirmDeleteDialog
            android.app.DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_game_deletion_single));
            bundle.putString("positiveButton", getString(R.string.dialog_game_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

}
