package team.unnamed.hephaestus.bukkit;

import net.kyori.adventure.key.Key;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Vector2Float;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.FontProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FontGenerator {

    private ArrayList<FontProvider> providers = new ArrayList<>();
    private int curChar = 0;

    public FontGenerator() {
        HashMap<String, Integer> advances = new HashMap<>();
        advances.put(" ", 4);
        advances.put("\\u200c", 0);
        providers.add(FontProvider.space(advances));
        System.out.println("Font generator init");
    }

    /**
     * Adds a textured character to the font generator.
     * @return The character equivalent int that this character will have
     */
    public int texturedChar(String textureKey, int height, int ascent) {
        int firstChar = curChar;
        ArrayList<String> chars = new ArrayList<>(1);
        chars.add(String.valueOf((char) curChar++));
        providers.add(FontProvider.bitMap(Key.key(textureKey), height, ascent, chars));
        return firstChar;
    }

    /**
     * Adds a ascii font to the font generator.
     * @return The offset of the font inside this generated font.
     */
    public int asciiFont(String textureKey, int height, int ascent) {
        int firstChar = curChar;
        ArrayList<String> chars = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            chars.add(
                String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) +
                String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) +
                String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) +
                String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++) + String.valueOf((char) curChar++)
            );
        }
        providers.add(FontProvider.bitMap(Key.key(textureKey), height, ascent, chars));
        return firstChar;
    }

    public void ttfFont(String input) {
        providers.add(
            FontProvider.trueType()
                    .file(Key.key(input))
                    .size(10f)
                    .shift(Vector2Float.ZERO)
                    .oversample(1f)
                    .build()
        );
    }

    public void write(ResourcePack pack, @Subst("(?:([a-z0-9_\\-.]+:)?|:)[a-z0-9_\\-./]+") String key) {
        pack.font(Key.key(key), providers);
    }

    public static String formatString(String input, int offset, Object... args) {
        StringBuilder formatted = new StringBuilder(input.formatted(args));
        for (int i = 0; i < formatted.length(); i++) {
            char target = formatted.charAt(i);
            if (target != ' ') {
                formatted.setCharAt(i, (char) (((int) target) + offset));
            }
        }
        return formatted.toString();
    }
}
