package com.example.nav
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class countActivity: AppCompatActivity() {
    lateinit var getBack: Button
    lateinit var circleS: Button
    lateinit var rectS: Button
    lateinit var clearS: Button
    private lateinit var tvCur: TextView
    private lateinit var tvSum: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.count_layout)
        getBack = findViewById(R.id.GetBack)
        circleS = findViewById(R.id.CircleSquare)
        rectS = findViewById(R.id.RectangleSquare)
        clearS = findViewById(R.id.ClearSquare)
        tvCur = this.findViewById(R.id.textViewCurrentFigure)
        tvSum = this.findViewById(R.id.textViewSummarySquare)

        val bundle: Bundle ? = getIntent().getExtras()
        var summary: Double
        val flag: Boolean
        val fsight: Double
        val ssight: Double

        if(bundle != null ){
            summary = bundle.getDouble("summarysquare").toDouble()
            fsight = bundle.getDouble("first").toDouble()
            ssight = bundle.getDouble("second").toDouble()
            flag=bundle.getBoolean("flag")
            Toast.makeText(this, "f:"+fsight+",s:"+ssight, Toast.LENGTH_LONG).show()
            tvSum.text = ""+summary+" sq.m"
            if(flag){
                getBack.setText(R.string.backtocoordinatesRU)
                circleS.setText(R.string.circlesRU)
                rectS.setText(R.string.rectanglesRU)
                clearS.setText(R.string.clearsquareRU)
            }
            else{
                getBack.setText(R.string.backtocoordinates)
                circleS.setText(R.string.circles)
                rectS.setText(R.string.rectangles)
                clearS.setText(R.string.clearsquare)
            }

        }
        else{
            summary = 0.0
            fsight = 1.0
            ssight = 1.0
            flag = true
        }



        getBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("ss",summary)
            intent.putExtra("ff",flag)

            startActivity(intent)

        }
        circleS.setOnClickListener{
            summary= summary + (Math.PI*fsight*ssight)
            tvCur.text = ""+(Math.PI*fsight*ssight)+" sq.m"
            tvSum.text = ""+summary+" sq.m"
            rectS.visibility = View.INVISIBLE
            rectS.isEnabled = false
            rectS.isClickable = false
            circleS.visibility = View.INVISIBLE
            circleS.isEnabled = false
            circleS.isClickable = false
        }
        rectS.setOnClickListener{
            summary= summary + (fsight*ssight)
            tvCur.text = ""+(fsight*ssight)+" sq.m"
            tvSum.text = ""+summary+" sq.m"
            rectS.visibility = View.INVISIBLE
            rectS.isEnabled = false
            rectS.isClickable = false
            circleS.visibility = View.INVISIBLE
            circleS.isEnabled = false
            circleS.isClickable = false
        }
        clearS.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("ss",0)
            intent.putExtra("ff",flag)

            startActivity(intent)
        }


    }
}