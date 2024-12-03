package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/4/2018.
 */

public class NoteModel
{


    public BigInteger getNoteId() {
        return NoteId;
    }

    public void setNoteId(BigInteger noteId) {
        NoteId = noteId;
    }

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return NoteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        NoteDescription = noteDescription;
    }

    public Integer getNoteCategory() {
        return NoteCategory;
    }

    public void setNoteCategory(Integer noteCategory) {
        NoteCategory = noteCategory;
    }

    public String getNoteDateTime() {
        return NoteDateTime;
    }

    public void setNoteDateTime(String noteDateTime) {
        NoteDateTime = noteDateTime;
    }

    private BigInteger NoteId;
    private String NoteTitle;
    private String NoteDescription;
    private Integer NoteCategory;
    private String NoteDateTime;


    public NoteModel()
    {
        NoteTitle = null;
        NoteDescription = null;
        NoteCategory = null;
        NoteDateTime = null;
    }

    public NoteModel(String noteTitle, String noteDescription, String noteDateTime, Integer noteCategory)
    {
        NoteTitle = noteTitle;
        NoteDescription = noteDescription;
        NoteCategory = noteCategory;
        NoteDateTime = noteDateTime;
    }
}
