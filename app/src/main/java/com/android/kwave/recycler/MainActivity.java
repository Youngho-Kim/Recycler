package com.android.kwave.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (RecyclerView) findViewById(R.id.listView);
        // 1. 데이터
//        ArrayList<Data> datas = new ArrayList<>();
//        datas = Loader.getData(this);
        ArrayList<Data> datas = Loader.getData(this);
        // 2. 어댑터
            CustomRecycler adapter = new CustomRecycler(datas,this);

        // 3. 연결
        listView.setAdapter(adapter);

        // 4. 레이아웃 매니저
        listView.setLayoutManager(new LinearLayoutManager(this));
    }
}



class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>  { // 리사이클러 뷰에는 반드시 오버라이드 해야하는 것들이 있음
                                                                              // 제네릭 자리에 뷰홀더를 집어 넣어준다.(강제사항)
    ArrayList<Data> datas;
    Context context;

    //1. 생성자 만들기
    public CustomRecycler(ArrayList<Data> datas, Context context) {
            this.datas = datas;
        this.context = context;
    }


    // List View에서 convertView == null 일때 처리
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);      // XML을 inflater를 통해서 객체로 생성
        // 위와 같이 설정할 경우 부모xml의 형식을 따라각 리스트가 설정이 되기 때문에 1개의 리스트의 크기가 전체 크기로 나오게 된다.
        // 이때 해결할 방법은 아래처럼 하던가 item_list.xml에서 android:layout_height="match_parent"를 android:layout_height="wrap_parent"으로 바꿔주면된다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);      // XML을 inflater를 통해서 객체로 생성
//        Holder holder = new Holder(view);

        return new Holder(view);        // 만들어진 객체를 홀더로 넘겨줌
    }


    // 각 데이터 셀이 나타날때 호출되는 함수
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 꺼내고
            Data data = datas.get(position);
        // 2. 데이터를 세팅
            holder.setImage(data.resId);
            holder.setNo(data.no);
            holder.setTitle(data.title);

    }


// 데이터의 전체 개수
    @Override
    public int getItemCount() {
        return datas.size();
    }


    class Holder extends RecyclerView.ViewHolder { // 내부 클래스는 해당하는 어댑터나 클래스에서만 사용하고자 할때 만든다.
        ImageView image;
        TextView no;
        TextView title;

        public Holder(View itemView) {               // 또한, Holder는 ViewHolder를 상속해야한다.
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            no = (TextView) itemView.findViewById(R.id.txtNo);
            title = (TextView) itemView.findViewById(R.id.txtTitle);

        }

        public void setImage(int resId) {
            image.setImageResource(resId);
        }

        public void setNo(int no) {
            this.no.setText(no+"");
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }
    }
}






class Loader {
    public static ArrayList<Data> getData(Context context){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=1 ; i<=10 ; i++){
            Data data = new Data();
            data.no = i;
            data.title = "걸그룹";


            data.setImage("girl"+i, context);


            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
    public int resId;


    public void setImage(String str, Context context){
        image = str;
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
    }
}