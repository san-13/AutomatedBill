package com.example.automatedbill

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.automatedbill.databinding.ActivityScanBinding

class Scan : AppCompatActivity() {
    private lateinit var binding:ActivityScanBinding
    private var imagecapture:ImageCapture?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //camera permission request
        if(!permissions()){
            ActivityCompat.requestPermissions(
                this,Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.dashboardbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    //start camera with permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== Constants.REQUEST_CODE_PERMISSIONS){
            if(permissions()){
                startCamera()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture=ProcessCameraProvider
            .getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider:ProcessCameraProvider=cameraProviderFuture.get()
            val preview=Preview.Builder()
                .build()
                .also{ mPreview->
                    mPreview.setSurfaceProvider(
                        binding.campreview.surfaceProvider
                    )
                }
            imagecapture=ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,cameraSelector,preview,imagecapture
                )
            }
            catch (e:Exception){
                Log.d(Constants.TAG,"Camera Failure",e)
            }

        },ContextCompat.getMainExecutor(this))
    }

    //check for camera permission
    private fun permissions()=Constants.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext,it
        )==PackageManager.PERMISSION_GRANTED
    }
}