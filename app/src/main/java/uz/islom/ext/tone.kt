package uz.islom.ext

import android.media.ToneGenerator


fun makeTone(toneGenerator: ToneGenerator, tone : Int){
    toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 150)
}