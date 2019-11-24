package uz.islom.manager

import android.media.AudioManager
import android.media.ToneGenerator

class AppToneManager {

    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 30)

    fun makeZikrSimpleTone() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 300)
    }

    fun makeZikrTypeChanedTone() {
        toneGenerator.startTone(ToneGenerator.TONE_DTMF_A, 300)
    }
}