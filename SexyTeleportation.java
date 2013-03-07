//package org.trepix.sexyscripts.sexyammsilver.startup;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.trepix.sexyscripts.sexyammsilver.util.Log;
import org.trepix.sexyscripts.sexyammsilver.util.MyActionBarActions;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 1/14/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */

@Manifest(authors = {"SDN (Azaxirus)"},
        name = "Sexy Teleportation",
        version = 0.2,
	topic = 901060,
	website = "http://www.powerbot.org/community/topic/901060-sdn-sexy-teleportation-gain-magic-xp-like-a-boss-free/",
        description = "Teleports for you for magic xp, place the teleport you want to use in the first slot of your ability bar")
public class SexyTeleportation extends ActiveScript implements PaintListener {

    String status;
    private final long startTime = System.currentTimeMillis();
    private int startXP;
    public static final WidgetChild TeleportOnAbilityBar = new Widget(640).getChild(0);


    @Override
    public int loop() {
        if (Game.getClientState() != 12) {
            if (!Players.getLocal().isMoving()) {
                if (MyActionBarActions.isActionBarOpenPTS()){
                    status = "Teleporting";
                    Mouse.click(TeleportOnAbilityBar.getCentralPoint(), true);
                    Task.sleep(2000);
                } else {
                    status = "Opening action bar";
                    MyActionBarActions.openActionBar();
                }
            }
        }

        return Random.nextInt(1,1);
    }

    public void onStart() {
        startXP = Skills.getExperience(Skills.MAGIC);
        Log.log("SUCCESS", "Starting Sexy Teleportation");
    }

    private int getPerHour(final long value) {
        return (int) (value * 3600000D / (System.currentTimeMillis() - startTime));
    }



    private final DecimalFormat df = new DecimalFormat("###,###,###,###");

    private final Color color1 = new Color(0, 51, 204, 77);
    private final Color color2 = new Color(0, 0, 0);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Trajan Pro", 1, 17);
    private final Font font2 = new Font("Trajan Pro", 0, 12);

    private int xpGained;
    private String xpPH;

    public void onRepaint(Graphics g1) {

        long millis = System.currentTimeMillis() - startTime;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;

        xpPH = df.format(getPerHour(xpGained));
        xpGained = Skills.getExperience(Skills.MAGIC) - startXP;

        Graphics2D g = (Graphics2D)g1;
        g.setColor(color1);
        g.fillRect(267, 382, 229, 131);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(267, 382, 229, 131);
        g.setFont(font1);
        g.drawString("Sexy Teleportation", 289, 402);
        g.setFont(font2);
        g.drawString("Time Ran:" + hours + ":" + minutes + ":" + seconds, 271, 424);
        g.drawString("XP Gained:" + xpGained, 271, 447);
        g.drawString("XP P/H:" + xpPH, 271, 467);
        g.drawString("Status:" + status, 271, 489);
    }

    public static String formatTime(final long milliseconds)
    {
        final long t_seconds = milliseconds / 1000;
        final long t_minutes = t_seconds / 60;
        final long t_hours = t_minutes / 60;
        final long seconds = t_seconds % 60;
        final long minutes = t_minutes % 60;
        final long hours = t_hours % 500;

        return hours + ":" + minutes + ":" + seconds;
    }

}
