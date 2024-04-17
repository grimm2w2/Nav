package com.example.nav


import android.os.Bundle

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager



class MainActivity : AppCompatActivity() {
    lateinit var getLocationSaved: Button
    lateinit var getDelete: Button
    lateinit var getTwoPointsCount: Button
    lateinit var localization: Button

    private val findgps: GPSLOC = GPSLOC(this)
    private val findnet: NetworkLoc = NetworkLoc(this)
    private lateinit var tvGPS: TextView
    private lateinit var tvNET: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        title="Test 1"
        getLocationSaved = findViewById(R.id.getLocationSaved)
        localization = findViewById(R.id.Localization)
        getDelete = findViewById(R.id.getLocationCleared)
        getTwoPointsCount = findViewById(R.id.getCountTwoPoints)
        tvGPS = this.findViewById(R.id.textViewGps)
        tvNET = this.findViewById(R.id.textViewNetwork)
        val db = DBHelper(this, null)
        val bundle: Bundle ? = getIntent().getExtras()
        val summary: Double
        var flag: Boolean
        if(bundle != null ){
            flag = bundle.getBoolean("ff")
            summary = bundle.getDouble("ss").toDouble()
        }
        else{
            flag = true
            summary = 0.0

        }
        if (flag){
            getLocationSaved.setText(R.string.savecoordRU)
            localization.setText(R.string.ru)
            getTwoPointsCount.setText(R.string.count2RU)
            getDelete.setText(R.string.clearallRU)
            flag=false

        }
        else{
            getLocationSaved.setText(R.string.savecoord)
            localization.setText(R.string.eng)
            getTwoPointsCount.setText(R.string.count2)
            getDelete.setText(R.string.clearall)
            flag=true
        }

        val recyclerview = findViewById<RecyclerView>(R.id.DB2)
        val recyclerview2 = findViewById<RecyclerView>(R.id.DB1)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview2.layoutManager = LinearLayoutManager(this)


        findgps.isLocationPermissionGranted()
        findgps.getLocation()
        findnet.getLocation()

        localization.setOnClickListener {
            if (flag){
                getLocationSaved.setText(R.string.savecoordRU)
                localization.setText(R.string.ru)
                getTwoPointsCount.setText(R.string.count2RU)
                getDelete.setText(R.string.clearallRU)

                flag=false
            }
            else{
                getLocationSaved.setText(R.string.savecoord)
                localization.setText(R.string.eng)
                getTwoPointsCount.setText(R.string.count2)
                getDelete.setText(R.string.clearall)
                flag=true
            }
        }
        getDelete.setOnClickListener {
            db.delCoord()
            Toast.makeText(this, "all deleted from database", Toast.LENGTH_LONG).show()
            val data = ArrayList<ItemView>()
            val data2 = ArrayList<ItemView>()
            val adapter = RVAdapter(data)
            val adapter2 = RVAdapter(data2)
            recyclerview.adapter = adapter
            recyclerview2.adapter = adapter2


        }
        getLocationSaved.setOnClickListener{


            val gps = tvGPS.text.toString()

            val net = tvNET.text.toString()




            db.addCoord(gps, net)




            Toast.makeText(this, gps + " " + net + " added to database", Toast.LENGTH_LONG).show()



            val cursor = db.getCoord()
            var text: String
            var count: Int
            // ArrayList of class ItemsViewModel
            val data = ArrayList<ItemView>()
            val data2 = ArrayList<ItemView>()





            cursor!!.moveToFirst()
            count = cursor.getInt(cursor.getColumnIndexOrThrow((DBHelper.ID_COL)))
            text = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NET_COL)) + "\n")
            text=count.toString()+": "+text
            data.add(ItemView( text))
            count = cursor.getInt(cursor.getColumnIndexOrThrow((DBHelper.ID_COL)))
            text = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GPS_COl)) + "\n")
            text=count.toString()+": "+text
            data2.add(ItemView( text))




            while(cursor.moveToNext()){

                count = cursor.getInt(cursor.getColumnIndexOrThrow((DBHelper.ID_COL)))
                text = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NET_COL)) + "\n")
                text=count.toString()+": "+text
                data.add(ItemView( text))
                count = cursor.getInt(cursor.getColumnIndexOrThrow((DBHelper.ID_COL)))
                text = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GPS_COl)) + "\n")
                text=count.toString()+": "+text
                data2.add(ItemView( text))
            }


            val adapter = RVAdapter(data)
            val adapter2 = RVAdapter(data2)


            recyclerview.adapter = adapter
            recyclerview2.adapter = adapter2
            cursor.close()


        }
        getTwoPointsCount.setOnClickListener(){


            val cursor = db.getCoord()
            val textGPSX: String
            val textNETX: String
            val textGPSY: String
            val textNETY: String
            val textGPSZ: String
            val textNETZ: String
            var X = ""
            var Y = ""
            var Z = ""
            val size = cursor!!.count
            if (size>=3){
                cursor.moveToLast()
                textNETX = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NET_COL)))
                textGPSX = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GPS_COl)))
                cursor.moveToPrevious()
                textNETY = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NET_COL)))
                textGPSY = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GPS_COl)))
                cursor.moveToPrevious()
                textNETZ = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NET_COL)))
                textGPSZ = (cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GPS_COl)))

                if (textGPSX != "NonActiveGPS" && textGPSX != "ActiveGPS" && textGPSX != "GPS coordinates" && textGPSX != textGPSY && textGPSZ != textGPSY && textGPSX != textGPSZ && textGPSY != "NonActiveGPS" && textGPSY != "ActiveGPS" && textGPSY != "GPS coordinates" && textGPSZ != "NonActiveGPS" && textGPSZ != "ActiveGPS" && textGPSZ != "GPS coordinates"){
                    X=textGPSX
                    Y=textGPSY
                    Z=textGPSZ
                }
                else if (textNETX != "ActiveInternet" && textNETX != "NonActiveInternet" && textNETX != "Network coordinates" && textNETX != textNETY && textNETZ != textNETY && textNETX != textNETZ && textNETY != "ActiveInternet" && textNETY != "NonActiveInternet" && textNETY != "Network coordinates" && textNETZ != "ActiveInternet" && textNETZ != "NonActiveInternet" && textNETZ != "Network coordinates"){
                    X=textNETX
                    Y=textNETY
                    Z=textNETZ
                }
                else {
                    Toast.makeText(this, "Save more coordinates", Toast.LENGTH_LONG).show()
                }


                if (X != "" && Y != "" && Z != ""){
                    val cd1=countDist(X,Y)
                    val cd2=countDist(Y,Z)
                    val c1 = cd1.getCalculateS()
                    val c2 = cd2.getCalculateS()
                    val intent = Intent(this, countActivity::class.java)
                    intent.putExtra("first",c1)
                    intent.putExtra("second",c2)
                    intent.putExtra("summarysquare",summary)
                    intent.putExtra("flag",!flag)
                    startActivity(intent)

                }
                else {
                    Toast.makeText(this, "Save more coordinates", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Save more coordinates", Toast.LENGTH_LONG).show()
            }



        }

    }

}
