package com.example.administrator.signin.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.signin.Cal;
import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.GeoPoint;
import com.example.administrator.signin.modul.Record;
import com.example.administrator.signin.modul.StudentCourse;
import com.example.administrator.signin.modul.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button arrive;
    private android.widget.Button exit;
    private android.widget.Button record;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private Button not;
    private int id;
    private double Latitude;
    private double Longitude;
    private double Latitude2;
    private double Longitude2;
    private Long time;
    private Button setCourse;
    private String cId;
    public static User user;
    private Button setuser;
    private android.widget.LinearLayout activitymain;
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activitymain = (LinearLayout) findViewById(R.id.activity_main);
        this.setuser = (Button) findViewById(R.id.set_user);
        this.setCourse = (Button) findViewById(R.id.setCourse);
        this.not = (Button) findViewById(R.id.not);
        this.record = (Button) findViewById(R.id.record);
        this.exit = (Button) findViewById(R.id.exit);
        this.arrive = (Button) findViewById(R.id.arrive);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        user = BmobUser.getCurrentUser(MainActivity.this,User.class);
        setCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SetCourse.class));
            }
        });
        setuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,newPassword.class));
            }
        });
        arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View conView = layoutInflater.inflate(R.layout.start_sign_in_layout, null);
            final EditText editText = (EditText)conView.findViewById(R.id.start_sign_in_id);
            builder.setView(conView);
            builder.setTitle("请输入4位签到码");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                String str = editText.getText().toString();
                if (str.length() != 4) {
                    Toast.makeText(MainActivity.this, "签到码没有4位", Toast.LENGTH_SHORT).show();
                } else {
                    id = Integer.valueOf(str);
                    BmobQuery<GeoPoint> query = new BmobQuery<GeoPoint>();
                    query.addWhereEqualTo("ID", id);
                    query.findObjects(MainActivity.this, new FindListener<GeoPoint>() {
                        @Override
                        public void onSuccess(List<GeoPoint> list) {
                            if (list.size() != 1){
                                Toast.makeText(MainActivity.this, "签到码错误", Toast.LENGTH_SHORT).show();
                            }else{
                                final GeoPoint geoPoint = list.get(0);
                                Latitude2 = geoPoint.getLatitude();
                                Longitude2 = geoPoint.getLongitude();
                                time = geoPoint.getTime();
                                cId = geoPoint.getcId();
                                BmobQuery<StudentCourse> query1 = new BmobQuery<StudentCourse>();
                                query1.addWhereEqualTo("sId",Integer.valueOf(user.getUsername()));
                                query1.addWhereEqualTo("cId",cId);
                                query1.findObjects(MainActivity.this, new FindListener<StudentCourse>() {
                                    @Override
                                    public void onSuccess(List<StudentCourse> list) {
                                        if (!list.isEmpty()){
                                        double dis = Cal.GetShortDistance(Longitude, Latitude, Longitude2, Latitude2);
                                        // Toast.makeText(MainActivity.this, ""+dis, Toast.LENGTH_SHORT).show();
                                        if (dis <= 2000) {
                                            signOn();
                                        } else {
                                            Toast.makeText(MainActivity.this, "请退出程序并离老师更近些签到", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                            Toast.makeText(MainActivity.this, "你没有选这节课", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(int i, String s) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }
                }
            });
            builder.show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record record = new Record();
                record.setOut_time(System.currentTimeMillis());
                String str = getSharedPreferences("data",MODE_PRIVATE).getString("Object",user.getObjectId());
                record.update(MainActivity.this, str, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "签退成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(MainActivity.this, "签退失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecordActivity.class));
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NotArriveActivity.class));
            }
        });
    }
    private void signOn(){
        BmobQuery<Record> query = new BmobQuery<Record>();
        query.addWhereGreaterThanOrEqualTo("in_time", time);
        query.addWhereEqualTo("name", user.getName());
        query.findObjects(MainActivity.this, new FindListener<Record>() {
            @Override
            public void onSuccess(List<Record> list) {
                if (list.isEmpty()) {
                    final Record record = new Record();
                    record.setName(user.getName());
                    record.setID(Integer.valueOf(user.getUsername()));
                    record.setIn_time(System.currentTimeMillis());
                    record.setOut_time(0L);
                    record.setcId(cId);
                    record.save(MainActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                            getSharedPreferences("data", MODE_PRIVATE).edit().putString("Object", record.getObjectId()).apply();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(MainActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "已签到!请不要重复签到", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            Longitude = location.getLongitude();
            Latitude = location.getLatitude();
        }
    }
}
