package org.unizd.rma.putopis

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** @HiltAndroidApp pokreće generiranje DI grafa. AppContainer više ne postoji. */
@HiltAndroidApp
class PutopisApp : Application()
