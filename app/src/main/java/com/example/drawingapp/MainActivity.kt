package com.example.drawingapp

import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.databinding.DialogBrushBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var bindingBrushDialog: DialogBrushBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        bindingBrushDialog = DialogBrushBinding.inflate(layoutInflater)

        bindingMainActivity.drawingView.changeBrushSize(23F)

        bindingMainActivity.brushButton.setOnClickListener { showBrushChooserDialog() }
    }

    private fun showBrushChooserDialog() {
        val brushDialog = Dialog(this@MainActivity).apply { setContentView(bindingBrushDialog.root) }
        val seekBarProgress = bindingBrushDialog.dialogSeekBar
        val showProgressTV = bindingBrushDialog.dialogTvProgress

        seekBarProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                bindingMainActivity.drawingView.changeBrushSize(seekBar.progress.toFloat())
                showProgressTV.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        brushDialog.show()
    }
}
