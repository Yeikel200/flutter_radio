package br.com.thyagoluciano.flutterradio

import android.content.Context
import androidx.annotation.NonNull
import br.com.thyagoluciano.flutterradio.player.RadioManager
import br.com.thyagoluciano.flutterradio.player.RadioService
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


class FlutterRadioPlugin : FlutterPlugin, MethodChannel.MethodCallHandler {

    private lateinit var radioManager: RadioManager
    private lateinit var methodChannel: MethodChannel
    private lateinit var eventChannel: EventChannel
    private lateinit var context: Context


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        setupChannels(flutterPluginBinding.binaryMessenger, flutterPluginBinding.applicationContext)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        methodChannel.setMethodCallHandler(null)
        eventChannel.setStreamHandler(null)
    }

    companion object {
        @Deprecated("This static function is optional and equivalent to onAttachedToEngine. " +
            "It supports the old pre-Flutter-1.12 Android projects.",
            replaceWith = ReplaceWith("onAttachedToEngine()"))
        @JvmStatic
        fun registerWith(registrar: Registrar) {
//          val channel = MethodChannel(registrar.messenger(), "flutter_radio")
//          channel.setMethodCallHandler(FlutterRadioPlugin())
            val plugin = FlutterRadioPlugin()
            plugin.setupChannels(registrar.messenger(), registrar.context())

        }

    }

    private fun setupChannels(messenger: BinaryMessenger, context: Context) {
        methodChannel = MethodChannel(messenger, "flutter_radio")
        eventChannel = EventChannel(messenger, "stateStream")

        this.context = context
        val radioService = RadioService(context)
        radioManager = RadioManager(radioService)

        //eventChannel.setStreamHandler(radioService)
        methodChannel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "audioStart" -> {
                radioManager.initPlayer()
                result.success(null)
            }
            "play" -> {
                val url: String? = call.argument("url")
                if (url != null)
                    radioManager.playOrPause(url)
                result.success(null)
            }
            "pause" -> {
                val url: String? = call.argument("url")
                if (url != null)
                    radioManager.playOrPause(url)
                result.success(null)
            }
            "playOrPause" -> {
                val url: String? = call.argument("url")
                if (url != null)
                    radioManager.playOrPause(url)
                result.success(null)
            }
            "stop" -> {
                radioManager.stop()
                result.success(null)
            }
            "isPlaying" -> {
                val play = radioManager.isPlaying()
                result.success(play)
            }
            "getStatus" -> {
                val status = radioManager.getStatus()
                result.success(status)
            }
            "release" -> {
                radioManager.release()
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

}
