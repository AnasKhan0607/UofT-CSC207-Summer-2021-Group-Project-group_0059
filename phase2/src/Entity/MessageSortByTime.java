package Entity;

import java.util.Comparator;
/**
 * Represents a Comparator for the message(by time)
 * @author Ahmad I., Ruilin P.
 */
public class MessageSortByTime implements Comparator<Message> {
    /**
     * compare two given messages of which is composed earlier
     * @param m1 Message 1
     * @param m2 Message 2
     * @return a integer, 1 meaning m1 is composed later than m2, -1 meaning the opposite, while 0 means composed at the same time
     */
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
