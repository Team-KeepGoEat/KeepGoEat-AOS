package org.keepgoeat.util.binding

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.util.NetworkMonitor

abstract class BindingActivity<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    AppCompatActivity() {
    lateinit var binding: B
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        snackbar = Snackbar.make(binding.root, "네트워크를 연결해 주세요", Snackbar.LENGTH_INDEFINITE)

        collectNetworkState()
    }

    private fun collectNetworkState() {
        NetworkMonitor(this, lifecycleScope).isConnected.flowWithLifecycle(lifecycle)
            .onEach { isConnected ->
                if (isConnected) snackbar?.dismiss()
                else snackbar?.show()
            }.launchIn(lifecycleScope)
    }
}
