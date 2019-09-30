package uz.islom.ui.util

import android.app.LauncherActivity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import timber.log.Timber
import uz.islom.R
import uz.islom.android.string


fun isShortcutCreated(context: Context): Boolean {
    val profileSharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    return profileSharedPreference.getBoolean("shortcut_created", false)
}

fun addShortcut(context: Context) {

    Timber.e("Create shortcut")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val shortcutInfo = ShortcutInfoCompat.Builder(context, "#1")
                    .setIntent(Intent(context, LauncherActivity::class.java)
                            .setAction(Intent.ACTION_MAIN)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addCategory(Intent.CATEGORY_LAUNCHER)
                    )
                    .setShortLabel("okidoki")
                    .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                    .build()
            ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null)
        } else {
            Timber.e("Shortcut is not supported by your launcher")
        }
    } else {
        val shortcutIntent = Intent(context, LauncherActivity::class.java)
                .setAction(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val addIntent = Intent()
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.string(R.string.app_name))
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher))
        addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        context.sendBroadcast(addIntent)
    }

    val profileSharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    profileSharedPreference.edit().putBoolean("shortcut_created", true).apply()

}


