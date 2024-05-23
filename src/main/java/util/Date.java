package util;

public class Date implements Comparable<Date>{
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date() {
        java.util.Date date = new java.util.Date();
        this.year = date.getYear() + 1900;
        this.month = date.getMonth() + 1;
        this.day = date.getDate();
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public int compareTo(Date date) {
        if (year != date.year) {
            return year - date.year;
        }
        if (month != date.month) {
            return month - date.month;
        }
        return day - date.day;
    }
}
