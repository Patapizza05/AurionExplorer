package fr.clementduployez.aurionexplorer.Anniversaires;

import java.util.List;

/**
 * Created by cdupl on 3/22/2016.
 */
public class BirthdayList {

    private List<BirthdayInfo> dailyBirthdays = null;
    private List<BirthdayInfo> monthlyBirthdays = null;

    public List<BirthdayInfo> getDailyBirthdays() {
        return dailyBirthdays;
    }

    public void setDailyBirthdays(List<BirthdayInfo> dailyBirthdays) {
        this.dailyBirthdays = dailyBirthdays;
    }

    public List<BirthdayInfo> getMonthlyBirthdays() {
        return monthlyBirthdays;
    }

    public void setMonthlyBirthdays(List<BirthdayInfo> monthlyBirthdays) {
        this.monthlyBirthdays = monthlyBirthdays;
    }
}
