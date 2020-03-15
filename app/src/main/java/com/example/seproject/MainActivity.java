package com.example.seproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText e1;
    Button submit;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.comment_id);
        submit=findViewById(R.id.submit_id);
        submit.setOnClickListener(this);
        db = openOrCreateDatabase("commentdb", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS comment(gaali VARCHAR);");
        SharedPreferences sp1 = getSharedPreferences
                ("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sp1.edit();
        int x=sp1.getInt("df",-1);
        if(x==-1)
        {
//            Toast.makeText(this, Integer.toString(sp.getInt("df",-1)), Toast.LENGTH_SHORT).show();
            edit1.putInt("df", 0);
            edit1.commit();
        }
        x=sp1.getInt("df",-1);
        if(x==0)
        {
//            Toast.makeText(this, Integer.toString(sp.getInt("df",-1)), Toast.LENGTH_SHORT).show();
            gali g=new gali();
            String xyz=g.getGalistr();
            String str[]=xyz.split("\\W+");
            for(int i=0;i<str.length;i++)
            {
                db.execSQL("INSERT INTO comment VALUES('" + str[i] + "');");
            }
            edit1.putInt("df",1);
            edit1.commit();
        }
    }

    @Override
    public void onClick(View view)
    {
        String str=e1.getText().toString().trim();
        str=str.toLowerCase();
        if(str.contains("hello")||str.contains("tity"))
        {
            str=str.replaceAll("hello", " ");
            str=str.replaceAll("tity", " ");
        }
        String xyz="";
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)=='_')
            {
                xyz+=" ";
                continue;
            }
            xyz+=str.charAt(i);
        }
        str=xyz;
        if(str.length()<1)
        {
            Toast.makeText(this,"Empty field!",Toast.LENGTH_LONG).show();
            return;
        }


        String str1[]=str.split("\\W+");
        for(int i=0;i<str1.length;i++)
        {
            if(str1[i].length()<1)
                str1[i]="god";
        }

        String repf="";
        for(int i=0;i<str1.length;i++)
        {
            str1[i]=str1[i].trim();
            Cursor c = db.rawQuery("SELECT * FROM comment WHERE gaali='" + str1[i] + "'", null);
            if(c.moveToFirst())
            {
                showMessage("Caution", "Words may have used which may offend someone");
                return;
            }
            int x=1;
            String rep1="";
            String rep2="";
            for(int j=0;j<str1[i].length()-1;j++)
            {
                if(str1[i].charAt(j)==str1[i].charAt(j+1))
                    x++;
                else
                    x=1;
                if(x<=2)
                    rep1+=Character.toString(str1[i].charAt(j));
            }
            if(x<=2)
                rep1+=Character.toString(str1[i].charAt(str1[i].length()-1));
            if(!str1[i].equals(rep1))
            {
                repf += rep1 + " ";
                Cursor d = db.rawQuery("SELECT * FROM comment WHERE gaali='" + rep1 + "'", null);
                if(d.moveToFirst())
                {
                    showMessage("Caution", "Words may have used which may offend someone");
                    return;
                }
            }
            for(int k=0;k<rep1.length()-1;k++)
            {
                if(rep1.charAt(k)==rep1.charAt(k+1))
                {
                    rep2+=Character.toString(rep1.charAt(++k));
                }
                else
                    rep2+=Character.toString(rep1.charAt(k));
            }
            if(rep1.length()>1)
                if(rep1.charAt(rep1.length()-1)!=rep1.charAt(rep1.length()-2))
                    rep2+=rep1.charAt(rep1.length()-1);
            if(!rep1.equals(rep2))
            {
                repf += rep2 + " ";
                Cursor e = db.rawQuery("SELECT * FROM comment WHERE gaali='" + rep2 + "'", null);
                if(e.moveToFirst())
                {
                    showMessage("Caution", "Words may have used which may offend someone");
                    return;
                }
            }
        }


//        for(int i=0;i<str1.length;i++)
//        {
//            Cursor c = db.rawQuery("SELECT * FROM comment WHERE gaali='" + str1[i] + "'", null);
//            if(c.moveToFirst())
//            {
//                showMessage("Caution", "Words may have used which may offend someone");
//                return;
//            }
//        }


        String str2="";
        for(int i=0;i<str1.length;i++)
        {
            str2+=str1[i];
        }
        str2+=repf;
        str2=slash(str2);
        Cursor d = db.rawQuery("SELECT * FROM comment", null);
        while (d.moveToNext())
        {
            if(str2.contains(d.getString(0)))
            {
                showMessage("Caution", "Words may have used which may offend someone");
                return;
            }
        }

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
//        SharedPreferences sp = getSharedPreferences
//                ("mycredentials", Context.MODE_PRIVATE);
//        Toast.makeText(this, Integer.toString(sp.getInt("df",-1)), Toast.LENGTH_SHORT).show();
    }

    public String slash(String str)
    {
        String repf="";
        int x=1;
        String rep1="";
        String rep2="";
        for(int j=0;j<str.length()-1;j++)
        {
            if(str.charAt(j)==str.charAt(j+1))
                x++;
            else
                x=1;
            if(x<=2)
                rep1+=Character.toString(str.charAt(j));
        }
        if(x<=2)
            rep1+=Character.toString(str.charAt(str.length()-1));
        if(!str.equals(rep1))
            repf += rep1 + " ";
        else
            repf += str + " ";
        //
        for(int k=0;k<rep1.length()-1;k++)
        {
            if(rep1.charAt(k)==rep1.charAt(k+1))
            {
                rep2+=Character.toString(rep1.charAt(++k));
            }
            else
                rep2+=Character.toString(rep1.charAt(k));
        }
        if(rep1.length()>1)
            if(rep1.charAt(rep1.length()-1)!=rep1.charAt(rep1.length()-2))
                rep2+=rep1.charAt(rep1.length()-1);
        if(!rep1.equals(rep2))
            repf += rep2 + " ";
        else
            repf += rep1 + " ";
        return repf;
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
