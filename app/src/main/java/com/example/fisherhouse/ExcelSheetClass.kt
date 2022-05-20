package com.example.fisherhouse

import java.util.jar.JarEntry

class ExcelSheetClass {

    var prefilled_link: String = ""


    var entries = mutableMapOf<String, String>()

    fun setEntry(entryCode: String, value : String) {
        var formattedValue = formatValues(value)
        var formattedEntryCode: String = ""
        if (entries.isEmpty()) {
            formattedEntryCode = "?$entryCode="
        }
        else {
            formattedEntryCode = formatEntry(entryCode)
        }

        entries[formattedEntryCode] = formattedValue
    }

    fun CompleteLink(): String {

        var completeLink = prefilled_link

        entries.forEach { (entryCode, value) ->
            completeLink += entryCode +  value
        }

        return completeLink
    }

    fun formatEntry(text: String): String {

        var text = "&$text="
        return text
    }

    fun formatValues(valu: String): String {
        return valu.replace(" ", "_").replace("&", "AND").replace(",", "")
            .replace("%", "Percent").replace('"', ' ').replace("'", "")
            .replace("<", "").replace(">", "").replace("?", "").replace("=", "")

    }

}