package team.unnamed.hephaestus.bukkit

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.math.roundToInt

object UIUtils {
    val characterSizes = intArrayOf(
        /* */0, /*☺*/0, /*☻*/0, /*♥*/0, /*♦*/0, /*♣*/0, /*♠*/0, /**/0, /* */ 0, /*\t*/0, /**/0, /*♂*/0, /*♀*/0, /**/0, /*♫*/0, /*☼*/0,
        /*►*/0, /*◄*/0, /*↕*/0, /*‼*/0, /*¶*/0, /*§*/0, /*▬*/0, /*↨*/0, /*↑*/0, /*↓*/0, /*→*/0, /*←*/0, /*∟*/0, /*↔*/0, /*▲*/0, /*▼*/0,
        /* */4, /*!*/2, /*"*/0, /*#*/0, /*$*/6, /*%*/6, /*&*/0, /*'*/0, /*(*/4, /*)*/4, /***/0, /*+*/6, /*,*/2, /*-*/6, /*.*/2, /* / */6,
        /*0*/6, /*1*/6, /*2*/6, /*3*/6, /*4*/6, /*5*/6, /*6*/6, /*7*/6, /*8*/6, /*9*/6, /*:*/2, /*;*/0, /*<*/5, /*=*/5, /*>*/5, /*?*/6,
        /*@*/7, /*A*/6, /*B*/6, /*C*/6, /*D*/6, /*E*/6, /*F*/6, /*G*/6, /*H*/6, /*I*/4, /*J*/6, /*K*/6, /*L*/6, /*M*/6, /*N*/6, /*O*/6,
        /*P*/6, /*Q*/6, /*R*/6, /*S*/6, /*T*/6, /*U*/6, /*V*/6, /*W*/6, /*X*/6, /*Y*/6, /*Z*/6, /*[*/2, /*\*/0, /*]*/2, /*^*/0, /*_*/6,
        /*`*/0, /*a*/6, /*b*/6, /*c*/6, /*d*/6, /*e*/6, /*f*/5, /*g*/6, /*h*/6, /*i*/2, /*j*/6, /*k*/5, /*l*/3, /*m*/6, /*n*/6, /*o*/6,
        /*p*/6, /*q*/6, /*r*/6, /*s*/6, /*t*/4, /*u*/6, /*v*/6, /*w*/6, /*x*/6, /*y*/6, /*z*/6, /*{*/0, /*|*/0, /*}*/0, /*~*/0, /*⌂*/0,
        /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6,
        /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6, /*?*/6,
        /*á*/0, /*í*/0, /*ó*/0, /*ú*/0, /*ñ*/0, /*Ñ*/0, /*ª*/0, /*º*/0, /*¿*/0, /*®*/4, /*¬*/0, /*½*/0, /*¼*/0, /*¡*/0, /*«*/0, /*»*/0,
        /*░*/0, /*▒*/0, /*▓*/0, /*│*/0, /*┤*/0, /*Á*/0, /*Â*/0, /*À*/0, /*©*/0, /*╣*/0, /*║*/0, /*╗*/0, /*╝*/0, /*¢*/0, /*¥*/0, /*┐*/0,
        /*└*/0, /*┴*/0, /*┬*/0, /*├*/0, /*─*/0, /*┼*/0, /*ã*/0, /*Ã*/0, /*╚*/0, /*╔*/0, /*╩*/0, /*╦*/0, /*╠*/0, /*═*/0, /*╬*/0, /*¤*/0,
        /*ð*/0, /*Ð*/0, /*Ê*/0, /*Ë*/0, /*È*/0, /*ı*/0, /*Í*/0, /*Î*/0, /*Ï*/0, /*┘*/0, /*┌*/0, /*█*/0, /*▄*/0, /*¦*/0, /*Ì*/0, /*▀*/0,
        /*Ó*/0, /*ß*/0, /*Ô*/0, /*Ò*/0, /*õ*/0, /*Õ*/0, /*µ*/0, /*þ*/0, /*Þ*/0, /*Ú*/0, /*Û*/0, /*Ù*/0, /*ý*/0, /*Ý*/0, /*¯*/0, /*´*/0,
        /*­*/0, /*±*/0, /*‗*/0, /*¾*/0, /*¶*/0, /*§*/0, /*÷*/6, /*¸*/0, /*°*/0, /*¨*/0, /*·*/0, /*¹*/0, /*³*/0, /*²*/0, /*■*/0, /* */0
    )

