package Entity;

import java.util.Comparator;

public class MessageSortByTime implements Comparator<Message> {
    public int compare(Message m1, Message m2) {
        if (m1.getTime().after(m2.getTime())) {
            return 1;
        } else if (m2.getTime().after(m1.getTime())) {
            return -1;
        } else {
            return 0;
        }

    }
}
