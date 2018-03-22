package com.sdsmdg.vishwas.elanicassignment.views;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.sdsmdg.vishwas.elanicassignment.QuestionListAdapter;
import com.sdsmdg.vishwas.elanicassignment.R;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;
import com.sdsmdg.vishwas.elanicassignment.presenters.PresenterClass;

import static com.sdsmdg.vishwas.elanicassignment.presenters.PresenterClass.getData;

public class MainActivity extends AppCompatActivity {

    private ImageView statusImage;
    private Toolbar toolbar;
    private RecyclerView questionList;
    private ProgressBar progressBar;
    private QuestionListAdapter questionListAdapter;
    private QuestionClass questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PresenterClass.startApp(MainActivity.this);
    }

    public void showSplash() {
        setContentView(R.layout.splash_screen);
    }


    public void startMainActivity() {
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statusImage = findViewById(R.id.status_image);
        progressBar = findViewById(R.id.simpleProgressBar);
        questionList = findViewById(R.id.question_list);
        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        questions = new QuestionClass();
        questionListAdapter = new QuestionListAdapter(MainActivity.this, questions);
        questionList.setAdapter(questionListAdapter);
        getData(MainActivity.this, "Android");
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setAdapter(QuestionClass questions) {
        this.questions.setItems(questions.getItems());
        questionListAdapter.notifyDataSetChanged();
//        questionList.setAdapter();
    }

    public void clearAdapter(){
//        questionList.removeAllViews();
        questions.clear();
        questionListAdapter.notifyDataSetChanged();
//        questionList.setAdapter(new QuestionListAdapter(MainActivity.this, new QuestionClass()));
    }

    public void clearScreen(){
        clearImage();
        clearAdapter();
    }

    public void noResultFound() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.no_result_found));
    }

    public void httpError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.http_error));
    }

    public void clearImage() {
        statusImage.setImageResource(0);
    }

    public void connectionError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.connection_error));
    }

    public void unknownError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sad_icon));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                getData(MainActivity.this, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
