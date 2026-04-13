package dev.astudio.alpine

import android.view.SurfaceHolder
import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService

class VideoWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = VideoEngine()
    inner class VideoEngine : Engine() {
        private var mediaPlayer: MediaPlayer? = null
        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            mediaPlayer = MediaPlayer().apply {
                val uri = android.net.Uri.parse("android.resource://$packageName/${R.raw.wall}")
                setDataSource(this@VideoWallpaperService, uri)
                setSurface(holder.surface)   // ✅ BEFORE prepare
                isLooping = false
                setVolume(0f, 0f)
                prepare()
                start()
            }
        }

            override fun onVisibilityChanged(visible: Boolean) {
                if (visible) {
                    mediaPlayer?.start()
                } else {
                    mediaPlayer?.pause()
                }
            }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

