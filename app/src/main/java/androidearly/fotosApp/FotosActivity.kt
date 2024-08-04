package androidearly.primeraApp.fotosApp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidearly.primeraApp.fotosApp.vml.CameraViewModel
import androidearly.primeraApp.fotosApp.vml.PictureClass
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class FotosActivity : ComponentActivity() {
    //variables globales
    private var fotoMaxima = 15
    private lateinit var adapter: CameraAdapter
    private val viewModel by viewModels<CameraViewModel>()
    private var selectedItem: PictureClass? = null
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private var pictureList: ArrayList<PictureClass> = ArrayList()

    //Botones
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: MaterialButton
    private lateinit var takepictureButton: FloatingActionButton

    //Barra de CoordinatorLayout
    private lateinit var toolBar: Toolbar
    private lateinit var picture: ImageView
    private lateinit var recyclerView: RecyclerView

    //TextView
    private lateinit var tipoMoviento: EditText
    private val tag = "TakePictureActivity"

    companion object {
        private const val FILE_PROVIDER_AUTHORITY = "com.coco.primeraapp.provider"
        private const val FILE_PREFIX = "TEV_CASA_LEY_"
        private const val FILE_EXTENSION = ".jpg"
        private const val REQUEST_CAMERA_PERMISSION = 1002

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)
        initComponents()
        initUI()
        setListeners()
    }

    //inicializan los componentes de la interfaz
    private fun initComponents() {
        // Botones
        deleteButton = findViewById(R.id.deleteButton) // Boton para buscar
        saveButton = findViewById(R.id.saveButton) //Boton de guardad
        //Barra de CoordinatorLayout
        toolBar = findViewById(R.id.toolBar)
        takepictureButton = findViewById(R.id.takepictureButton)
        picture = findViewById(R.id.pintures)
        /* -- Recicleview -- */
        adapter = CameraAdapter(pictureList, object : CameraAdapter.SelectionDialogActions {
            override fun onSelected(item: PictureClass) {
                if (selectedItem == item) {
                    selectedItem = null
                    putPictureIntoImageView(null)
                } else {
                    item.bitmap?.let {
                        selectedItem = item
                        putPictureIntoImageView(it)
                    }
                }
            }
        })
        recyclerView = findViewById(R.id.recycler_view)!!
        recyclerView.layoutManager = LinearLayoutManager(this)

        tipoMoviento = findViewById(R.id.tipMov)
        tipoMoviento.filters =
            arrayOf(InputFilter { source, _, _, _, _, _ ->
                if (source != null && source.contains("\n")) {
                    ""
                } else {
                    null
                }
            })
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    private fun initUI() {
        cargaTotalFotos()
    }

    private fun cargaTotalFotos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val list = getTodaysPictures().map { picture ->
                    // Reduce el tamaño de la imagen antes de codificarla en Base64
                    val resizedBitmap = picture.bitmap?.let {
                        Bitmap.createScaledBitmap(
                            it,
                            picture.bitmap!!.width / 2,
                            picture.bitmap!!.height / 2,
                            true
                        )
                    }
                    Base64.encodeToString(getBytesFromBitmap(resizedBitmap), Base64.DEFAULT)
                }
                Log.d(tag, "Se encontraron ${list.size}")
                list.forEach {
                    fillTemporalFileForExistingPhotos()
                    val decodedString: ByteArray = Base64.decode(it, Base64.DEFAULT)
                    val decodedByte =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    runOnUiThread {
                        pictureList.last().bitmap = decodedByte
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.d(tag, "entro $e")
            }
        }
    }

    private fun setListeners() {
        // SE ALMACENA LA FOTOS
        saveButton.setOnClickListener {
            cargaTotalFotos()
        }

        // Boton para eliminar fotos
        deleteButton.setOnClickListener {
            if (selectedItem != null) {
                selectedItem?.let {
                    Log.d(tag, "Se selecciono: $selectedItem")
                    adapter.removeItem(it)
                    selectedItem = null
                    putPictureIntoImageView(null)
                }
            } else {
                Log.d(tag, "Necesitas Seleccionar una images")
            }
        }

        // Boton para tomar fotos
        takepictureButton.setOnClickListener {
            if (adapter.itemCount >= fotoMaxima) {
                Log.d(tag, "No tienes los permisos necesarios para tomar fotos")
            } else {
                launchCamera()
            }
        }

        // Boton de regreso
        toolBar.setNavigationOnClickListener {
            finish()
        }

        // Inicializa el ActivityResultLauncher
        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        if (pictureList.last().file?.exists() == true) {
                            viewModel.getFormattedImage(pictureList.last().file)
                                ?.let { bitmapImage ->
                                    pictureList.last().bitmap = bitmapImage
                                    adapter.notifyItemChanged(adapter.itemCount - 1)
                                }
                        } else {
                            if (pictureList.last().file?.exists() == true) {
                                pictureList.last().file?.delete()
                            }
                            pictureList.removeLast()
                        }
                    } catch (e: Exception) {
                        Log.d(tag, "Error $e")

                    }
                } else {
                    if (pictureList.last().file?.exists() == true) {
                        pictureList.last().file?.delete()
                    }
                    pictureList.removeLast()
                }
            }
    }

    private fun putPictureIntoImageView(bitmap: Bitmap?) {
        if (bitmap == null) {
            Log.d(tag, (true).toString())
            picture.setImageResource(R.drawable.ic_camera) // Cambia a tu recurso de icono
        } else {
            Log.d(tag, bitmap.toString())
            picture.setImageBitmap(bitmap)
        }
    }

    private fun checkCameraPermission() = ActivityCompat.checkSelfPermission(
        baseContext,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() = ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.CAMERA),
        REQUEST_CAMERA_PERMISSION
    )

    private fun launchCamera() {
        if (checkCameraPermission()) {
            val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                fillTemporalFileForNewPhoto()
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureList.last().uri)
                takePictureLauncher.launch(cameraIntent)
            } catch (ex: IOException) {
                Log.d(tag, "Error al guardar la foto: $ex")
            }
        } else {
            requestCameraPermission()
        }
    }

    private fun createImageFileInAppDir(): File {
        try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd").format(Date())
            val imagePath = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File(imagePath, "TEV_CASA_LEY_${timeStamp}" + ".jpg")
        } catch (e: Exception) {
            Log.d(tag, "Error al crear el archivo de imagen: ${e.localizedMessage}")
            throw e
        }
    }

    private fun getExistingImageFileInAppDir(): File {
        val imagePath = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return imagePath?.listFiles()?.last() ?: createImageFileInAppDir()
    }

    private fun fillTemporalFileForExistingPhotos() {
        val photoFile = try {
            getExistingImageFileInAppDir()
        } catch (ex: IOException) {
            Log.d(tag, "Error al obtener la foto: $ex")
            null
        }

        photoFile?.let {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@FotosActivity,
                "com.coco.primeraapp.provider", // Cambia a tu autoridad
                it
            )
            pictureList.add(PictureClass(it, photoURI, null))
        }
    }

    private fun fillTemporalFileForNewPhoto() {
        val photoFile = try {
            createImageFileInAppDir()
        } catch (ex: IOException) {
            Log.d(tag, "Error al guardar la foto: $ex")
            null
        }

        photoFile?.let {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@FotosActivity,
                "com.coco.primeraapp.provider", // Cambia a tu autoridad
                it
            )
            pictureList.add(PictureClass(it, photoURI, null))
        }
    }

    private fun getTodaysPictures(): List<PictureClass> {
        val todaysPictures = mutableListOf<PictureClass>()
        val imagePath = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = dateFormat.format(Date())
        val files = imagePath?.listFiles()
        val todaysFiles = files?.filter { file ->
            file.name.startsWith("TEV_CASA_LEY_${today}")
        }
        todaysFiles?.forEach { file ->
            val uri = Uri.fromFile(file)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            todaysPictures.add(PictureClass(file, uri, bitmap))
        }
        return todaysPictures
    }

    private fun getBytesFromBitmap(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        return stream.toByteArray()
    }
}