package cluckinbell.com

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_music.*
import java.lang.Exception

class MusicActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        player = MediaPlayer.create(this, R.raw.cluckin_bell)
        player.isLooping = true
        player.setVolume(0.5f, 0.5f)
        totalTime = player.duration

        volume_bar.setOnSeekBarChangeListener(
            object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser) {
                        var volumeNum = progress / 100.0f
                        player.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        progress_bar.max = totalTime
        progress_bar.setOnSeekBarChangeListener(
            object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    player.seekTo(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        Thread(Runnable {
            while(player != null) {
                try {
                    var msg = Message()
                    msg.what = player.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(10000000)
                } catch (e: Exception) {

                }
            }
        }).start()
    }

    var handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what
            progress_bar.progress = currentPosition
        }
    }

    fun playButtonClick(v: View) {
        if (player.isPlaying) {
            player.pause()
            play_button.setBackgroundResource(R.drawable.play)
        } else {
            player.start()
            play_button.setBackgroundResource(R.drawable.stop)
        }
    }
}
