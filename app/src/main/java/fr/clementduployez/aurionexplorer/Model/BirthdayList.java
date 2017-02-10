package fr.clementduployez.aurionexplorer.Model;

import java.util.List;

import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;

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

    public boolean isEmpty() {
        return (dailyBirthdays == null || dailyBirthdays.isEmpty()) && (monthlyBirthdays == null || monthlyBirthdays.isEmpty());
    }
}
