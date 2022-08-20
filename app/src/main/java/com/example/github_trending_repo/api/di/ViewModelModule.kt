package com.example.github_trending_repo.api.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_trending_repo.MyApplication
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.instanceOrNull
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import com.example.github_trending_repo.ui.home.SharedViewModel

private const val MODULE_NAME = "ViewModel Module"

class ViewModelModule
val viewModelModule = Kodein.Module(MODULE_NAME, false) {

    bindViewModel<SharedViewModel>() with provider {
        SharedViewModel(instance(), MyApplication())
    }

    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(
            kodein.direct
        )
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
    }
}

inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}

inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy { ViewModelProvider(this, direct.instance())[VM::class.java] }
}
inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
    return lazy { ViewModelProvider(this, direct.instance())[VM::class.java] }
}

inline fun <reified VM : ViewModel, T> T.viewModel(activity: FragmentActivity): VM where T : KodeinAware {
    return ViewModelProvider(activity, direct.instance())[VM::class.java]
}
