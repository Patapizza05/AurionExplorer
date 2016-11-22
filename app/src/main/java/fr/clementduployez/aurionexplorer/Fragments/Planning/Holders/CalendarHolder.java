package fr.clementduployez.aurionexplorer.Fragments.Planning.Holders;

import android.view.View;
import android.widget.TextView;

import fr.clementduployez.aurionexplorer.Ui.Holders.AurionHolder;
import fr.clementduployez.aurionexplorer.Model.CalendarInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CalendarHolder extends AurionHolder<CalendarInfo> {

    private View container;
    private final TextView beginHour;
    private final TextView endHour;
    private final TextView lessonType;
    private final TextView lessonTitle;
    private final TextView lessonRoom;
    private final TextView lessonId;

    public CalendarHolder(View itemView) {
        super(itemView);
        this.container = itemView.findViewById(R.id.calendar_recycler_item_container);
        this.beginHour = (TextView) itemView.findViewById(R.id.calendar_begin_hour);
        this.endHour = (TextView) itemView.findViewById(R.id.calendar_end_hour);
        this.lessonType = (TextView) itemView.findViewById(R.id.calendar_lesson_type);
        this.lessonTitle = (TextView) itemView.findViewById(R.id.calendar_lesson_title);
        this.lessonRoom = (TextView) itemView.findViewById(R.id.calendar_lesson_room);
        this.lessonId = (TextView) itemView.findViewById(R.id.calendar_lesson_id);
    }

    @Override
    public void bind(CalendarInfo calendarInfo) {
        this.beginHour.setText(calendarInfo.getBeginHour());
        this.endHour.setText(calendarInfo.getEndHour());
        this.lessonType.setText(calendarInfo.getLessonType());

        String title = calendarInfo.getLessonTitle();
        if (calendarInfo.getDescription() != null) {
            title += " - " + calendarInfo.getDescription();
        }
        if (calendarInfo.getTeacher() != null) {
            title += " - " + calendarInfo.getTeacher();
        }

        this.lessonTitle.setText(title);
        this.lessonRoom.setText(calendarInfo.getLessonRoom());
        this.lessonId.setText(calendarInfo.getLessonId());

        String type = calendarInfo.getLessonType();

        if (type != null && (type.equals("Examen") || type.equals("Partiel"))) {
            this.container.setBackgroundResource(R.drawable.ripple_yellow_background);
        }
        else {
            this.container.setBackgroundResource(R.drawable.ripple_background);
        }
    }
}
