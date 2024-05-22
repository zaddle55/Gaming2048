package util.comparator;

import model.Save;

import java.util.Comparator;

public class CompareByScore implements Comparator<Save> {

        @Override
        public int compare(Save o1, Save o2) {
            if (o1.getScore() != o2.getScore()) {
                return o1.getScore() - o2.getScore();
            }
            return o1.getStep() - o2.getStep();
        }
}
