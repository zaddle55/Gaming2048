package util.comparator;

import model.Save;

import java.util.Comparator;

public class CompareByTime implements Comparator<Save> {

        @Override
        public int compare(Save o1, Save o2) {
            if (o1.saveDate.compareTo(o2.saveDate) != 0) {
                return o1.saveDate.compareTo(o2.saveDate);
            }
            return o1.saveTime.compareTo(o2.saveTime);
        }
}
