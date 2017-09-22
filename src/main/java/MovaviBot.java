import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.apache.commons.codec.binary.Base64;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by d.lihodedov on 15.09.2017.
 */
public class MovaviBot extends TelegramLongPollingBot {
    JenkinsServer jenkins;
    Map<String, Job> jobs;
    private static String fromFile = "http://jenkins.movavi.srv/view/Mobile/job/Movavi_VideoEditor_Android/lastSuccessfulBuild/artifact/app/build/outputs/apk/app-armv7a-release.apk";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")){
                long chat_id = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText("Custom message text\nNice to meet you friend!");
                ReplyKeyboardMarkup keyboardMarkup=new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard=new ArrayList<KeyboardRow>();
                KeyboardRow row = new KeyboardRow();
                try {
                    jenkins = new JenkinsServer(new URI("http://jenkins.movavi.srv/"), "testrail", "006436088f74df66c6dc51841dda999d");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    jobs=jenkins.getJobs("Mobile");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<String> projects = new ArrayList<String>(jobs.keySet());





                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboard);
                // Add it to the message
                message.setReplyMarkup(keyboardMarkup);
                try {
                    // Send the message
                    sendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                            }

            }
//            if (!message_text.equals("/build")){
//                try {
//                    SendMessage message3 = new SendMessage() // Create a message object object
//                            .setChatId(chat_id)
//                            .setText("If you would like to get latest android build, try to send /build to me" );
//                    sendMessage(message3);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
            }





    public static int countWords(String s){

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "";
    }




}
