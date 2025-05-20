package ru.tim.TgMusicMiniApp.telegram_bot.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.telegram_bot.TelegramBot;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.repo.UserInfoRepository;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotMessageScheduler {

    private final BotMessageService botMessageService;
    private final UserInfoRepository userInfoRepository;
    private final TelegramBot bot;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAllUserMessages(){
        List<Long> userIds = userInfoRepository.getAllUniqueUsers();
        for(Long id : userIds){
            updateAllUsersMessages(id);
        }
    }

    @Async
    public void updateAllUsersMessages(Long userId){
        List<BotMessage> messageList = botMessageService.getAllChatMessages(userId);
        List<BotMessage> updatedList = new ArrayList<>();
        for(BotMessage message : messageList){
            if(message.getHasNextReply().equals(true)){
                Message msg = sendNextButton(userId);
                BotMessage updatedMessage = BotMessage.builder()
                        .messageId(msg.getMessageId())
                        .chatId(msg.getChatId())
                        .hasNextReply(true)
                        .build();
                updatedList.add(updatedMessage);
            }else {
                ForwardMessage forwardMessage = new ForwardMessage();
                forwardMessage.setChatId(userId);
                forwardMessage.setFromChatId(userId);
                forwardMessage.setFromChatId(userId);
                forwardMessage.setMessageId(message.getMessageId());
                forwardMessage.setDisableNotification(true);
                try {
                    Message msg = bot.execute(forwardMessage);
                    BotMessage updatedMessage = BotMessage.builder()
                            .messageId(msg.getMessageId())
                            .chatId(msg.getChatId())
                            .hasNextReply(false)
                            .build();
                    updatedList.add(updatedMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        bot.cleanupChat(userId);
        updatedList.forEach(botMessageService::saveBotMessage);
    }

    public Message sendNextButton(Long userId){
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton nextButton = new InlineKeyboardButton();

        nextButton.setText("Далее ▶️");

        String callbackData = "next_" + userId;
        nextButton.setCallbackData(callbackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(nextButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboard.setKeyboard(rowList);

        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Нажмите кнопку чтобы продолжить прослушивание:");
        message.setReplyMarkup(inlineKeyboard);

        try {
            return bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
