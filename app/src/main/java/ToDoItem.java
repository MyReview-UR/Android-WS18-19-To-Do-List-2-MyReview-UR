package de.ur.mi.android.todolist;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ToDoItem implements Comparable<ToDoItem> {

    private String name;
    private GregorianCalendar cal;

    public ToDoItem(String name, int day, int month, int year) {
        this.name = name;
        cal = new GregorianCalendar(year, month, day);
    }


    public String getName() {
        return name;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);

        return df.format(cal.getTime());
    }

    private Date getDueDate() {
        return cal.getTime();
    }

    @Override
    public int compareTo(ToDoItem another) {
        return getDueDate().compareTo(another.getDueDate());

    }

}
