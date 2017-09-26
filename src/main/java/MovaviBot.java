import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
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

import static com.offbytwo.jenkins.model.BuildResult.BUILDING;
import static com.offbytwo.jenkins.model.BuildResult.SUCCESS;

/**
 * Created by d.lihodedov on 15.09.2017.
 */
public class MovaviBot extends TelegramLongPollingBot {
    JenkinsServer jenkins;
    Map<String, Job> jobs;
    List<String> projects;
    JobWithDetails job;
    ReplyKeyboardMarkup keyboardMarkup;
    List<BuildWithStatus> buildWithStatuses;
    long chat_id;
    List<Build> buildList;
    private static String fromFile = "http://jenkins.movavi.srv/view/Mobile/job/Movavi_VideoEditor_Android/lastSuccessfulBuild/artifact/app/build/outputs/apk/app-armv7a-release.apk";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                jenkins = new JenkinsServer(new URI("http://jenkins.movavi.srv/"), "testrail", "006436088f74df66c6dc51841dda999d");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                jobs = jenkins.getJobs("Mobile");
            } catch (IOException e) {
                e.printStackTrace();
            }
            projects = new ArrayList<String>(jobs.keySet());

//            if (buildWithStatuses.isEmpty())buildWithStatuses=new ArrayList<BuildWithStatus>();


            if (update.getMessage().getText().equals("/start")) {
                chat_id = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText("Custom message text\nNice to meet you friend!");
                keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
//                for (int i=0;i<projects.size();i++){
//                    projects.set(i,projects.get(i).replaceAll("_"," "));
//                }
                for (String project : projects) {
                    KeyboardRow row = new KeyboardRow();
                    row.add(project);
                    keyboard.add(row);
                }
//                 Set the keyboard to the markup
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

            for (int i = 0; i < jobs.size(); i++) {
                if (update.getMessage().getText().equals(projects.get(i))) {
                    try {
                        job = jobs.get(projects.get(i)).details();
                    } catch (IOException e) {
                    }
                    keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
//                    buildWithStatuses = new ArrayList<BuildWithStatus>(buildList.size());
                    buildList = job.getBuilds();
                    List<BuildWithStatus> BuildWithStatuses=new ArrayList<BuildWithStatus>();
                    for (int m = 0; m < buildList.size(); m++) {
                        String status = "✖️";
                        KeyboardRow row = new KeyboardRow();
                        try {
                            if (buildList.get(m).details().getResult() == SUCCESS) status = "✔️";
                            else if (buildList.get(m).details().getResult() == BUILDING) status = "⌛";
                            BuildWithStatus s = new BuildWithStatus(status, buildList.get(m).getNumber());
                            buildWithStatuses.add(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        row.add(buildWithStatuses.get(m).GetName());
                        keyboard.add(row);
                    }


//                    for (int j=0;j<buildList.size();j++){
//                        KeyboardRow row = new KeyboardRow();
//
//                        try {
//                           if (buildList.get(j).details().getResult()==SUCCESS) status="✔️";
//                           else if (buildList.get(j).details().getResult()==BUILDING) status="⌛";
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        row.add(status+ "Build: "+String.valueOf(buildList.get(j).getNumber()));
//                        keyboard.add(row);
//
//                    }
                    // Set the keyboard to the markup
                    keyboardMarkup.setKeyboard(keyboard);

                    // Add it to the message
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText("Build list info:");
                    message.setReplyMarkup(keyboardMarkup);
                    try {
                        // Send the message
                        sendMessage(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

//            if (buildList.size()>0)
            for (int i=0;i<buildList.size();i++){
                if (update.getMessage().getText().equals(buildWithStatuses.get(0).GetName())) {
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText("blabla");
                    //message.setReplyToMessageId(update.getMessage().getMessageId());

                    try {
                        // Send the message
                        sendMessage(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }




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
