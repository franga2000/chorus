package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.TextField
import org.chorusmc.chorus.util.config

/**
 * @author Gio
 */
class SettingTextField : TextField() {

    var regex: Regex? = null

    init {
        textProperty().addListener {_ ->
            if(text.isNotEmpty()) {
                if(regex != null && text.contains(regex!!)) {
                    text = text.replace(regex!!, "")
                }
                if(regex.toString() == "[^0-9]") {
                    if(super.getText().length > 9) {
                        super.setText(super.getText().substring(0, 9))
                    }
                }
                config.set(id, text)
            }
        }
    }
}