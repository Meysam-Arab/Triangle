package ir.fardan7eghlim.tri.Views.Note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

import ir.fardan7eghlim.tri.Models.NoteModel;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_note;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_task;

public class IndexNoteActivity extends BaseActivity {

    private ArrayList<NoteModel> list_of_notes;
    private ImageView img_add_note;
    private LinearLayout ll_no_note_add_note;
    private StaggeredGridView grid_view_add_note;
    private CustomAdapterList_note CALM01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_note);

        ll_no_note_add_note=findViewById(R.id.ll_no_note_add_note);
        grid_view_add_note=findViewById(R.id.grid_view_add_note);

        img_add_note=findViewById(R.id.img_add_note);
        img_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexNoteActivity.this, InsertNoteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume()
    {
        //meysam - fill list of notes from db
        list_of_notes=db.getNotes();
        // amir
        if(list_of_notes.size()>0){
            ll_no_note_add_note.setVisibility(View.GONE);
            grid_view_add_note.setVisibility(View.VISIBLE);
            //show notes
            grid_view_add_note.setAdapter(null);
            CALM01 = new CustomAdapterList_note(IndexNoteActivity.this,
                    new ArrayList<Object>(list_of_notes)
                    ,null);
            grid_view_add_note.setAdapter(CALM01);
            grid_view_add_note.invalidateViews();
        }else{
            ll_no_note_add_note.setVisibility(View.VISIBLE);
            grid_view_add_note.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {

        CustomAdapterList_note.setInflater(null);
        if(CALM01 != null)
            CALM01.setInflater(null);

        super.onDestroy();
    }
}
