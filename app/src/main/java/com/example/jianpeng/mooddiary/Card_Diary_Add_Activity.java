package com.example.jianpeng.mooddiary;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card_Diary_Add_Activity extends BaseCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    //插入图片的Activity的返回的code
    static final int IMAGE_CODE = 99;

    int FLAG=0;
    private ProgressDialog progressDialog;
    Context context;
    private EditText et_CardDiaryText;
    ImageView addPicture;              //插入图片的imageView
    ImageView saveButton;
    Card_Diary card_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__diary__add);
        FLAG=0;
        init();
    }

    private  void init(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        context=getApplicationContext();
        card_diary=new Card_Diary();
        et_CardDiaryText=findViewById(R.id.et_addCardDiaryText);
        addPicture=findViewById(R.id.iv_AddCardDiaryPicture);
        saveButton=findViewById(R.id.iv_AddCardDiarySave);
    }



    //region 调用图库
    private void callGallery(){

        int permission_WRITE = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_READ = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission_WRITE != PackageManager.PERMISSION_GRANTED || permission_READ != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Card_Diary_Add_Activity.this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }

        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum,IMAGE_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = this.getContentResolver();
        if(requestCode == IMAGE_CODE){
            try{
                // 获得图片的uri
                Uri originalUri = data.getData();
                assert originalUri != null;
                String path = originalUri.getPath();

                //获取图片
                Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                if(bitmap != null){
                    SpannableString ss = getBitmapMime(resolver, originalUri);
                    insertPhotoToEditText(ss);
                    et_CardDiaryText.append("\n");
                }
                else{
                    showToast("Failed to insert image, no read and write storage permission, please go to the permission center", Toast.LENGTH_LONG);
                }

            }catch (Exception e){
                e.printStackTrace();
                //showToast("图片插入失败",Toast.LENGTH_SHORT);
            }
        }
    }


    //将图片插入到EditText中
    private void insertPhotoToEditText(SpannableString ss){

        if(FLAG==0){
            String str=et_CardDiaryText.getText().toString().trim();
            if((str.length()>=2 && !str.substring(str.length()-2,str.length()).equals("\n")) || str.length()==1){
                str=str+"\n";
                et_CardDiaryText.setText(str);
                et_CardDiaryText.setSelection(str.length());
            }
            FLAG=1;
        }
        else {
            String input=et_CardDiaryText.getText().toString().trim();
            String subStr="";
            Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
            Matcher m = p.matcher(input);

            int i=0;
            while(m.find()){
                int start = m.start();
                int end = m.end();
                subStr=subStr+input.substring(i,start);
                i=end;
            }
            subStr=subStr+input.substring(i,input.length());
            if(subStr.length()!=0 && !subStr.substring(subStr.length()-2,subStr.length()).equals("\n")) {
                subStr=subStr+"\n";
            }
            et_CardDiaryText.setText(subStr);
            et_CardDiaryText.setSelection(subStr.length());
        }
        Editable et = et_CardDiaryText.getText();
        int start = et_CardDiaryText.getSelectionStart();
        et.insert(start,ss);
        et_CardDiaryText.setText(et);
        et_CardDiaryText.setSelection(start+ss.length());
        et_CardDiaryText.setFocusableInTouchMode(true);
        et_CardDiaryText.setFocusable(true);

    }


    //根据图片路径利用SpannableString和ImageSpan来加载图片
    private SpannableString getBitmapMime(ContentResolver resolver, Uri originalUri) {
        int width = ScreenUtils.getScreenWidth(context);
        int height = ScreenUtils.getScreenHeight(context);

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert bitmap != null;
        int bmpWidth=bitmap.getWidth();
        int bmpHeight=bitmap.getHeight();

        bitmap = ImageUtils.zoomImage(bitmap,width*0.88,bmpHeight/(bmpWidth/(width*0.88)));

        Date tempdate=new Date();
        String tempstr=tempdate.toString();
        String bmpName=User.getUserName()+"-"+tempstr+".png";
        BitmapUtil.saveImgToDisk(bmpName,bitmap);
        card_diary.setBitmap(bitmap);
        card_diary.setBitmapName(bmpName);

        String path=BitmapUtil.getDeafaultFilePath()+bmpName;

        String tagPath = "<img src=\""+path+"\"/>";//为图片路径加上<img>标签
        SpannableString ss = new SpannableString(tagPath);//这里使用加了<img>标签的图片路径
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }





    public void onClickCancel(View view) {
        android.support.v7.app.AlertDialog.Builder Dlg = new android.support.v7.app.AlertDialog.Builder(this);
        Dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), CardDiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Dlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dlg.setMessage("\nAre you sure to cancel editing?");
        Dlg.setTitle("Confirm");
        Dlg.setIcon(R.mipmap.ic_launcher);
        Dlg.show();
    }


    //show Toast
    public void showToast(String str, int showTime) {
        Toast toast = Toast.makeText(context, str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);  //set the display location
        //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        //v.setTextColor(getResources().getColor(R.color.messageTextClor));
        toast.show();
    }


    public void onClickSave(View view) {

        Date date=new Date();
        String strText=et_CardDiaryText.getText().toString().trim();
        card_diary.setTime(date);
        card_diary.setText(strText);


        if (!strText.isEmpty()) {
            saveButton.setClickable(false);

            // Display the progress Dialog
            progressDialog.setMessage("Add Travel Diary ...");
            if (!progressDialog.isShowing())
                progressDialog.show();

            AddCardDiaryRequest(User.getUserName(),card_diary);
        }
        else {
            showToast("Card Diary can not be empty.", Toast.LENGTH_LONG);
        }

    }


    public void AddCardDiaryRequest(final String username, final Card_Diary card_diary){
        //请求地址
        final String url = new Config().getAddCardDiaryUrl();
        final String tag = "AddCardDiary";

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                //做自己的登录成功操作，如页面跳转
                                Intent intent = new Intent(getApplicationContext(), CardDiaryActivity.class);
                                startActivity(intent);
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                finish();

                            } else {
                                //做自己的登录失败操作，如Toast提示
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Save Card Diary failure, please try again later!", Toast.LENGTH_LONG);
                                saveButton.setClickable(true);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Save Card Diary failure, please try again later!", Toast.LENGTH_SHORT);
                            saveButton.setClickable(true);
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //响应错误操作
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                showToast("No network connection, please try again later!", Toast.LENGTH_LONG);
                saveButton.setClickable(true);
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                final String strdate=card_diary.getstrTime();
                final String strText=card_diary.getText();
                int numberofBmp=card_diary.getNumberOfBmp();
                String tempBmpName="";
                if(numberofBmp!=0){
                    tempBmpName=card_diary.getBitmapName();
                    Bitmap tempBmp=card_diary.getBitmap();

                }

                params.put("userName", username);
                params.put("strDate", strdate);  //给请求中填充要附带的数据
                params.put("strText", strText);
                params.put("numberofBmp", Integer.toString(numberofBmp));
                params.put("bmpname", tempBmpName);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }


    public void onClickAddPicture(View view) {

        if(FLAG==0){
            //调用图库
            callGallery();
        }
        else {
            android.support.v7.app.AlertDialog.Builder Dlg = new android.support.v7.app.AlertDialog.Builder(this);
            Dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    callGallery();
                }
            });
            Dlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            Dlg.setMessage("\nYou can only insert one picture, are you sure to replace the previous one?");
            Dlg.setTitle("Confirm");
            Dlg.setIcon(R.mipmap.ic_launcher);
            Dlg.show();
        }
    }


}
