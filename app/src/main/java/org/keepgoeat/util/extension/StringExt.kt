package org.keepgoeat.util.extension

import java.text.BreakIterator

fun String?.getStringLength(): Int {
    val it: BreakIterator = BreakIterator.getCharacterInstance()
    it.setText(this)
    var count = 0
    while (it.next() != BreakIterator.DONE) {
        count++
    }
    return count
}
