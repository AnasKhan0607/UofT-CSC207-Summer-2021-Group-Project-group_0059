import Controller.MessageController;
import Controller.UserLoginController;
import Entity.Message;
import Presenter.MessagePresenter;
import UseCase.MessageManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TestMessage {
    public static void main(String[] args){
        Message msg001 = new Message("これをあなたが読んでいる時、\n         わたしはわたしではないだろう。", "YUKI. N",
                "Kyon", new Date(2008, Calendar.DECEMBER,20), true);
        Message msg002 = new Message("このメッセージが表示されたということば、\n         そこにあなた、わたし、涼宮ハルヒ、朝比奈みくる\n         " +
                "古泉一樹が存在しているはずである。", "YUKI. N",
                "Kyon", new Date(2008, Calendar.DECEMBER,20), true);
        Message msg003 = new Message("それが鍵。\n         あなたは解答をみつけ出した。", "YUKI. N",
                "Kyon", new Date(2008, Calendar.DECEMBER,20), true);
        Message msg004 = new Message("これは緊急脱出プログラムである。\n         起動させる場合はエンターキーを、\n         そうでない場合はそれ以外のキーを選択せよ。", "YUKI. N",
                "Kyon", new Date(2008, Calendar.DECEMBER,20), true);
        Message msg005 = new Message("起動させた場合、\n         あなたは時空修正の機会を得る。\n         ただし成功は保証できない。\n         また帰還の保証もできない。", "YUKI. N",
                "Kyon", new Date(2009, Calendar.DECEMBER,20), true);
        Message msg006 = new Message("このプログラムが起動するのは一度きりである。\n         実行ののち、消去される。 \n" +
                "         非実行が選択された場合は起動せずに消去される。\n         Ready?_ ", "YUKI. N",
                "Kyon", new Date(2009, Calendar.DECEMBER,20), true);
        Message msg007 = new Message("Ready?", "YUKI. N",
                "Kyon", new Date(2008, Calendar.DECEMBER,20), true);
        MessageManager messageManager = new MessageManager();
        ArrayList<Object> temp = new ArrayList<>();
        temp.add("YUKI. N");
        temp.add("Kyon");
        temp.add(new Date(2008, Calendar.DECEMBER,20));
        temp.add("これをあなたが読んでいる時、\n         わたしはわたしではないだろう。");


        //messageManager.addMessage(temp);
        //MessageController m1 = new MessageController("Kyon");
        //m1.readMessage();

        //UserLoginController c1 = new UserLoginController();
        //c1.NormalUserinput();
        /**
        MessagePresenter.printMessage(msg001);
        MessagePresenter.printMessage(msg002);
        MessagePresenter.printMessage(msg003);
        MessagePresenter.printMessage(msg004);
        MessagePresenter.printMessage(msg005);
        MessagePresenter.printMessage(msg006);
         **/
        //MessagePresenter.printMessage(msg007);
    }
}
