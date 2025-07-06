package net.nixontnl.levelascension.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.util.XPUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Skills screen – neutral card tiles with a classic Minecraft 3‑D slot effect.
 */
@Environment(EnvType.CLIENT)
public class SkillsScreen extends Screen {

    /* ── layout & colour constants ──────────────────── */
    private static final int COLS   = 2;
    private static final int CELL_W = 160;
    private static final int CELL_H = 24;
    private static final int HPAD   = 14;
    private static final int VPAD   = 24;
    private static final int GAP    = 6;
    private static final int FOOTER = 24;

    private static final int PANEL_BG  = 0xFF2B2D30; // overall background
    private static final int TILE_BG   = 0xFF8B8B8B; // face colour of tile
    private static final int HL        = 0xFFFFFFFF; // highlight (top / left)
    private static final int SH        = 0xFF3C3C3C; // shadow   (bottom / right)
    private static final int BAR_BG    = 0xFF444444;
    private static final int BAR_FG    = 0xFF3ABA3A;

    private static final List<SkillType> ORDER = Arrays.asList(
            SkillType.MINING, SkillType.WOODCUTTING, SkillType.EXCAVATION, SkillType.FARMING,
            SkillType.MELEE,  SkillType.RANGED,      SkillType.COOKING,    SkillType.FISHING,
            SkillType.BEAST_MASTERY, SkillType.ALCHEMY, SkillType.ENCHANTING, SkillType.ARCHAEOLOGY,
            SkillType.SMITHING, SkillType.CRAFTING
    );

    private final int panelW;
    private final int panelH;
    private int x, y;

    public SkillsScreen() {
        super(Text.literal("Skills"));
        int rows = (int) Math.ceil(ORDER.size() / (float) COLS);
        panelW = HPAD * 2 + COLS * CELL_W + (COLS - 1) * GAP;
        panelH = VPAD + rows * (CELL_H + GAP) - GAP + FOOTER;
    }

    @Override protected void init() {
        x = (width  - panelW) / 2;
        y = (height - panelH) / 2;
    }

    @Override public void render(DrawContext ctx, int mx, int my, float dt) {
        /* ── background panel ── */
        ctx.fill(x, y, x + panelW, y + panelH, PANEL_BG);
        ctx.drawBorder(x, y, panelW, panelH, 0xFFFFFFFF);
        ctx.drawText(textRenderer, "Skills",
                x + (panelW - textRenderer.getWidth("Skills")) / 2,
                y + 8, 0xFFFFFF, false);

        /* pull player data */
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        PlayerSkillData data = SkillEventHandler.getSkillData(mc.player.getUuid());
        if (data == null) return;

        int total = 0;

        for (int i = 0; i < ORDER.size(); i++) {
            SkillType type = ORDER.get(i);
            int col = i % COLS;
            int row = i / COLS;
            int cx  = x + HPAD + col * (CELL_W + GAP);
            int cy  = y + VPAD + row * (CELL_H + GAP);

            // 3‑D slot effect
            ctx.fill(cx, cy, cx + CELL_W, cy + CELL_H, TILE_BG); // face
            ctx.fill(cx, cy, cx + CELL_W, cy + 1, HL);           // top highlight
            ctx.fill(cx, cy, cx + 1,     cy + CELL_H, HL);       // left highlight
            ctx.fill(cx, cy + CELL_H - 1, cx + CELL_W, cy + CELL_H, SH);      // bottom shadow
            ctx.fill(cx + CELL_W - 1, cy, cx + CELL_W, cy + CELL_H, SH);      // right shadow

            // icon mapping
            ItemStack icon = switch (type) {
                case MINING        -> new ItemStack(Items.DIAMOND_PICKAXE);
                case WOODCUTTING   -> new ItemStack(Items.DIAMOND_AXE);
                case EXCAVATION    -> new ItemStack(Items.DIAMOND_SHOVEL);
                case FARMING       -> new ItemStack(Items.DIAMOND_HOE);
                case FISHING       -> new ItemStack(Items.FISHING_ROD);
                case COOKING       -> new ItemStack(Items.CAKE);
                case MELEE         -> new ItemStack(Items.DIAMOND_SWORD);
                case RANGED        -> new ItemStack(Items.BOW);
                case BEAST_MASTERY -> new ItemStack(Items.LEAD);
                case ALCHEMY       -> new ItemStack(Items.POTION);
                case ARCHAEOLOGY   -> new ItemStack(Items.BRUSH);
                case ENCHANTING    -> new ItemStack(Items.ENCHANTED_BOOK);
                case SMITHING      -> new ItemStack(Items.ANVIL);
                case CRAFTING      -> new ItemStack(Items.CRAFTING_TABLE);
            };
            ctx.drawItem(icon, cx + 3, cy + 4);

            int lvl  = data.getSkill(type).getLevel();
            int xp   = data.getSkill(type).getXP();
            int next = XPUtils.getXpForLevel(lvl + 1);
            int base = XPUtils.getXpForLevel(lvl);
            float pct = next > base ? (xp - base) / (float)(next - base) : 1f;

            int barX = cx + 26;
            int barW = CELL_W - 70;
            int barY = cy + 8;
            ctx.fill(barX - 1, barY - 1, barX + barW + 1, barY + 7, 0xFF000000);
            ctx.fill(barX, barY, barX + barW, barY + 6, BAR_BG);
            ctx.fill(barX, barY, barX + (int)(barW * pct), barY + 6, BAR_FG);

            String lvlTxt = lvl + " / 99";
            ctx.drawText(textRenderer, lvlTxt,
                    cx + CELL_W - textRenderer.getWidth(lvlTxt) - 6,
                    cy + (CELL_H - textRenderer.fontHeight) / 2, 0xFFFFFF, true);

            total += lvl;

            if (mx >= cx && mx < cx + CELL_W && my >= cy && my < cy + CELL_H) {
                ctx.drawTooltip(textRenderer,
                        Text.literal(type.getName() + " – " + xp + " / " + next + " XP"), mx, my);
            }
        }

        String footer = "Total Level: " + total;
        ctx.drawText(textRenderer, footer,
                x + (panelW - textRenderer.getWidth(footer)) / 2,
                y + panelH - FOOTER + 6, 0xFFFFFF, false);

        super.render(ctx, mx, my, dt);
    }

    @Override public boolean shouldPause() { return false; }
    @Override public void renderBackground(DrawContext ctx,int mx,int my,float dt) { /* no blur */ }
}
