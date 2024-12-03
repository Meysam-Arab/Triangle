package ir.fardan7eghlim.tri.Models;

/**
 * Created by Meysam on 26/07/2017.
 */

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Models.SessionModel;

public class SoundModel {

    public static String KAZEM_ZADEH_TRM = "kazem_zadeh_trim";
    public static String AGHATI = "aghati";
    public static String KAZEM_ZADEH = "kazem_zadeh";
    public static String MOAZEN_ZADEH = "moazen_zadeh";

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    private Context cntx;
    private static MediaPlayer mPlayer;

    public static String IS_PLAYING = "playing";
    public static String STOPPED = "stopped";

//    private static boolean specific_sound_playing = false;

    public SoundModel(Context contex)
    {
        cntx = contex;
    }


    public static void stopSound()
    {
        if(mPlayer != null)
        {
            if(mPlayer.isPlaying())
            {
//                thread.stop();
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
        }
    }
    private int getFile(String name)
    {

        if(name.equals(KAZEM_ZADEH_TRM))
            return R.raw.kazem_zadeh_trm;
        if(name.equals(KAZEM_ZADEH))
            return R.raw.kazem_zadeh;
        if(name.equals(AGHATI))
            return R.raw.aghati;
        if(name.equals(MOAZEN_ZADEH))
            return R.raw.moazen_zadeh;

        return R.raw.kazem_zadeh_trm;
    }
    public void playSound(String name)
    {
        if(mPlayer != null)
        {

            if(mPlayer.isPlaying()){
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
//                thread.interrupt();

            }

        }
//        if(!new SessionModel(cntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
//        {
            if( mPlayer == null || !mPlayer.isPlaying())
            {
//                specific_sound_playing = false;
                mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getFile(name));

                mPlayer.start();
            }
//        }
    }

    public static boolean isPlaying()
    {
        if(mPlayer == null)
            return false;
        if(mPlayer.isPlaying())
            return true;
        return false;
    }

//    public static void playSpecificSound(int source, final Context mcntx, boolean continueAfterStop, final Integer afterStopSound, final Float afterStopVolume, Float volume)
//    {
//        if(mPlayer != null)
//        {
//
//            if(mPlayer.isPlaying()){
//                mPlayer.stop();
//                mPlayer.release();
//                mPlayer = null;
////                thread.interrupt();
//
//            }
//
//        }
//
//        if(!new SessionModel(mcntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
//        {
//            if(continueAfterStop)
//            {
//                specific_sound_playing = true;
//                mPlayer = MediaPlayer.create(mcntx.getApplicationContext(), source);
//                mPlayer.setLooping(true);
//                if(volume != null)
//                    mPlayer.setVolume(volume,volume);
//                mPlayer.start();
//
//            }
//            else
//            {
//                specific_sound_playing = true;
//
//                mPlayer = MediaPlayer.create(mcntx.getApplicationContext(), source);
//                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
//                {
//                    @Override
//                    public void onCompletion(MediaPlayer mp)
//                    {
//                        // Code to start the next audio in the sequence
//                        if(!new SessionModel(mcntx.getApplicationContext()).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
//                        {
//                            specific_sound_playing = false;
//                            if(mPlayer != null)
//                            {
//                                mPlayer.stop();
//                                mPlayer.release();
//                                mPlayer = null;
//                            }
//
////                            new SoundModel(mcntx).playCountinuseRandomMusic();
//                            if(afterStopSound != null)
//                            {
////                                if(BaseActivity.bgSound != null)
////                                    BaseActivity.bgSound.playCountinuseRandomMusic();
//                                playSpecificSound(afterStopSound,mcntx,true,null,null,afterStopVolume);
//
//                            }
////                            else
////                                playSpecificSound(afterStopSound,mcntx,true,null,null,afterStopVolume);
//
//
//                        }
//
//                    }
//                });
//                mPlayer.setLooping(false);
//                if(volume != null)
//                    mPlayer.setVolume(volume,volume);
//                mPlayer.start();
//            }
//
//        }
//    }

//    public static void stopSpecificSound()
//    {
//        specific_sound_playing = false;
//        if(mPlayer != null)
//        {
//            mPlayer.setLooping(false);
//            mPlayer.stop();
//            mPlayer.release();
//            mPlayer = null;
//        }
//
//    }
}
