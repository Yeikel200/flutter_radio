package br.com.thyagoluciano.flutterradio.player

class RadioManager(private val service: RadioService) {

    private var instance: RadioManager? = null

    //private var service = RadioService(context)

    private var serviceBound = false

    fun initPlayer() {
        service.onCreate()
    }

    fun playOrPause(streamUrl: String) {
        service.playOrPause(streamUrl)
    }

    fun stop() = service.stop()

    fun isPlaying(): Boolean {
        return service.isPlaying()
    }

    fun release() {
        service.release()
    }

    fun getStatus(): String {
        return service.getStatus()
    }

}