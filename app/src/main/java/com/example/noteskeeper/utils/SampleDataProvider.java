package com.example.noteskeeper.utils;

import com.example.noteskeeper.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleDataProvider {
    private static final String SAMPLE_TEXT_1 = "A Simple Note ";
    private static final String SAMPLE_TEXT_2 = "A material metaphor is the unifying theory \n of a rationalized space and a system of motion. ";
    private static final String SAMPLE_TEXT_3 = "The material is grounded\n in tactile reality, inspired by the study of paper and ink, yet ";

    private static Date getDate(int diffAmount){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND, diffAmount);
        return calendar.getTime();
    }

    public static List<NoteEntity>  getSampleData() {
        List<NoteEntity> notesList = new ArrayList<>();
        notesList.add(new NoteEntity(1,getDate(0),SAMPLE_TEXT_1));
        notesList.add(new NoteEntity(2,getDate(-1),SAMPLE_TEXT_2));
        notesList.add(new NoteEntity(3,getDate(-2),SAMPLE_TEXT_3));
        return notesList;
    }

}
