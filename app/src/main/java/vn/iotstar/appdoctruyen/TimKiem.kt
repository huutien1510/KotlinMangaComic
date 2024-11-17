package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.TimKiemAdapter
import vn.iotstar.appdoctruyen.model.Model_TimKiem
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

class TimKiem : AppCompatActivity(), View.OnClickListener {
    var edt_search: EditText? = null
    var autoCompleteTextView: AutoCompleteTextView? = null
    var adapterItems: ArrayAdapter<String>? = null
    var listtheloai: ArrayList<String>? = null
    var listtimkiem: ArrayList<Model_TimKiem>? = null
    var tv_trong: TextView? = null
    var img_giongnoi: ImageView? = null
    var srv_danhsach: ScrollView? = null
    var email: String? = null
    var textTheLoai: String? = null
    var textSearch = ""
    private var rcv_timkiem: RecyclerView? = null
    private var rcv_adapter: TimKiemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tim_kiem)
        Anhxa()
        val intent = intent
        email = intent.getStringExtra("email")
        listtheloai = ArrayList()
        theLoai
        listtheloai!!.add(0, "Tất cả")
        adapterItems = ArrayAdapter(this, R.layout.list_item, listtheloai!!)
        autoCompleteTextView!!.setText(listtheloai!![0])
        autoCompleteTextView!!.setAdapter(adapterItems)
        textTheLoai = listtheloai!![0]
        setOnClickListener()
        Search()
    }

    private val theLoai: Unit
        private get() {
            APIService.apiService.theLoai!!.enqueue(object : Callback<List<String>?> {
                override fun onResponse(call: Call<List<String>?>, response: Response<List<String>?>) {
                    if (response.isSuccessful && response.body() != null) {
                        listtheloai = response.body() as ArrayList<String>?
                    } else {
                        Log.e("API_CALL", "Failed to fetch data from API")
                        Toast.makeText(applicationContext, "Failed to fetch data from API", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<String>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(applicationContext, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    fun editTextSearch(textSearch: String) {
        if (textSearch == "") {
            Toast.makeText(this, "Không có sản phẩm nào", Toast.LENGTH_SHORT).show()
            tv_trong!!.visibility = View.VISIBLE
            srv_danhsach!!.visibility = View.GONE
        } else {
            tv_trong!!.visibility = View.GONE
            srv_danhsach!!.visibility = View.VISIBLE
            val txt = removeAccent(textSearch)
            this.textSearch = txt
            recyclerViewTruyen(txt)
        }
    }

    private fun recyclerViewTruyen(textSearch: String) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_timkiem!!.setLayoutManager(linearLayoutManager)
        val item = DividerItemDecoration(this@TimKiem, DividerItemDecoration.VERTICAL)
        rcv_timkiem!!.addItemDecoration(item)
        listtimkiem = ArrayList()
        listTimKiem
        Handler().postDelayed({
            if (listtimkiem!!.size == 0) {
                Toast.makeText(applicationContext, "Không có truyện cần tìm!!!", Toast.LENGTH_SHORT).show()
                tv_trong!!.visibility = View.VISIBLE
                srv_danhsach!!.visibility = View.GONE
            } else {
                rcv_adapter = TimKiemAdapter(applicationContext, listtimkiem, email!!)
                rcv_timkiem!!.setAdapter(rcv_adapter)
            }
        }, 5000)
    }

    private val listTimKiem: Unit
        private get() {
            APIService.apiService.getListTimKiem(textSearch.trim { it <= ' ' })!!
                .enqueue(object : Callback<ArrayList<Model_TimKiem>?> {
                    override fun onResponse(
                        call: Call<ArrayList<Model_TimKiem>?>,
                        response: Response<ArrayList<Model_TimKiem>?>
                    ) {
                        listtimkiem = response.body()
                        rcv_adapter = TimKiemAdapter(applicationContext, listtimkiem, email!!)
                        rcv_timkiem!!.setAdapter(rcv_adapter)
                    }

                    override fun onFailure(call: Call<ArrayList<Model_TimKiem>?>, t: Throwable) {
                        Log.e("API_CALL", "Failed to fetch data from API", t)
                        Toast.makeText(this@TimKiem, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }

    fun Search() {
        edt_search!!.setOnClickListener { v: View? ->
            textSearch = edt_search!!.getText().toString()
            editTextSearch(textSearch)
        }
        autoCompleteTextView!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            textTheLoai = item
            editTextSearch(textSearch)
            if (item === "") {
                Toast.makeText(applicationContext, "Thể loại: Tất cả", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Thể loại: $item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Anhxa() {
        edt_search = findViewById(R.id.edt_search)
        autoCompleteTextView = findViewById(R.id.auto_complete_txt)
        tv_trong = findViewById(R.id.tv_trong)
        rcv_timkiem = findViewById(R.id.rcv_timkiem)
        img_giongnoi = findViewById(R.id.img_giongnoi)
        srv_danhsach = findViewById(R.id.srv_danhsach)
    }

    private fun setOnClickListener() {
        img_giongnoi!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.img_giongnoi) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                edt_search!!.setText(Objects.requireNonNull(result)!![0])
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_SPEECH_INPUT = 1
        fun removeAccent(s: String): String {
            var s = s
            s = s.lowercase(Locale.getDefault())
            s = s.replace("đ".toRegex(), "d")
            val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
            val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            return pattern.matcher(temp).replaceAll("")
        }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<ArrayList<Model_TimKiem>?>) {
    TODO("Not yet implemented")
}
