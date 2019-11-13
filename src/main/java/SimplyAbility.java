import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;

public class SimplyAbility extends AbilityBot {
    public SimplyAbility(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
    }

    public Ability sayHelloNew() {
        return Ability.builder()
                .name("hellonew")
                .info("says hello new")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("new hello", ctx.chatId()))
                .post(ctx -> silent.send("bye bye", ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 0;
    }
}
