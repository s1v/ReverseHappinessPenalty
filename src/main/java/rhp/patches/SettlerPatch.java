package rhp.patches;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.maps.levelData.settlementData.settler.PopulationThought;
import necesse.level.maps.levelData.settlementData.settler.Settler;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Settler.class, name = "getPopulationThough", arguments = int.class)
public class SettlerPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.Argument(0) int totalSettlers) {
        return true; // 元の処理をスキップ
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Argument(0) int totalSettlers, @Advice.Return(readOnly = false) PopulationThought populationThought) {
        if (totalSettlers >= 24) {
            populationThought = new PopulationThought((GameMessage)new LocalMessage("settlement", "hugesettlement"), 24, 40);
            return;
        }
        if (totalSettlers >= 18) {
            populationThought = new PopulationThought((GameMessage)new LocalMessage("settlement", "largesettlement"), 18, 30);
            return;
        }
        if (totalSettlers >= 12) {
            populationThought = new PopulationThought((GameMessage)new LocalMessage("settlement", "averagesettlement"), 12, 20);
            return;
        }
        if (totalSettlers >= 6) {
            populationThought = new PopulationThought((GameMessage)new LocalMessage("settlement", "smallsettlement"), 6, 10);
            return;
        }
        populationThought = new PopulationThought((GameMessage)new LocalMessage("settlement", "tinysettlement"), 0, 0);
    }
}
