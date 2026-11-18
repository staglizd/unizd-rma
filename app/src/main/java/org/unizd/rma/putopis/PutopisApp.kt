package org.unizd.rma.putopis

import android.app.Application

class PutopisApp : Application() {
    val container by lazy { AppContainer() }
}
