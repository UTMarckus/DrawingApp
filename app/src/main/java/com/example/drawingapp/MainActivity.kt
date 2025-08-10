package com.example.drawingapp

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.databinding.DialogBrushBinding
import yuku.ambilwarna.AmbilWarnaDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var bindingBrushDialog: DialogBrushBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        bindingBrushDialog = DialogBrushBinding.inflate(layoutInflater)

        bindingMainActivity.drawingView.changeBrushSize(23F)

        bindingMainActivity.brushButton.setOnClickListener { showBrushChooserDialog() }

        bindingMainActivity.blueDarkButton.setOnClickListener(this)
        bindingMainActivity.blueLightButton.setOnClickListener(this)
        bindingMainActivity.greenButton.setOnClickListener(this)
        bindingMainActivity.orangeButton.setOnClickListener(this)
        bindingMainActivity.purpleButton.setOnClickListener(this)
        bindingMainActivity.redButton.setOnClickListener(this)
        bindingMainActivity.yellowButton.setOnClickListener(this)

        bindingMainActivity.undoButton.setOnClickListener(this)
        bindingMainActivity.colorPickerButton.setOnClickListener(this)

    }

    private fun showBrushChooserDialog() {
        val brushDialog =
            Dialog(this@MainActivity).apply { setContentView(bindingBrushDialog.root) }
        val seekBarProgress = bindingBrushDialog.dialogSeekBar
        val showProgressTV = bindingBrushDialog.dialogTvProgress

        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.blue_dark_button -> bindingMainActivity.drawingView.setBrushColor("#0099CC")
            R.id.blue_light_button -> bindingMainActivity.drawingView.setBrushColor("#33b5e5")
            R.id.green_button -> bindingMainActivity.drawingView.setBrushColor("#99CC00")
            R.id.orange_button -> bindingMainActivity.drawingView.setBrushColor("#FFBB33")
            R.id.purple_button -> bindingMainActivity.drawingView.setBrushColor("#AA66CC")
            R.id.red_button -> bindingMainActivity.drawingView.setBrushColor("#FF4444")
            R.id.yellow_button -> bindingMainActivity.drawingView.setBrushColor("#F8FF37")

            R.id.undo_button -> bindingMainActivity.drawingView.undoPath()
            R.id.color_picker_button -> showColorPickerDialog()
        }
    }

    private fun showColorPickerDialog() {
        val dialog = AmbilWarnaDialog(this, Color.GREEN, object: AmbilWarnaDialog.OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog?) { }

                    override fun onOk(
                        dialog: AmbilWarnaDialog?,
                        color: Int
                    ) {
                        bindingMainActivity.drawingView.setBrushColor(color)
                    }
                })

        dialog.show()
    }
}
