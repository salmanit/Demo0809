package com.sage.demo0809.ui.aliplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alivc.player.AliVcMediaPlayer

import com.sage.demo0809.R
import kotlinx.android.synthetic.main.activity_ali_player_home.*
import com.alivc.player.AccessKey
import com.alivc.player.AccessKeyCallback



class ActivityAliPlayerHome : AppCompatActivity() {
    val URL = "http://livecdn.video.taobao.com/temp/test1466295255657-65e172e6-1b96-4660-9f2f-1aba576d84e8.m3u8"
    var mAliVcMediaPlayer:AliVcMediaPlayer?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ali_player_home)
        AliVcMediaPlayer.init(this,"video_live") { AccessKey("QxJIheGFRL926hFX", "hipHJKpt0TdznQG2J4D0EVSavRH7mR") }
        sv_player.setZOrderOnTop(true)
        btn_list.setOnClickListener {  }
        btn_player2.setOnClickListener {  }
        btn_play_multy.setOnClickListener {
            try {
                if(mAliVcMediaPlayer==null){
                   mAliVcMediaPlayer= AliVcMediaPlayer(applicationContext, sv_player)
                    mAliVcMediaPlayer!!.setStopedListener {
                        println("=======stop")
                    }
                    mAliVcMediaPlayer!!.setFrameInfoListener {
                        println("=======stop=====")
                        println("=========stop====${mAliVcMediaPlayer!!.duration}++++${mAliVcMediaPlayer!!.bufferPosition}++++" +
                                "${mAliVcMediaPlayer!!.videoWidth}++++${mAliVcMediaPlayer!!.videoHeight}")
                    }
                    mAliVcMediaPlayer!!.prepareAndPlay(URL)

                }else{
                    if(!mAliVcMediaPlayer!!.isPlaying){
                        mAliVcMediaPlayer!!.play()
                    }else{
                        mAliVcMediaPlayer!!.pause()
                    }
                }



            } catch(e: Exception) {
                e.printStackTrace()
            }
        }



    }
}
