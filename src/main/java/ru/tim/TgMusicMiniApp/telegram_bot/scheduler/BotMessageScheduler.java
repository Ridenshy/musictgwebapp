package ru.tim.TgMusicMiniApp.telegram_bot.scheduler;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.telegram_bot.TelegramBot;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.repo.UserInfoRepository;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;
import ru.tim.TgMusicMiniApp.telegram_bot.service.CallBackHandler;
import ru.tim.TgMusicMiniApp.telegram_bot.service.UserInfoService;
import ru.tim.TgMusicMiniApp.telegram_bot.utility.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotMessageScheduler {

    private final BotMessageService botMessageService;
    private final UserInfoService userInfoService;
    private final TelegramBot bot;
    private final CallBackHandler callBackHandler;

    @Scheduled(fixedRate = 60000)
    public void updateAllUserMessages(){
        List<Long> userIds = userInfoService.getAllUniqueUserIds();
        if(!userIds.isEmpty()){
            for(Long id : userIds){
                updateAllUsersMessages(id);
            }
        }
    }

    @Async
    public void updateAllUsersMessages(Long userId){
        List<BotMessage> messageList = botMessageService.getExpiredSoonMessages(userId);
        if(!messageList.isEmpty()){
            List<BotMessage> updatedList = new ArrayList<>();
            for(BotMessage message : messageList){
                if(message.getHasNextReply().equals(true)){
                    Message msg = sendMoveButton(userId);
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
            callBackHandler.cleanupChat(userId);
            updatedList.forEach(botMessageService::saveBotMessage);
        }
    }
    
    public Message sendMoveButton(Long userId){

        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Нажмите Назад/Далее");
        message.setReplyMarkup(KeyBoardUtils.moveButton());

        try {
            return bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
