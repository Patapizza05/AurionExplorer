package fr.clementduployez.aurionexplorer.MonPlanning;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CalendarHolder extends RecyclerView.ViewHolder {

    private final TextView beginHour;
    private final TextView endHour;
    private final TextView lessonType;
    private final TextView lessonTitle;
    private final TextView lessonRoom;
    private final TextView lessonId;

    public CalendarHolder(View itemView) {
        super(itemView);
        this.beginHour = (TextView) itemView.findViewById(R.id.calendar_begin_hour);
        this.endHour = (TextView) itemView.findViewById(R.id.calendar_end_hour);
        this.lessonType = (TextView) itemView.findViewById(R.id.calendar_lesson_type);
        this.lessonTitle = (TextView) itemView.findViewById(R.id.calendar_lesson_title);
        this.lessonRoom = (TextView) itemView.findViewById(R.id.calendar_lesson_room);
        this.lessonId = (TextView) itemView.findViewById(R.id.calendar_lesson_id);
    }

    public void bind(CalendarInfo calendarInfo) {
        this.beginHour.setText(calendarInfo.getBeginHour());
        this.endHour.setText(calendarInfo.getEndHour());
        this.lessonType.setText(calendarInfo.getLessonType());
        this.lessonTitle.setText(calendarInfo.getLessonTitle());
        this.lessonRoom.setText(calendarInfo.getLessonRoom());
        this.lessonId.setText(calendarInfo.getLessonId());
    }
}
