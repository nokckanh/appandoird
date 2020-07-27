package com.vanhieu.thigiuaki;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.DividerItemDecoration;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;

        import java.util.ArrayList;

public class ListMucsic extends AppCompatActivity {


    private ArrayList<model> arrayList = new ArrayList<>();
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_mucsic );
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        arrayList.add(new model(R.drawable.anh4,"Sơn Tùng","Cơn Mưa Ngang Qua"));
        arrayList.add(new model(R.drawable.anh4,"Ho ngoc ha","Ngày vui lại đến"));
        arrayList.add(new model(R.drawable.anh4,"Lý Hải","Con Bướm xinh"));
        arrayList.add(new model(R.drawable.anh4,"Sơn Tùng","Ánh nắng xa rồi"));
        arrayList.add(new model(R.drawable.anh4,"Dương Hải","Đấu trường đua xe "));
        arrayList.add(new model(R.drawable.anh4,"Sơn Tùng","Con duong mua"));
        arrayList.add(new model(R.drawable.anh4,"Hải Móm","Ai là ai"));

        adapter = new Adapter(arrayList,ListMucsic.this);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
    }
}
