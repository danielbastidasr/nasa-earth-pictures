package daniel.bastidas.earthnasa.common

import android.app.Application
import daniel.bastidas.earthnasa.BuildConfig
import daniel.bastidas.earthnasa.common.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@AndroidApplication)
            modules(koinModules)
        }
    }
}

