package uz.islom.manager

import android.media.AudioManager
import android.media.ToneGenerator

class AppToneManager {

    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 50)

    fun makeZikrTone() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 300)
    }

}