package ru.modulkassa.findgoods.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import ru.modulkassa.findgoods.ui.shared.BaseActivity
import timber.log.Timber

class ScanActivity : BaseActivity(), ResultHandler {
    companion object {
        private const val ZXING_CAMERA_PERMISSION = 1
        const val BARCODE_RESULT = "BARCODE_RESULT"
        const val SUCCESS = 1
        const val CANCEL = 2
    }

    lateinit var scanView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanView = ZXingScannerView(this)
        setContentView(scanView)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == ZXING_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Timber.i("camera is available")
            } else {
                Toast.makeText(this, "Please grant camera permission to use the QR Scanner",
                    Toast.LENGTH_SHORT).show()
            }
            return
        }
    }

    override fun handleResult(result: Result) {
        Timber.i("Text from scanner: ${result.text}")
        val intent = Intent()
        intent.putExtra(BARCODE_RESULT, result.text)
        setResult(SUCCESS, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        scanView.setResultHandler(this)
        scanView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scanView.stopCamera()
    }

}
