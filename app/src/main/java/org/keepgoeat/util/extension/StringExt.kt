package org.keepgoeat.util.extension

import java.text.BreakIterator

/** 사용자가 인식하는 문자열의 글자 수를 반환하는 함수 */
fun String?.getStringLength(): Int {
    if (this == null) return 0
    val it: BreakIterator = BreakIterator.getCharacterInstance()
    it.setText(this)
    var count = 0
    while (it.next() != BreakIterator.DONE) {
        count++
    }
    return count
}
