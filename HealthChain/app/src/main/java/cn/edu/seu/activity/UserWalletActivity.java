package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xujiaji.happybubble.Auto;
import com.xujiaji.happybubble.BubbleDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.adapter.TransferListAdapter;
import cn.edu.seu.common.CoinTransManager;
import cn.edu.seu.fragment.HealthFragment;
import cn.edu.seu.http.RequestAction.UserBalanceRequest;
import cn.edu.seu.http.RequestAction.UserTransactionRecordRequest;

public class UserWalletActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView back;
    private ImageView detail;
    private ImageView clipAddress;
    private TextView transant;
    private TextView account;
    private TextView balance;
    private TextView address;
    private BubbleDialog detailDialog;
    private View dialogView;
    private ListView transactionRecordListView;
    private List<Map<String, String>>  transactionRecordList;
    private TransferListAdapter transferListAdapter;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);
        initView();
    }

    private void initView(){

        sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);

        //?????????????????????UI
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);

        back = (ImageView) findViewById(R.id.back);
        detail = findViewById(R.id.detail);
        transant = findViewById(R.id.transant);
        account = findViewById(R.id.account);
        balance = (TextView) findViewById(R.id.balance);
        transactionRecordList = new ArrayList<>();
        transactionRecordListView = findViewById(R.id.transactionRecordListView);
        transferListAdapter = new TransferListAdapter(this, transactionRecordList, R.layout.activity_transaction_record_list_item);
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_ethaddr_detail, null);
        address = dialogView.findViewById(R.id.address);
        clipAddress = dialogView.findViewById(R.id.clipAddress);

        detailDialog = new BubbleDialog(this)
                .addContentView(dialogView)
                .autoPosition(Auto.UP_AND_DOWN)
                .calBar(true)
                .softShowUp();

        back.setOnClickListener(this);
        detail.setOnClickListener(this);
        clipAddress.setOnClickListener(this);
        transant.setOnClickListener(this);
        address.setText(sharedPreferences.getString("ethAddress", ""));
        transactionRecordListView.setAdapter(transferListAdapter);
        transactionRecordListView.setOnItemClickListener(this);

        account.setText(sharedPreferences.getString("username", ""));
        getBalance();
        getTransactionRecord();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //???????????????????????????????????????
            case R.id.back:
                finish();
                break;
            case R.id.transant:
                Intent intent = new Intent(UserWalletActivity.this, UserTransferActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.detail:
                detailDialog.setClickedView(detail).show();
                break;
            case R.id.clipAddress:
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("ethAddress", address.getText().toString()));
                Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UserWalletActivity.this, TransactionRecordDetailActivity.class);
        intent.putExtra("id", transactionRecordList.get(position).get("id"));
        intent.putExtra("type", transactionRecordList.get(position).get("type"));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getBalance();
        getTransactionRecord();
    }

    // ????????????
    private void getBalance(){
        // ????????????
        // ?????????token???sharedPreferences.getString("token", "")
        // ??????balance.setText()

        String token = sharedPreferences.getString("token", "");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        Handler handler = new UserBalanceHadnler(UserWalletActivity.this);
        UserBalanceRequest request = new UserBalanceRequest(UserWalletActivity.this, handler);

        request.doPost(params);
    }

    // ??????????????????
    private void getTransactionRecord(){
        transactionRecordList.clear();

        String token = sharedPreferences.getString("token", "");
        String ethAddress = sharedPreferences.getString("ethAddress", "");

        // ????????????(???????????????)

        Map<String, String>params = new HashMap<>();
        params.put("token", token);
        params.put("ethAddress", ethAddress);

        Handler handler = new UserTransactionRecordHadnler(UserWalletActivity.this);
        UserTransactionRecordRequest request = new UserTransactionRecordRequest(UserWalletActivity.this, handler);

        request.doPost(params);


        // ???????????????ethAddress???sharedPreferences.getString("ethAddress", "")
    }

    /**
     * ????????????  -- ??????
     */
    public class UserBalanceHadnler extends Handler {

        private Context context;

        public UserBalanceHadnler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // ??????????????????
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            String bal = response.getJSONObject("_data").getString("balance");
                            balance.setText("$ " + CoinTransManager.transToCoin(bal));
                        }
                        else{
                            Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }



    /**
     * ????????????  -- ????????????
     */
    public class UserTransactionRecordHadnler extends Handler {

        private Context context;

        public UserTransactionRecordHadnler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // ??????????????????
                    String ethAddress = sharedPreferences.getString("ethAddress", ""); // ?????????????????????
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONArray _data = response.getJSONArray("_data");

                            for(Integer i= _data.length() - 1; i >= 0; i--){
                                Map<String, String> item = new HashMap<>();
                                item.put("id", _data.getJSONObject(i).getString("id")); // ????????????id
                                String sendAddress = _data.getJSONObject(i).getString("sendAddress"); // ???????????????
                                String recieveAddress = _data.getJSONObject(i).getString("recieveAddress"); // ???????????????

                                if(ethAddress.equals(sendAddress))
                                    item.put("type", "0"); // ????????????(type???0??????????????????1????????????);
                                else if(ethAddress.equals(recieveAddress))
                                    item.put("type", "1"); // ????????????(type???0??????????????????1????????????)

                                item.put("amount", CoinTransManager.transToCoin(_data.getJSONObject(i).getString("transactEth")));  // ????????????
                                transactionRecordList.add(item);

                            }
                            transferListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "????????????????????????..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}
