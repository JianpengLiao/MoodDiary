package com.example.jianpeng.mooddiary;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travel_Diary_Add_Activity extends BaseCompatActivity implements DatePicker.OnDateChangedListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    //插入图片的Activity的返回的code
    static final int IMAGE_CODE = 99;


    private ProgressDialog progressDialog;
    private Context context;
    private int year, month, day, hour, minute, second;
    private StringBuffer strdate;
    private EditText et_TravelDiaryText, et_TravelDiaryDate;
    ScrollView scrollView;
    ImageView addPicture;              //插入图片的imageView
    ImageView saveButton;
    Travel_Diary travel_diary=new Travel_Diary();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel__diary__add);

        init();
        initWidget();
        initDateTime();
    }


    private void init(){
        et_TravelDiaryText = findViewById(R.id.et_addTravelDiaryText);
        et_TravelDiaryDate = findViewById(R.id.et_addTravelDiaryDate);
        scrollView = findViewById(R.id.sv_edit_view);
        addPicture=findViewById(R.id.iv_AddTravelDiaryPicture);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        context = this;
        strdate = new StringBuffer();
        saveButton=findViewById(R.id.iv_AddTravelDiarySave);

        if (et_TravelDiaryDate != null) {
            et_TravelDiaryDate.setCursorVisible(false);      //设置输入框中的光标不可见
            et_TravelDiaryDate.setFocusable(false);
            et_TravelDiaryDate.setFocusableInTouchMode(false);     //触摸时也得不到焦点
        }
    }


    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        strdate.append(hour).append(":").append(minute).append(":").append(second).append(" ")
                .append(day).append("/").append(String.valueOf(month)).append("/").append(String.valueOf(year));
        et_TravelDiaryDate.setText(strdate);
    }


    //region 初始化控件的各种事件
    private void initWidget() {

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent motionEvent){
                return false;
            }
        });


        scrollView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick (View view){
                return false;
            }
        });


        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                //通知父控件请勿拦截本控件touch事件
                view.getParent().requestDisallowInterceptTouchEvent(true);
                //点击整个页面都会让内容框获得焦点，且弹出软键盘
                et_TravelDiaryText.setFocusable(true);
                et_TravelDiaryText.setFocusableInTouchMode(true);
                et_TravelDiaryText.requestFocus();
                et_TravelDiaryText.setSelection(et_TravelDiaryText.getText().length());
                //显示或隐藏软键盘，如果已显示则隐藏，反之显示
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });


        //插入图片的点击事件
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用图库
                callGallery();
            }
        });
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }


    public void onClickSetDate(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (strdate.length() > 0) {
                    strdate.delete(0, strdate.length());
                }
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);
                strdate.append(hour).append(":").append(minute).append(":").append(second).append(" ")
                        .append(day).append("/").append(String.valueOf(month)).append("/").append(String.valueOf(year));
                et_TravelDiaryDate.setText(strdate);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.layout_dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("Set Date");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }


    public void onClickCancel(View view) {
        android.support.v7.app.AlertDialog.Builder Dlg = new android.support.v7.app.AlertDialog.Builder(this);
        Dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), TravelDiaryActivity.class);
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




    //region 调用图库
    private void callGallery(){

        int permission_WRITE = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_READ = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission_WRITE != PackageManager.PERMISSION_GRANTED || permission_READ != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Travel_Diary_Add_Activity.this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
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
                    et_TravelDiaryText.append("\n");
                }
                else{
                    showToast("Insert failed, no read and write storage permissions, please go to the permission center",Toast.LENGTH_LONG);
                }

            }catch (Exception e){
                e.printStackTrace();
                //showToast("图片插入失败",Toast.LENGTH_SHORT);
            }
        }
    }


    //将图片插入到EditText中
    private void insertPhotoToEditText(SpannableString ss){
        Editable et = et_TravelDiaryText.getText();
        int start = et_TravelDiaryText.getSelectionStart();
        et.insert(start,ss);
        et_TravelDiaryText.setText(et);
        et_TravelDiaryText.setSelection(start+ss.length());
        et_TravelDiaryText.setFocusableInTouchMode(true);
        et_TravelDiaryText.setFocusable(true);
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String tempstr = formatter.format(tempdate);
        String bmpName=User.getUserName()+"_"+tempstr+".png";
        BitmapUtil.saveImgToDisk(bmpName,bitmap);
        travel_diary.getBmpArray().add(bitmap);
        travel_diary.getBmpNameArray().add(bmpName);

        String path=BitmapUtil.getDeafaultFilePath()+bmpName;

        String tagPath = "<img src=\""+bmpName+"\"/>";//为图片路径加上<img>标签
        SpannableString ss = new SpannableString(tagPath);//这里使用加了<img>标签的图片路径
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }




    public void onClickSave(View view) {
        String strdate=et_TravelDiaryDate.getText().toString().trim();
        String strText=et_TravelDiaryText.getText().toString().trim();
        travel_diary.setstrTime(strdate);
        travel_diary.setText(strText);

        if (!strText.isEmpty()) {
            saveButton.setClickable(false);

            // Display the progress Dialog
            progressDialog.setMessage("Add Travel Diary ...");
            if (!progressDialog.isShowing())
                progressDialog.show();

            AddTravelDiaryRequest(User.getUserName(),travel_diary);
        }
        else {
            showToast("Travel Diary can not be empty.", Toast.LENGTH_LONG);
        }

    }


    public void AddTravelDiaryRequest(final String username, final Travel_Diary travel_diary){
        //请求地址
        final String url = new Config().getAddTravelDiaryUrl();
        final String tag = "AddTravelDiary";

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
                                //成功操作
                                Intent intent = new Intent(getApplicationContext(), TravelDiaryActivity.class);
                                startActivity(intent);
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                finish();

                            } else {
                                //失败操作
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Save Travel Diary failure, please try again later!", Toast.LENGTH_LONG);
                                saveButton.setClickable(true);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Save Travel Diary failure, please try again later!", Toast.LENGTH_SHORT);
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
                final String strdate=travel_diary.getstrTime();
                final String strText=travel_diary.getText();
                ArrayList<String> tempBmpNameArray=travel_diary.getBmpNameArray();
                int numberofBmp=tempBmpNameArray.size();

                if(numberofBmp!=0) {
                    for (int i = 0; i < numberofBmp; i++) {
                        params.put("bmpname"+i, tempBmpNameArray.get(i));

                        String path=BitmapUtil.getDeafaultFilePath()+tempBmpNameArray.get(i);
                        File f= new File(path);
                        String r=UploadUtil.uploadFile(f);
                        if(r.equals("FAILURE")){
                            showToast("Save Travel Diary Picture failure, please try again later!", Toast.LENGTH_SHORT);
                        }
                    }
                }
                params.put("userName", username);
                params.put("strDate", strdate);  //给请求中填充要附带的数据
                params.put("strText", strText);
                params.put("numberofBmp", Integer.toString(numberofBmp));
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }









    //show Toast
    public void showToast(String str, int showTime) {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);  //set the display location
        //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        //v.setTextColor(getResources().getColor(R.color.messageTextClor));
        toast.show();
    }

}
