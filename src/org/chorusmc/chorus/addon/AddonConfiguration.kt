package org.chorusmc.chorus.addon

import org.chorusmc.chorus.configuration.ChorusConfiguration
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

/**
 * @author Gio
 */

class AddonConfiguration : ChorusConfiguration("config.yml") {

    private lateinit var config: Yaml

    private lateinit var map: HashMap<String, Any>

    override fun createIfAbsent(folder: File?) {
        target = File(folder, name)
        if(!target.exists()) target.createNewFile()
        config = Yaml(with(DumperOptions()) {
            defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
            this
        })
        map = config.load(FileInputStream(target)) ?: HashMap()
    }

    override fun getKeys() = map.keys

    override fun get(key: String) = map[key]

    override fun set(key: String, value: String) {
        setWithoutSaving(key, value)
        store()
    }

    override fun setWithoutSaving(key: String, value: String) {
        map[key] = value
    }

    override fun store() {
        config.dump(map, FileWriter(target))
    }
}