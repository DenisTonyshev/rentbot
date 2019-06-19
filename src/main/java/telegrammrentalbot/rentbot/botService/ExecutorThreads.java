package telegrammrentalbot.rentbot.botService;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThreads {
    private Message message;
    ExecutorThreads(Message message){ this.message = message;}
    public void executeThreads(int numberOfThreads){
        ExecutorService executor= Executors.newFixedThreadPool(numberOfThreads);
            for (int i = 0; i < numberOfThreads; i++){
                CreateThread createThread = new CreateThread(message);
                executor.execute(createThread);
            }
        executor.shutdown();
    }

}

class CreateThread implements Runnable{
    BotWorkingLogic botWorkingLogic = new BotWorkingLogic();
    Message message;

    CreateThread(Message message){this.message = message;}
    @Override
    public void run() {
        botWorkingLogic.TEST_FILL_THE_BASE(message);
    }
}
