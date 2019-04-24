package de.ur.mi.android.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {
    private ArrayList<ToDoItem> taskList;
    private Context context;

    public ToDoListAdapter(Context context, ArrayList<ToDoItem> listItems) {
        super(context, R.layout.listitem_tasklist, listItems);

        this.context = context;
        this.taskList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem_tasklist, null);

        }

        ToDoItem task = taskList.get(position);

        if (task != null) {
            TextView taskName = (TextView) v.findViewById(R.id.task_name);
            TextView taskDate = (TextView) v.findViewById(R.id.task_date);

            taskName.setText(task.getName());
            taskDate.setText(task.getFormattedDate());
        }

        return v;
    }

}