    val positiveCharacters = mapOf(
        Pair(1024, '\uF82F'),
        Pair(512, '\uF82E'),
        Pair(256, '\uF82D'),
        Pair(128, '\uF82C'),
        Pair(64, '\uF82B'),
        Pair(32, '\uF82A'),
        Pair(16, '\uF829'),
        Pair(8, '\uF828'),
        Pair(7, '\uF827'),
        Pair(6, '\uF826'),
        Pair(5, '\uF825'),
        Pair(4, '\uF824'),
        Pair(3, '\uF823'),
        Pair(2, '\uF822'),
        Pair(1, '\uF821')
    )

    val negativeCharacters = mapOf(
        Pair(1024, '\uF80F'),
        Pair(512, '\uF80E'),
        Pair(256, '\uF80D'),
        Pair(128, '\uF80C'),
        Pair(64, '\uF80B'),
        Pair(32, '\uF80A'),
        Pair(16, '\uF809'),
        Pair(8, '\uF808'),
        Pair(7, '\uF807'),
        Pair(6, '\uF806'),
        Pair(5, '\uF805'),
        Pair(4, '\uF804'),
        Pair(3, '\uF803'),
        Pair(2, '\uF802'),
        Pair(1, '\uF801')
    )

    var cache: HashMap<Int, String> = HashMap()
    fun createOffset(offset: Int): String {
        var cacheResult: String? = cache[offset]
        if (cacheResult != null) return cacheResult

        var currentOffset: Int = offset

        val result = StringBuilder()
        if (offset > 0) {
            for ((key, value) in positiveCharacters.entries) {
                while (currentOffset >= key) {
                    result.append(value)
                    currentOffset -= key
                }
                if (currentOffset == 0) break
            }
        } else {
            currentOffset = -offset
            for ((key, value) in negativeCharacters.entries) {
                while (currentOffset >= key) {
                    result.append(value)
                    currentOffset -= key
                }
                if (currentOffset == 0) break
            }
        }

        cacheResult = result.toString()
        cache[offset] = cacheResult;
        return cacheResult
    }
}

fun String.mcWidth(): Int {
    var width = this.chars().map { UIUtils.characterSizes[it] }.sum()
    width -= count { it == ' ' } * 2
    return width
}

fun TextComponent.Builder.text(
    text: String,
    font: Int, x: Int,
    shadowFont: Int? = null,
    mult: Float = 0.5f,
    textHeight: Int = 8
) {
    val width = text.mcWidth() * (textHeight.toFloat() / 8f)

    // shadow text
    if (shadowFont != null) {
        append(
            Component.text(
                UIUtils.createOffset(-(width * mult).roundToInt() - (x - 1)) +
                        FontGenerator.formatString(text, shadowFont) +
                        UIUtils.createOffset(x - 1 - (width * mult).roundToInt())
            ).color(TextColor.color(0, 0, 0))
        )
    }

    // non-shadow text
    append(
        Component.text(
            UIUtils.createOffset(-(width * mult).roundToInt() - x) +
            FontGenerator.formatString(text, font) +
                    UIUtils.createOffset(x + 1 - (width * (1f - mult)).roundToInt())
        ).color(TextColor.color(255, 255, 255))
    )
}

fun TextComponent.Builder.button(
    buttonTextures: List<List<Int>>,
    x: Int, y: Int,
    width: Int, height: Int,
    color: TextColor
) {
    // loop through width and height
    (0 until width).forEach { preX ->
        val x = (preX + x) * 18
        (0 until height).forEach { preY ->
            // calculate sample
            val top = if (preY == 0) 1 else 0
            val bottom = if (preY == height - 1) 1 else 0
            val left = if (preX == width - 1) 1 else 0
            val right = if (preX == 0) 1 else 0
            val sample = top + (bottom * 2) + (left * 4) + (right * 8)

            // add text
            append(
                Component.text(
                    UIUtils.createOffset(-18 - 1 - x) +
                    String(charArrayOf(buttonTextures[sample][preY + y].toChar())) +
                            UIUtils.createOffset(x)
                ).color(color)
            )
        }
    }
}

fun TextComponent.Builder.char(
    char: Int,
    x: Int,
    width: Int,
    color: TextColor = TextColor.color(255, 255, 255)
) {
    append(
        Component
            .text(
                UIUtils.createOffset(-x) +
                        char.toChar() +
                        UIUtils.createOffset(x + -(width + 1))
            )
            .color(color)
    )
}

fun TextComponent.Builder.offset(offset: Int) {
    append(Component.text(UIUtils.createOffset(offset)))
}

fun Inventory.clickable(
    tooltip: String,
    x: Int, y: Int,
    width: Int, height: Int
) {
    (x until x + width).forEach { x ->
        (y until y + height).forEach { y ->
            val item = ItemStack(Material.ACACIA_BOAT)
            val meta = item.itemMeta
            meta.setDisplayName(tooltip)
            item.setItemMeta(meta)
            setItem(y * 9 + x, item)
        }
    }
}
