package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView

/**
 * @author Gio
 */
class ReportABug : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.report_a_bug.title"))
        helpView.addText(translate("help.report_a_bug.text"))
        helpView.addNode(UrlLabel(translate("help.report_a_bug.url_text"), "https://github.com/iAmGio/chorus/issues"))
        helpView.show()
    }
}