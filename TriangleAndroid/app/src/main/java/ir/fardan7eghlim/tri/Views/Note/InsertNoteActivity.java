package ir.fardan7eghlim.tri.Views.Note;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.math.BigInteger;

import ir.fardan7eghlim.tri.Models.NoteModel;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.DialogPopUp;

public class InsertNoteActivity extends BaseActivity {

    private EditText txt_title_inNote;
    private EditText txt_description_inNote;
    private Button btn_add_inNote;
    private Button btn_Quit_instask;
    private BigInteger note_id;
    private NoteModel note_edit;
    private ImageView img_delete_inNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_note);

        //sets
        txt_title_inNote = findViewById(R.id.txt_title_inNote);
        txt_description_inNote = findViewById(R.id.txt_description_inNote);
        btn_add_inNote = findViewById(R.id.btn_add_inNote);
        btn_Quit_instask = findViewById(R.id.btn_Quit_instask);
        img_delete_inNote = findViewById(R.id.img_delete_inNote);

        ///for edit
        Bundle extras = getIntent().getExtras();
        note_id = null;
        note_edit = null;
        if (extras != null && extras.getString("note_id") != null) {
            note_id = new BigInteger(extras.getString("note_id"));
            if (note_id != null) {
                note_edit = db.getNoteById(note_id);
                txt_title_inNote.setText(note_edit.getNoteTitle());
                txt_description_inNote.setText(note_edit.getNoteDescription());
            }
        }
        if (note_edit != null) {
            btn_add_inNote.setText("ویرایش");
            img_delete_inNote.setVisibility(View.VISIBLE);
            img_delete_inNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // meysam - action for delete a note by its id
                    //go for delete
                    DialogPopUp.show(InsertNoteActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while(DialogPopUp.isUp()){
                                    Thread.sleep(500);
                                }
                                if(!DialogPopUp.isUp()){
                                    Thread.currentThread().interrupt();//meysam
                                    if(DialogPopUp.dialog_result==1){
                                        //first button clicked
                                        db.deleteNoteById(note_id);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Utility.displayToast(InsertNoteActivity.this,getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                                            }
                                        });
                                        InsertNoteActivity.this.finish();

                                    }
                                    else if (DialogPopUp.dialog_result==2)
                                    {
                                        //second button clicked

                                    }
                                    else
                                    {
                                        //do nothing
                                    }
                                    DialogPopUp.hide();
                                }
                            }
                            catch (InterruptedException e)
                            {
                                // Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        } else {
            txt_title_inNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        //buttos
        btn_Quit_instask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButoon();
            }
        });
        btn_add_inNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = txt_title_inNote.getText().toString();
                final String description = txt_description_inNote.getText().toString();
                if (!title.equals("") || !description.equals("")) {
                    //add new note even title or description was inserted
                    NoteModel note = new NoteModel();
                    note.setNoteTitle(title);
                    note.setNoteDescription(description);
                    note.setNoteDateTime(Utility.convertDateGorgeian2Persian(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                    if(note_edit!=null){
                        //edit
                        //meysam - edit note
                        note_edit.setNoteTitle(note.getNoteTitle());
                        note_edit.setNoteDescription(note.getNoteDescription());
                        db.editNote(note_edit);
                    }else {
                        db.addNote(note);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utility.displayToast(InsertNoteActivity.this, getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                            finish();
                        }
                    });
                } else {
                    Utility.displayToast(getBaseContext(), "لطفا یادداشتی وارد کنید!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void BackButoon() {
        final String title = txt_title_inNote.getText().toString();
        final String description = txt_description_inNote.getText().toString();
        if (!title.equals("") || !description.equals("")) {
            if(!note_edit.getNoteTitle().equals(title) || !note_edit.getNoteDescription().equals(description))
            {
                DialogPopUp.show(InsertNoteActivity.this, note_edit!=null?"آیا تغییرات ذخیره شود؟":"آیا ذخیره شود؟", getString(R.string.btn_Yes), getString(R.string.btn_No), true, false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (DialogPopUp.isUp()) {
                                Thread.sleep(500);
                            }
                            if (!DialogPopUp.isUp()) {
                                Thread.currentThread().interrupt();//meysam
                                if (DialogPopUp.dialog_result == 1) {
                                    //first button clicked
                                    NoteModel note = new NoteModel();
                                    note.setNoteTitle(title);
                                    note.setNoteDescription(description);
                                    note.setNoteDateTime(Utility.convertDateGorgeian2Persian(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                                    if(note_edit!=null){
                                        //edit
                                        //meysam - edit note
                                    }else {
                                        db.addNote(note);
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utility.displayToast(InsertNoteActivity.this, getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                                        }
                                    });
                                    InsertNoteActivity.this.finish();

                                } else if (DialogPopUp.dialog_result == 2) {
                                    //second button clicked
                                    finish();
                                } else {
                                    //do nothing
                                }
                                DialogPopUp.hide();
                            }
                        } catch (InterruptedException e) {
                            //  Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else
            {
                finish();
            }
        }else{
            finish();
        }
    }

    private void clearFields() {
        txt_description_inNote.setText("");
        txt_title_inNote.setText("");
    }

    @Override
    public void onBackPressed() {
        BackButoon();
        super.onBackPressed();
    }
}
