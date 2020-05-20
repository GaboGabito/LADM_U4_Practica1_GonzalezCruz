package mx.edu.ittepic.ladm_u4_practica1_gonzalezcruz

import android.app.AlertDialog
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.Toast

class CallReceiver : BroadcastReceiver(){
    var ring = false
    var callReceived = false
    var number :String?=null
    private var incoming_number: String? = null
    var mensajeEnvio=""
    override fun onReceive(context: Context, intent: Intent) {

        //Toast.makeText(context,"SE CAMBIO EL VALOR DE RING A TRUE",Toast.LENGTH_LONG).show()

        var state = intent.extras!!.getString(TelephonyManager.EXTRA_STATE)
        //val bundle:Bundle? = intent!!.extras
        //var number:String = bundle?.getString("incoming_number").toString()
        //numero = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        val tm = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
        /*if(state==TelephonyManager.EXTRA_STATE_RINGING){
            ring = true
            number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            Toast.makeText(context,"SE CAMBIO EL VALOR DE RING A TRUE",Toast.LENGTH_LONG).show()
            Toast.makeText(context,"INCOMING: "+number,Toast.LENGTH_LONG).show()
        }
        if(state==TelephonyManager.EXTRA_STATE_IDLE){
            if (ring==false&&callReceived==false){
                Toast.makeText(context,"LLAMADA PERDIDA DE: "+number,Toast.LENGTH_LONG).show()
            }
        }*/
        when(tm.callState){
            TelephonyManager.CALL_STATE_RINGING->{
                incoming_number = intent.getStringExtra("incoming_number")
                Toast.makeText(context,"INCOMING: ${incoming_number}",Toast.LENGTH_LONG).show()
                if(incoming_number!=null){
                    try{
                        val cursor=BaseDatos(context,"telefonia",null,1)
                            .readableDatabase
                            .rawQuery("SELECT * FROM TELEFONIA WHERE CELULAR = '${incoming_number}'",null)
                        if(cursor.count>0){
                            cursor.moveToFirst()
                            mensajeEnvio = cursor.getString(1)
                            SmsManager.getDefault().sendTextMessage(incoming_number,null,mensajeEnvio,null,null)
                        }


                    }catch (e:SQLiteException){
                        Toast.makeText(context, e.message,Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
            TelephonyManager.CALL_STATE_IDLE->{

            }
        }


    }
}


