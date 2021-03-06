package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.LorePreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class LorePreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val title = TextField(translate("preview.lore.title_default"))
        title.promptText = translate("preview.lore.title_prompt")
        val textArea = TextArea(selectedText)
        textArea.prefHeight = 80.0
        textArea.promptText = translate("preview.lore.lore_prompt")
        val image = LorePreviewImage(title.text, selectedText)
        val menu = ColoredTextPreviewMenu(translate("preview.lore"), image, listOf(title, textArea))
        val background = image.background
        background.width = 450.0
        background.height = image.flows.size * 21.0 + 18
        title.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(title.text, true).toTextFlow().withStyleClass(image.styleClass)
        }
        textArea.textProperty().addListener {_ ->
            val f1 = menu.image.flows[0]
            val lines = textArea.text.split("\n")
            val flows = lines.map {ChatParser(it, true).toTextFlow().withStyleClass(image.styleClass)}.toMutableList()
            flows.add(0, f1)
            menu.image.flows = flows.toFlowList()
            background.height = flows.size * 21.0 + 18
        }
        menu.toFocus = 1
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}