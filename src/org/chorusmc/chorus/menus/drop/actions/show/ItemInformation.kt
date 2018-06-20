package org.chorusmc.chorus.menus.drop.actions.show

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.ItemInformationBox

/**
 * @author Gio
 */
class ItemInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val selected = area.selectedText
        val path: String
        val item: Item
        if(selected.matches(Regex(EditorPattern.ITEM.pattern))) {
            item = McClass("Item").valueOf(selected.split(":")[0]) as Item
            path = if(selected.contains(":"))
                item.id.toString() + "_" + selected.split(":")[1] else item.id.toString() + "_0"
        } else {
            path = if(selected.contains(":"))
                selected.replace(":", "_") else selected + "_0"
            @Suppress("UNCHECKED_CAST")
            item = IdAble.byId(McClass("Item").cls as Class<out IdAble>, selected.split(":")[0].toShort()) as Item
        }
        val input = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/$path.png")
        val box = ItemInformationBox(if(input == null) null else Image(input), item)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}